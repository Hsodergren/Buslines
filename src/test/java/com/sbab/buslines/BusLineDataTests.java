package com.sbab.buslines;

import com.sbab.buslines.data.BusLineCollector;
import com.sbab.buslines.data.BusLineData;
import com.sbab.buslines.data.Types;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BusLineDataTests {

    BusLineCollector collector = new BusLineCollector() {
        @Override
        public List<Types.Line> getLines() {
            return List.of(new Types.Line(1,"BUS"),
                    new Types.Line(2,"BUS"),
                    new Types.Line(3,"SHIP"));
        }

        @Override
        public List<Types.Stop> getSites() {
            return List.of(new Types.Stop(1,1,"A"),
                    new Types.Stop(2,1,"A"),
                    new Types.Stop(3,3,"B"),
                    new Types.Stop(4,3,"B"),
                    new Types.Stop(5,5,"C"),
                    new Types.Stop(6,5,"C"),
                    new Types.Stop(7,7,"D"),
                    new Types.Stop(8,8,"E"),
                    new Types.Stop(9,9,"F"));
        }

        @Override
        public List<Types.JourneyPoint> getJps() {
            List<Types.JourneyPoint> l1 = List.of(new Types.JourneyPoint(1,1),
                    new Types.JourneyPoint(1,3),
                    new Types.JourneyPoint(1,5),
                    new Types.JourneyPoint(1,2),
                    new Types.JourneyPoint(1,4),
                    new Types.JourneyPoint(1,6));

            List<Types.JourneyPoint> l2 = List.of(new Types.JourneyPoint(2,5),
                    new Types.JourneyPoint(2,7),
                    new Types.JourneyPoint(2,8),
                    new Types.JourneyPoint(2,3),
                    new Types.JourneyPoint(2,6));

            List<Types.JourneyPoint> l3 = List.of(new Types.JourneyPoint(3,7),
                    new Types.JourneyPoint(3,8),
                    new Types.JourneyPoint(3,9));
            List<Types.JourneyPoint> ret = new ArrayList<>();
            ret.addAll(l1);
            ret.addAll(l2);
            ret.addAll(l3);
            return ret;
        }
    };

    BusLineData data = new BusLineData(collector);


    @Test
    void TestShouldReturnTwoLines() {
        assertEquals(data.getLines().size(),2);
    }

    @Test
    void TestShouldReturnCorrectStops() {
        Map<Integer, List<String>> lines = data.getLines();
        assertEquals(lines.get(1), List.of("A","B","C"));
        assertEquals(lines.get(2), List.of("C","D","E","B"));
    }

    @Test
    void TestGetAllNumberOfStops() {
        Service s = new Service(data);
        List<Service.NumStopOnLine> expected =
                List.of(new Service.NumStopOnLine(2,4),
                        new Service.NumStopOnLine(1,3));
        assertEquals(expected, s.numStopOnLines(null));
    }

    @Test
    void TestGetLineWithSingleId() {
        Service s = new Service(data);
        assertEquals(Map.of(2, List.of("C","D","E","B")),
                     s.getLines(List.of(2)));

    }

    @Test
    void TestGetLineWithMultipleIds() {
        Service s = new Service(data);
        assertEquals(Map.of(2, List.of("C","D","E","B"),1, List.of("A","B","C")),
                s.getLines(List.of(2,1)));
    }
}
