package com.sbab.buslines;

import com.sbab.buslines.data.BusLineData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Service {
    @Autowired
    private BusLineData data;

    public record NumStopOnLine(int line, int numStops) {}

    public Service(BusLineData data) {
        this.data = data;
    }
    /**
     * @return all stops on all lines.
     */
    public Map<Integer, List<String>> getLines() {
        return data.getLines();
    }

    /**
     * @param ids line ids to return.
     * @return all stops on the given lines.
     */
    public Map<Integer, List<String>> getLines(List<Integer> ids) {
        Map<Integer, List<String>> allLines = data.getLines();
        HashMap<Integer, List<String>> selectedLines = new HashMap<>();
        for (Integer id : ids) {
            List<String> stops = allLines.get(id);
            if (stops != null) {
                selectedLines.put(id, stops);
            }
        }
        return selectedLines;
    }

    /**
     * @param num the number of lines to return. if num is null, return all lines.
     * @return a sorted list of the [num] lines with most stops.
     */
    public List<NumStopOnLine> numStopOnLines(Integer num) {
        Stream<NumStopOnLine> stream = getLines().entrySet().stream()
                .sorted((a,b) -> Integer.compare(b.getValue().size(), a.getValue().size()))
                .map(a -> new NumStopOnLine(a.getKey(), a.getValue().size()));
        if (num != null) {
            return stream.limit(num).collect(Collectors.toList());
        } else {
            return stream.collect(Collectors.toList());
        }
    }
}
