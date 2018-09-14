/*
 * Copyright 2018 TestProject LTD. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.testproject.addon.permissions.manager.internal;

/**
 * Aggregating Android permissions into logical groups
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class PermissionsGroups {


    /*
        An application such as file browser may need some of the permission that listed in the group.
     */
    public static final String[] STORAGE =
            {
                    "DELETE_PACKAGES",                  // Allows an application to delete packages.
                    "GET_PACKAGE_SIZE",                 // Allows an application to find out the space used by any package.
                    "MANAGE_DOCUMENTS",                 // Allows an application to manage access to documents, usually as part of a document picker.
                    "MEDIA_CONTENT_CONTROL",            // Allows an application to know what content is playing and control its playback.
                    "MOUNT_FORMAT_FILESYSTEMS",         // Allows formatting file systems for removable storage.
                    "MOUNT_UNMOUNT_FILESYSTEMS",        // Allows mounting and unmounting file systems for removable storage.
                    "READ_CALL_LOG",                    // Allows an application to read the user's call log.
                    "READ_CONTACTS",                    // Allows an application to read the user's contacts data.
                    "READ_CALENDAR",                    // Allows an application to read the user's calendar data.
                    "READ_EXTERNAL_STORAGE",            // Allows an application to read from external storage.
                    "READ_LOGS",                        // Allows an application to read the low-level system log files.
                    "WRITE_CONTACTS",                   // Allows an application to write the user's contacts data.
                    "WRITE_EXTERNAL_STORAGE",           // Allows an application to write to external storage.
                    "WRITE_SECURE_SETTINGS",            // Allows an application to read or write the secure system settings.
                    "WRITE_SETTINGS",                   // Allows an application to read or write the system settings.
                    "WRITE_SYNC_SETTINGS",              // Allows applications to write the sync settings.
                    "WRITE_VOICEMAIL"                   // Allows an application to modify and remove existing voicemails in the system.
            };

    /*
        An application that based on GPS may need some permissions in the group
     */
    public static final String[] LOCATION =
            {
                    "ACCESS_COARSE_LOCATION",       // Allows an app to access approximate location.
                    "ACCESS_FINE_LOCATION",         // Allows an app to access precise location.
                    "ACCESS_LOCATION_EXTRA_COMMANDS", // Allows an application to access extra location provider commands.
                    "CONTROL_LOCATION_UPDATES",     // Allows enabling/disabling location update notifications from the radio.
                    "INSTALL_LOCATION_PROVIDER",    // Allows an application to install a location provider into the Location Manager.
                    "LOCATION_HARDWARE"             // Allows an application to use location features in hardware, such as the geofencing api.
            };

    /*
        An application such as email application may need some of the permission that listed in the group
    */
    public static final String[] CONTACTS =
            {
                    "READ_CONTACTS",                // Allows an application to read the user's contacts data.
                    "READ_PHONE_NUMBERS",           // Allows read access to the device's phone number(s).
                    "READ_VOICEMAIL",               // Allows an application to read voicemails in the system.
                    "WRITE_CONTACTS"               // Allows an application to write the user's contacts data.
            };

    /*
        An application that need access to SMS (for editing, deleting, changing, ech.)
        may need some of the permission that listed in the group
     */
    public static final String[] SMS =
            {
                    "BROADCAST_SMS",             // Allows an application to broadcast an SMS receipt notification.
                    "READ_SMS",                  // Allows an application to read SMS messages.
                    "RECEIVE_SMS",              // Allows an application to receive SMS messages.
                    "SEND_SMS",                  // Allows an application to send SMS messages.
            };


    /*
        An application that need access to phone calls, phone number, ech.
        may need some of the permission that listed in the group
     */
    public static final String[] PHONE_CALLS =
            {
                    "ANSWER_PHONE_CALLS",           // Allows the app to answer an incoming phone call.
                    "CALL_PHONE",                  // Allows an application to initiate a phone call without going through the Dialer user interface for the user to confirm the call.
                    "CALL_PRIVILEGED",             // Allows an application to call any phone number, including emergency numbers, without going through the Dialer user interface for the user to confirm the call being placed.
                    "CAPTURE_AUDIO_OUTPUT",        // Allows an application to capture audio output.
                    "CAPTURE_SECURE_VIDEO_OUTPUT", // Allows an application to capture secure video output.
                    "CAPTURE_VIDEO_OUTPUT",        // Allows an application to capture video output.
                    "MANAGE_OWN_CALLS",            // Allows a calling application which manages it own calls through the self-managed ConnectionService APIs.
                    "READ_CALL_LOG",               // Allows an application to read the user's call log.
                    "READ_PHONE_NUMBERS",          // Allows read access to the device's phone number(s).
                    "READ_VOICEMAIL",              // Allows an application to read voicemails in the system.
                    "RECORD_AUDIO",                // Allows an application to record audio.

            };

    /*
        An application that need access to internet or network such as internet browser
        may need some of the permission that listed in the group
     */
    public static final String[] NETWORK =
            {
                    "ACCESS_NETWORK_STATE",     // Allows applications to access information about networks.
                    "ACCESS_WIFI_STATE",        // Allows applications to access information about Wi-Fi networks.
                    "INTERNET"                  // Allows applications to open network sockets.
            };

    /*
        An application that need access to media content such as video/audio player
        may need some of the permission that listed in the group
     */
    public static final String[] MEDIA =
            {
                    "MEDIA_CONTENT_CONTROL",    // Allows an application to know what content is playing and control its playback.
                    "CAMERA",                    // Required to be able to access the camera device.
                    "RECORD_AUDIO",                // Allows an application to record audio.

            };

    /*
        An application that need access to sensors such as camera, body sensors
        may need some of the permission that listed in the group
    */
    public static final String[] SENSORS =
            {
                    "CAMERA",                    // Required to be able to access the camera device.
                    "BODY_SENSORS",              // Allows an application to access data from sensors that the user uses to measure what is happening inside his/her body, such as heart rate.

            };
}
