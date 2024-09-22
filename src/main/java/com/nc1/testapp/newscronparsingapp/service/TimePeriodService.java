package com.nc1.testapp.newscronparsingapp.service;

import com.nc1.testapp.newscronparsingapp.exception.PeriodNotFoundException;
import com.nc1.testapp.newscronparsingapp.utils.DayPeriodLimitsEnum;
import org.springframework.stereotype.Service;

@Service
public class TimePeriodService {
    public DayPeriodLimitsEnum getDayPeriodFromString(String period)
            throws PeriodNotFoundException {
        for (DayPeriodLimitsEnum periodEnum : DayPeriodLimitsEnum.values()) {
            if (period != null && period.equals(periodEnum.name().toLowerCase())) {
                return periodEnum;
            }
        }
        throw new PeriodNotFoundException("Period parameter null or not recognized!");
    }
}
