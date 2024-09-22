package com.nc1.testapp.newscronparsingapp.utils;

import java.time.LocalTime;

public class AppConstants {
    private AppConstants() {
//        This is util class, not for instantiate!
    }
// Test resource for parsing. First in Google search
    public static final String SITE_FOR_PARSING = "https://www.nbcnews.com/";

//    Current time period for parsing is 20 min
    public static final String NEWS_PARSING_TIME_PERIOD = "0 */20 * * * *";

//    LocalDateTime formatter. European date format, no ':' delimiters
//    No Zoned localization for the test task
    public static final String DATE_TIME_FORMATTER = "dd-MM-yyyy HH-mm";

//    LocalTime periods limits
    public static final LocalTime MORNING_PERIOD_START_TIME = LocalTime.of(6, 0);
    public static final LocalTime MORNING_PERIOD_END_TIME = LocalTime.of(10, 59);

    public static final LocalTime DAY_PERIOD_START_TIME = LocalTime.of(11, 0);
    public static final LocalTime DAY_PERIOD_END_TIME = LocalTime.of(16, 59);

    public static final LocalTime EVENING_PERIOD_START_TIME = LocalTime.of(17, 0);
    public static final LocalTime EVENING_PERIOD_END_TIME = LocalTime.of(21, 59);
}
