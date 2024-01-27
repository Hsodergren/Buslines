package com.sbab.buslines;

import com.sbab.buslines.data.BusLineData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    Service service;

    @GetMapping("/lines")
    public Map<Integer, List<String>> lines() {
        return service.getLines();
    }

    @GetMapping("/lines/{ids}")
    public Map<Integer, List<String>> linesIds(@PathVariable("ids") List<Integer> ids) {
        return service.getLines(ids);
    }

    @GetMapping("/moststops")
    public List<Service.NumStopOnLine> mostStops(@RequestParam Optional<Integer> num) {
        if (num.isPresent()) {
            return service.numStopOnLines(num.get());
        } else {
            return service.numStopOnLines(null);
        }
    }
}
