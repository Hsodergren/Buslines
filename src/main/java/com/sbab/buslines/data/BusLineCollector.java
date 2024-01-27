package com.sbab.buslines.data;

import java.util.List;

public interface BusLineCollector {
    List<Types.Line> getLines();
    List<Types.Stop> getSites();
    List<Types.JourneyPoint> getJps();
}
