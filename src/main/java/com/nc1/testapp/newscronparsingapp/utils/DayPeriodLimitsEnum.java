package com.nc1.testapp.newscronparsingapp.utils;

import java.time.LocalTime;

public enum DayPeriodLimitsEnum {
    MORNING (new LocalTime[]{AppConstants.MORNING_PERIOD_START_TIME,
            AppConstants.MORNING_PERIOD_END_TIME}),
    DAY(new LocalTime[]{AppConstants.DAY_PERIOD_START_TIME,
            AppConstants.DAY_PERIOD_END_TIME}),
    EVENING(new LocalTime[]{AppConstants.EVENING_PERIOD_START_TIME,
            AppConstants.EVENING_PERIOD_END_TIME});

    private final LocalTime[] dayPeriodLimits;

    DayPeriodLimitsEnum(LocalTime[] dayPeriodLimits) {
        this.dayPeriodLimits = dayPeriodLimits;
    }

    public LocalTime[] getDayPeriodLimits() {
        return dayPeriodLimits;
    }
}
