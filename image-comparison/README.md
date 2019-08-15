# TestProject Addon - Image Comparison

This Addon provides actions to compare two images.
The addon also generates a result image that highlights the differences between the two images.

## Actions
There are 2 actions in this addon:

* Compare two images
* Take element screenshot

### Fields

|  Field/Action   | Required | Input/Output | 
|-----------------|----------|--------------|
| imagePath       |     X    |     INPUT    |
| threshold       |          |     INPUT    |
| resultImagePath |          |     INPUT    |
| imgName         |          |     INPUT    |
| fullResPath     |          |     OUTPUT   |

#### Input Fields

* `imagePath` - The path to the original image that you want to compare to.

    > This is a mandatory parameter, otherwise action will fail.


* `threshold` - The differences percentage you are willing to accept between the two images.

    > The default value, in case you leave this field empty, is 5%.

* `resultImagePath` - The path where you want to save the result comparison image.

    > The result image is in jpg format. It highlights the differences between the two images by drawing red rectangles across them.

* `imgName`- The name that you want to assign the result image.

    > In case you left this field empty, the image name will be: 
      * For "Take element screenshot" action: "screenshot_<current_time_stamp>.jpg"
      * For "Compare twp images" action: "comparison_result<current_time_stamp>.jpg"

#### Output Fields

* `fullResPath` - Contains the full path where the result image was saved in.

### Results

#### Failure Criteria

This action have a few criterion that may result in failure assertion:

* When `imagePath` inputs is empty or can not be found, then the step will fail with an appropriate message.

* When the differences between the two images is greater than the defined `threshold` parameter, then the step will fail with an appropriate message.

### Result Description

The action will report a message with the following information:

* In case the user didn't provide the path for the original image:
    > You must provide a path to the original image you want to compare to.

* In case the provided path was not found:
    > The specified location to save the result image was not found.

* In case the provided threshold value is not a number:
    > "The threshold value must be a double type numeric value."

* In case the differences percentage is greater than the defined threshold:
    > The difference percentage is: %.2f%% and it's greater than the defined threshold %.2f%%.

* In case the screenshot was successfully taken:
    > Element screenshot was stored in %s.

* In case the images comparison result is lower than the defined threshold:
    > The difference percentage between the two images is %.2f%%  The result image was saved in: %s.

### Examples

Following TestProject platform conventions, unset parameters will be treated as nulls / empty strings and won't be used.

#### Example 1: Take element screenshot

| Field            | Value                                            | Input/Output |
|------------------|--------------------------------------------------|--------------|
| imagePath        | C:\Users\johndoe\Desktop\screenshots             |     INPUT    |
| imgName          | before.jpg                                       |     INPUT    |
| fullResPath      | C:\Users\johndoe\Desktop\screenshots\before.jpg  |     OUTPUT   |

Action result: *PASSED*

#### Example 2: Compare two images

| Field            | Value                                            | Input/Output |
|------------------|--------------------------------------------------|--------------|
| imagePath        | C:\Users\johndoe\Desktop\screenshots\before.jpg  |     INPUT    |
| threshold        | 5                                                |     INPUT    |
| resultImagePath  | C:\Users\johndoe\Desktop\screenshots             |     INPUT    |
| imgName          | after                                            |     INPUT    |
| fullResPath      | C:\Users\johndoe\Desktop\screenshots\after.jpg   |     OUTPUT   |

Action result: *PASSED*

## Author

TestProject LTD. 2019

## License

Apache License 2.0

## Platform

Web, Android and iOS