public class Main
{
    public static void main(String[] args)
    {
    	if (args.length == 1 && args[0].equals("--help"))
    	{
    		System.out.println("Usage: bin2img.jar [--background 1/0] [--width width_in_px] "+
    				"[--color 1/0] [--lowcolors 1/0] [--circle 1/0] [--out output_file_name] input_file_name");
    		System.exit(0);
    	}
    	
    	Bin2img test = new Bin2img(args[args.length-1]);
    	for (int i = 0; i < args.length; i++)
    	{
    		switch (args[i]) 
    		{
			case "--background":
				if (args[i+1].equals("1"))
				{
					test.setBackground(true);
				}
				else
				{
					test.setBackground(false);
				}
				break;
			case "--circle":
				if (args[i+1].equals("1"))
				{
					test.setCircle(true);
				}
				else
				{
					test.setCircle(false);
				}
				break;
			case "--color":
				if (args[i+1].equals("1"))
				{
					test.setColor(true);
				}
				else
				{
					test.setColor(false);
				}
				break;
			case "--width":
				test.setWidth(Integer.parseInt(args[i+1]));
				break;
			case "--out":
				test.setOutFilename(args[i+1]);
				break;
			case "--lowcolors":
				if (args[i+1].equals("1"))
				{
					test.setLowColors(true);
				}
				else
				{
					test.setLowColors(false);
				}
				break;
			}
    	}
    	System.out.println("Image creation started");
        test.toPNG();
    }
}
