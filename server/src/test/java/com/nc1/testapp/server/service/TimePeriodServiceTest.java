package com.nc1.testapp.server.service;

import com.nc1.testapp.common.utils.DayPeriodLimitsEnum;
import com.nc1.testapp.server.exception.PeriodNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimePeriodServiceTest {

    private TimePeriodService timePeriodService;

    @BeforeEach
    void setUp() {
        timePeriodService = new TimePeriodService();
    }

    @Test
    void testGetDayPeriodFromString_Morning() throws PeriodNotFoundException {
        String period = "morning";

        DayPeriodLimitsEnum result = timePeriodService.getDayPeriodFromString(period);

        assertEquals(DayPeriodLimitsEnum.MORNING, result);
    }

    @Test
    void testGetDayPeriodFromString_Day() throws PeriodNotFoundException {
        String period = "day";

        DayPeriodLimitsEnum result = timePeriodService.getDayPeriodFromString(period);

        assertEquals(DayPeriodLimitsEnum.DAY, result);
    }

    @Test
    void testGetDayPeriodFromString_Evening() throws PeriodNotFoundException {
        String period = "evening";

        DayPeriodLimitsEnum result = timePeriodService.getDayPeriodFromString(period);

        assertEquals(DayPeriodLimitsEnum.EVENING, result);
    }

    @Test
    void testGetDayPeriodFromString_PeriodNotFound() {
        String period = "night";

        Exception exception = assertThrows(PeriodNotFoundException.class, () -> {
            timePeriodService.getDayPeriodFromString(period);
        });

        assertEquals("Period parameter null or not recognized!", exception.getMessage());
    }

    @Test
    void testGetDayPeriodFromString_NullPeriod() {
        String period = null;

        Exception exception = assertThrows(PeriodNotFoundException.class, () -> {
            timePeriodService.getDayPeriodFromString(period);
        });

        assertEquals("Period parameter null or not recognized!", exception.getMessage());
    }
}
