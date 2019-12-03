package com.techno.takhdimprovider.App;

/**
 * Created by ritesh on 22/9/17.
 */

public class Config {

    //global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    //public static final String BASE_URL = "http://mobileappdevelop.co/Takhdim/webservice/";
//    public static final String BASE_URL = "http://mobileappdevelop.in/Takhdim/webservice/";
    public static final String BASE_URL = "https://www.takhdim-admin.com/index.php/webservice/";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
