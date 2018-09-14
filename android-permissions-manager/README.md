# TestProject Addon - Android Permissions Manager
Provides actions to grant or revoke Android permissions for the application under test
A list of all existing permissions can be found [here](https://developer.android
.com/reference/android/Manifest.permission).

### Supported groups

##### Permissions for contacts list
    DELETE_PACKAGES                  Allows an application to delete packages.
    GET_PACKAGE_SIZE                 Allows an application to find out the space used by any package.
    MANAGE_DOCUMENTS                 Allows an application to manage access to documents, usually as part of a document picker.
    MEDIA_CONTENT_CONTROL            Allows an application to know what content is playing and control its playback.
    MOUNT_FORMAT_FILESYSTEMS         Allows formatting file systems for removable storage.
    MOUNT_UNMOUNT_FILESYSTEMS        Allows mounting and unmounting file systems for removable storage.
    READ_CALL_LOG                    Allows an application to read the user's call log.
    READ_CONTACTS                    Allows an application to read the user's contacts data.
    READ_CALENDAR                    Allows an application to read the user's calendar data.
    READ_EXTERNAL_STORAGE            Allows an application to read from external storage.
    READ_LOGS                        Allows an application to read the low-level system log files.
    WRITE_CONTACTS                   Allows an application to write the user's contacts data.
    WRITE_EXTERNAL_STORAGE           Allows an application to write to external storage.
    WRITE_SECURE_SETTINGS            Allows an application to read or write the secure system settings.
    WRITE_SETTINGS                   Allows an application to read or write the system settings.
    WRITE_SYNC_SETTINGS              Allows applications to write the sync settings.
    WRITE_VOICEMAIL                  Allows an application to modify and remove existing voicemails in the system.
                    
##### Permissions for device location
    ACCESS_COARSE_LOCATION            Allows an app to access approximate location.
    ACCESS_FINE_LOCATION              Allows an app to access precise location.
    ACCESS_LOCATION_EXTRA_COMMANDS    Allows an application to access extra location provider commands.
    CONTROL_LOCATION_UPDATES          Allows enabling/disabling location update notifications from the radio.
    INSTALL_LOCATION_PROVIDER         Allows an application to install a location provider into the Location Manager.
    LOCATION_HARDWARE                 Allows an application to use location features in hardware, such as the geofencing api.
    
                   
##### Permissions for media contents
    MEDIA_CONTENT_CONTROL             Allows an application to know what content is playing and control its playback.
    CAMERA                            Required to be able to access the camera device.
    RECORD_AUDIO                      Allows an application to record audio.
    
##### Permissions for network / internet
    ACCESS_NETWORK_STATE               Allows applications to access information about networks.
    ACCESS_WIFI_STATE                  Allows applications to access information about Wi-Fi networks.
    INTERNET                           Allows applications to open network sockets.


##### Permissions for phone calls
    ANSWER_PHONE_CALLS                 Allows the app to answer an incoming phone call.
    CALL_PHONE                         Allows an application to initiate a phone call without going through the Dialer user interface for the user to confirm the call.
    CALL_PRIVILEGED                    Allows an application to call any phone number, including emergency numbers, without going through the Dialer user interface for the user to confirm the call being placed.
    CAPTURE_AUDIO_OUTPUT               Allows an application to capture audio output.
    CAPTURE_SECURE_VIDEO_OUTPUT        Allows an application to capture secure video output.
    CAPTURE_VIDEO_OUTPUT               Allows an application to capture video output.
    MANAGE_OWN_CALLS                   Allows a calling application which manages it own calls through the self-managed ConnectionService APIs.
    READ_CALL_LOG                      Allows an application to read the user's call log.
    READ_PHONE_NUMBERS                 Allows read access to the device's phone number(s).
    READ_VOICEMAIL                     Allows an application to read voicemails in the system.
    RECORD_AUDIO                       Allows an application to record audio.


##### Permissions for sensors
    CAMERA                             Required to be able to access the camera device.
    BODY_SENSORS                       Allows an application to access data from sensors that the user uses to measure what is happening inside his/her body, such as heart rate.


##### Permissions for SMS
    BROADCAST_SMS                      Allows an application to broadcast an SMS receipt notification.
    READ_SMS                           Allows an application to read SMS messages.
    RECEIVE_SMS                        Allows an application to receive SMS messages.
    SEND_SMS                           Allows an application to send SMS messages.

                    

##### Permissions for external storage
    DELETE_PACKAGES                   Allows an application to delete packages.
    GET_PACKAGE_SIZE                  Allows an application to find out the space used by any package.
    MANAGE_DOCUMENTS                  Allows an application to manage access to documents, usually as part of a document picker.
    MEDIA_CONTENT_CONTROL             Allows an application to know what content is playing and control its playback.
    MOUNT_FORMAT_FILESYSTEMS          Allows formatting file systems for removable storage.
    MOUNT_UNMOUNT_FILESYSTEMS         Allows mounting and unmounting file systems for removable storage.
    READ_CALL_LOG                     Allows an application to read the user's call log.
    READ_CONTACTS                     Allows an application to read the user's contacts data.
    READ_CALENDAR                     Allows an application to read the user's calendar data.
    READ_EXTERNAL_STORAGE             Allows an application to read from external storage.
    READ_LOGS                         Allows an application to read the low-level system log files.
    WRITE_CONTACTS                    Allows an application to write the user's contacts data.
    WRITE_EXTERNAL_STORAGE            Allows an application to write to external storage.
    WRITE_SECURE_SETTINGS             Allows an application to read or write the secure system settings.
    WRITE_SETTINGS                    Allows an application to read or write the system settings.
    WRITE_SYNC_SETTINGS               Allows applications to write the sync settings.
    WRITE_VOICEMAIL                   Allows an application to modify and remove existing voicemails in the system.


## Author
TestProject Inc. 2018

## License
Apache License 2.0

## Platform
Android Actions
