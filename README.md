# bin2img
This program can create PNG images from binary files

Last avalible release: https://github.com/Tmin10/bin2img/releases/latest

Usage:
```bin2img.jar [--background 1/0] [--width width_in_px] [--color 1/0] [--lowcolors 1/0] [--circle 1/0] [--out output_file_name] input_file_name```

Options:
* **background**: 1 for white background, 0 for transparent background.
* **width**: width of output picture in pixels. Used only with circle = 0 option.
* **color**: 1 for showing every byte as one RGB pixel, 0 for showing every bit as black or white.
* **lowcolors**: used only with color = 1. 1 for only 4 color pixels, 0 for fullcolor pixels.
* **circle**: 1 for showing file as spiral from center to borders, 0 for left to right rows.
* **out**: name for output file.
* **input_file_name**: name for input file.
