import javax.imageio.ImageIO;

import com.objectplanet.image.PngEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bin2img
{
    private byte[] file;
    private String filename;
    private String outFilename = null;
    
    private boolean color = true;
    private boolean background = false;
    private boolean circle = false;
    private boolean lowColors = false;
    private int width = 300;
    
    

    public Bin2img (String filename)
    {
    	this.filename = filename;
        Path path = Paths.get(filename);
        try
        {
            file = Files.readAllBytes(path);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void toPNG ()
    {
    	int cHeight, cWidth; 
    	if (this.circle)
    	{
    		cHeight = (int) Math.sqrt(getPixelsCount())+2;
        	cWidth = (int) Math.sqrt(getPixelsCount())+2;
        }
    	else
    	{
    		//TODO round up
	    	cHeight = (int) this.getPixelsCount()/this.width;
	        if (this.getPixelsCount()%500 > 0)
	        {
	        	cHeight++;
	        }
	        cWidth = this.width;
    	}
        BufferedImage bi = new BufferedImage(cWidth, cHeight, BufferedImage.TYPE_INT_RGB);
        
        if (this.background)
        {
        	Graphics2D ig2 = bi.createGraphics();
            ig2.setBackground(Color.white);
	        ig2.clearRect(0, 0, cWidth, cHeight);
        }

        if (this.circle)
        {
        	//TODO round up
        	int x = Math.round(cHeight/2);
        	int y = x;
        	int direction = 1;	//1-right 2-down 3-left 4-up
        	int incriment = 1;
        	int go = 0;			//steps counter
        	for (int i = 0; i < getPixelsCount(); i++)
        	{
        		if ((i+1)%(getPixelsCount()/10) == 0)
        		{
        			System.out.print("\r"+(((i+1)*10/(getPixelsCount()/10)))+"%");
        		}
        		bi.setRGB(x, y, getColorByNumber(i));
        		switch (direction) 
        		{
        		case 1:
        			x ++;
        			if (++go >= incriment)
        			{
        				direction = 2;
        				go = 0;
        			}
					break;
				case 2:
					y ++;
					if (++go >= incriment)
        			{
						direction = 3;
						go = 0;
						incriment++;
        			}
					break;
				case 3:
					x--;
					if (++go >= incriment)
        			{
						direction = 4;
						go = 0;
        			}
					break;
				case 4:
					y--;
					if (++go >= incriment)
        			{
						direction = 1;
						go = 0;
						incriment++;
        			}
					break;	
				}
        	}
        }
        else
        {	
        	for (int i = 0; i < getPixelsCount(); i++)
	        {
        		if ((i+1)%(getPixelsCount()/10) == 0)
        		{
        			System.out.print("\r"+(((i+1)*10/(getPixelsCount()/10)))+"%");
        		}
	            bi.setRGB(i%cWidth, i/cWidth, getColorByNumber(i));
	        }
        }
        
        String filename = this.filename+".png";
        if (this.outFilename != null)
        {
        	filename = this.outFilename;
        }
        
	    File output_file = new File(filename);
        try
        {
        	System.out.println();
        	System.out.println("Image saving...");
        	
            //ImageIO.write(bi, "PNG", output_file);
            
        	BufferedOutputStream imageOutputStream = new BufferedOutputStream(new FileOutputStream(output_file));
            new PngEncoder().encode(bi, imageOutputStream);
            imageOutputStream.close();
        	
        	System.out.println("Saved to "+filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private int getPixelsCount ()
    {
    	if (this.color)
    	{
    		return this.file.length;
    	}
    	else
    	{
    		return this.file.length * 8;
    	}
    }
    
    private int getColorByNumber (int number)
    {
    	if (this.color)
    	{
    		if (this.lowColors)
    		{
    			int b = this.file[number];
    			b += 127;	//TODO unsign convert
    			if (b < 64)
    			{
    				return Color.red.getRGB();
    			}
    			else if (b < 128)
    			{
    				return Color.blue.getRGB();
    			}
    			else if (b < 192)
    			{
    				return Color.green.getRGB();
    			}
    			else
    			{
    				return Color.yellow.getRGB();
    			}
    		}
    		else
    		{
    			return getColor4Byte(this.file[number]);
    		}
    	}
    	else
    	{
    		int b = this.file[number/8];
    		int offset = 7-number%8;
    		if (((b>>offset)&1) == 1)
    		{
    			return Color.black.getRGB();
    		}
    		else
    		{
    			return Color.white.getRGB();
    		}
    		
    	}
    }
    
    private int getColor4Byte (byte b)
    {
    	int red = (b>>5)&7;
    	int green = (b>>2)&7;
    	int blue = b&3;
    	
    	return (255<<24)|(red<<21)|(green<<13)|(blue<<6);
    }

	public void setColor(boolean color) 
	{
		this.color = color;
	}

	public void setBackground(boolean background) 
	{
		this.background = background;
	}

	public void setCircle(boolean circle) 
	{
		this.circle = circle;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public void setOutFilename(String outFilename) 
	{
		this.outFilename = outFilename;
	}

	public void setLowColors(boolean lowColors) 
	{
		this.lowColors = lowColors;
	}
    
}
