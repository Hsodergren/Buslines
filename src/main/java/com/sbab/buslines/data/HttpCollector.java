package com.sbab.buslines.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;



public class HttpCollector implements BusLineCollector {
    RestClient restclient;
    private final String APIKEY = "014364b70ff04b7db715a25fc3aa2bfe";
    private List<Types.Line> lines;
    private List<Types.Stop> sites;
    private List<Types.JourneyPoint> jps;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Response<A>(@JsonProperty("ResponseData") Result<A> data) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Result<A>(@JsonProperty("Result") List<A> data) {}

    public HttpCollector() {
        restclient = RestClient.builder().baseUrl("https://api.sl.se/api2/LineData.json").build();
    }
    @Override
    public List<Types.Line> getLines() {
        if (lines == null) {
            Response<Types.Line> resp = restclient.get().uri("?model=line&key=" + APIKEY)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            lines = resp == null ? null : resp.data().data();

        }
        return lines;
    }

    @Override
    public List<Types.Stop> getSites() {
        if (sites == null) {
            Response<Types.Stop> resp = restclient.get().uri("?model=stop&key=" + APIKEY)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            sites = resp == null ? null : resp.data().data();
        }
        return sites;
    }

    @Override
    public List<Types.JourneyPoint> getJps() {
        if (jps == null) {
            Response<Types.JourneyPoint> resp = restclient.get().uri("?model=jour&key=" + APIKEY)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            jps = resp == null ? null : resp.data().data();
        }
        return jps;
    }
}
