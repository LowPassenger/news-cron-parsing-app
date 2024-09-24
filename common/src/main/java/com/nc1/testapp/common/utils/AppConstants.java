package com.nc1.testapp.common.utils;

import java.time.LocalTime;

public class AppConstants {
    private AppConstants() {
//        This is util class, not for instantiate!
    }
// Test resource for parsing. Plain text news site
    public static final String SITE_FOR_PARSING = "https://www.csmonitor.com/text_edition";

//    Html blocs to select on news page
    public static final String NEWS_BLOCKS_TO_SELECT = "ul.list-unstyled > li\n";
    public static final String NEWS_HEADLINE_HTML_TAG = "span";
    public static final String NEWS_DESCRIPTION_HTML_TAG = "p";

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

//    URL for Server API connection to Server module for getting news at some period

    public static final String SERVER_API_URL_FOR_LOCAL_SERVER =
            "http://localhost:8081/api/news/period?period=";

//    Period names for Client API for using in URL requests
    public static final String URL_REQUEST_MORNING_PERIOD_NAME = "morning";
    public static final String URL_REQUEST_DAY_PERIOD_NAME = "day";
    public static final String URL_REQUEST_EVENING_PERIOD_NAME = "evening";

//    JavaFX parameter's
//    Window size, proportions Width * Height are 4 * 3
    public static final double JAVAFX_WINDOW_WIDTH = 800D;
    public static final double JAVAFX_WINDOW_HEIGHT = 600D;
}
