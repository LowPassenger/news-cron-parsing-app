package com.nc1.testapp.server.service;

import com.nc1.testapp.common.utils.DayPeriodLimitsEnum;
import com.nc1.testapp.server.exception.PeriodNotFoundException;
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
