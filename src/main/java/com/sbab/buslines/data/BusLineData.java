package com.sbab.buslines.data;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BusLineData {

    private BusLineCollector collector;
    public BusLineData() {}

    public BusLineData(BusLineCollector collector) {
        init(collector);
    }

    public void init(BusLineCollector collector) {
        this.collector = collector;
    }

    private Set<Integer> getBusLines() {
        return collector.getLines().stream()
                .filter( line -> line.defaultTransportModeCode().equals("BUS"))
                .map(Types.Line::lineNumber)
                .collect(Collectors.toSet());
    }

    private Map<Integer, Types.Stop> getStops() {
        HashMap<Integer, Types.Stop> map = new HashMap<>();
        for (Types.Stop site : collector.getSites()) {
            map.put(site.stopNumber(), site);
        }
        return map;
    }

    public Map<Integer, List<String>> getLines() {
        Set<Integer> busLines = getBusLines();
        Map<Integer, Types.Stop> busStops = getStops();
        Map<Integer, List<String>> lines = new HashMap<>();
        Map<Integer, Set<Integer>> stopAreasOnLine = new HashMap<>();
        for (Types.JourneyPoint jp : collector.getJps()) {
            if (!busLines.contains(jp.lineNumber())) {
                continue;
            }

            if (!lines.containsKey(jp.lineNumber())) {
                lines.put(jp.lineNumber(), new ArrayList<>());
                stopAreasOnLine.put(jp.lineNumber(), new HashSet<>());
            }
            Types.Stop stop = busStops.get(jp.journeyPatternPointNumber());
            if (stop != null && !stopAreasOnLine.get(jp.lineNumber()).contains(stop.stopAreaNumber())) {
                lines.get(jp.lineNumber()).add(stop.siteName());
                stopAreasOnLine.get(jp.lineNumber()).add(stop.stopAreaNumber());
            }
        }
        return lines;
    }
}
