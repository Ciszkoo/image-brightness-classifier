# Image Brightness Classifier

This program determines whether an image is bright or dark based on a user-defined cut-off point. The cut-off point value, the folder name containing the images, and the folder name where the classified images should be saved are specified in the [`application.conf`](src/main/resources) file. The program calculates the average lightness of the image by converting the pixel color format from RGB to HSL. The program saves the images in the format of `{original name}_{bright | dark}_{lightness value}.{original extension}`.

## Getting Started
### Prerequisites
- Java 11 or later
- sbt 1.6.2 or later

### Configuration
The configuration file [`application.conf`](src/main/resources) contains the following parameters:
- `cut-off-point`: The cut-off point value used to classify the images into bright or dark.
- `input-dir`: The name of the folder containing the images to be classified.
- `output-dir`: The name of the folder where the classified images should be saved.

### Usage
- Update the [`application.conf`](src/main/resources) file with the desired values for the cut-off point, input directory, and output directory.
- Place the images to be classified in the input directory specified in the [`application.conf`](src/main/resources) file.
- Navigate to the project directory and run the program using the command `sbt run`.
- The classified images will be saved in the output directory specified in the [`application.conf`](src/main/resources) file.

## License
This project is licensed under the MIT License - see the [`LICENSE.md`](LICENSE.md) file for details.
