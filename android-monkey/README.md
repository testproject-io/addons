# TestProject Addon - Android Monkey

This addon is a wrapper for the Android Monkey program: https://developer.android.com/studio/test/monkey

The Monkey is a program that runs on your emulator or device and generates pseudo-random streams of user events such as clicks, touches, or gestures, as well as a number of system-level events. You can use the Monkey to stress-test applications that you are developing, in a random yet repeatable manner.

## Actions

There is a single action in this addon:

* Android Monkey

### Fields

|  Field/Action   | Required | Input/Output |
|-----------------|----------|--------------|
| randomSeed      |          |     INPUT    |
| packages        |          |     INPUT    |
| numEvents       |          |     INPUT    |
| throttle        |          |     INPUT    |
| pctAppSwitch    |          |     INPUT    |
| pctTouch        |          |     INPUT    |
| pctMotion       |          |     INPUT    |
| pctTrackball    |          |     INPUT    |
| pctSysKeys      |          |     INPUT    |
| pctNav          |          |     INPUT    |
| pctFlip         |          |     INPUT    |
| pctAnyEvent     |          |     INPUT    |
| pctPinchZoom    |          |     INPUT    |

#### Input Fields

* `randomSeed` - Seed value for pseudo-random number generator. If you re-run the Monkey with the same seed value, it will generate the same sequence of events.
> 

* `packages` - If you specify one or more packages this way, the Monkey will only allow the system to visit activities within those packages.

    > You can add more than one package by separating them using semi-colon - `;`
    > If none are specified, the default value will be the current package you are testing.

* `numEvents` - The number of monkey events to perform.

    > If none are specified, the default value is: `500`

* `throttle`- Sets the delay between the events (in milliseconds).

    > You can use this option to slow down the Monkey. If not specified, there is no delay and the events are generated as rapidly as possible

* `pctAppSwitch` - The percentage of application switching.

    > The default value is set to zero.

* `pctTouch` - The percentage of touch gestures. Touch events are a down-up event in a single place on the screen

    > The default value is set to zero.

* `pctMotion` - The percentage of motion gestures. Motion events consist of a down event somewhere on the screen, a series of pseudo-random movements, and an up event.

    > The default value is set to zero.

* `pctTrackball` - The percentage of motion gestures. Trackball events consist of one or more random movements, sometimes followed by a click

    > The default value is set to zero.

* `pctSysKeys` - The percentage of system keys. These are keys that are generally reserved for use by the system, such as Home, Back, Start Call, End Call, or Volume controls.

    > The default value is set to zero.

* `pctNav` - The percentage of navigation events. Navigation events consist of up/down/left/right, as input from a directional input device.

    > The default value is set to zero.

* `pctFlip` - The percentage of flip events.

    > The default value is set to zero.

* `pctAnyEvent` - The percentage of other types of events. This is a catch-all for all other types of events such as key presses, other less-used buttons on the device, and so forth.

    > The default value is set to zero.

* `pctPinchZoom` - The percentage of pinch&zoom gestures. A multi-touch gesture for zooming in and out on an element or screen.

    > The default value is set to zero.

### Results

#### Failure Criteria

Action has several criteria that may result in failure assertion:

* When the Android device could not be found.

* When the ADB initialization was interrupted or exceeded the timeout.

### Result Description

Action will report a message with the following information:

* In case the Android device was not found:
    > "Requested device <DEVICE_UDID> not found"

* In case the ADB initialization was interrupted:
    > "Interrupted while waiting for ADB to initialize"

* In case the ADB initialization reached to the timeout:
    > "Timed out while waiting for ADB to initialize and retrieve devices"

## Author

TestProject LTD. 2019

## License

Apache License 2.0

## Platform

Android
