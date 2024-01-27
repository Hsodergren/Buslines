package com.sbab.buslines.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class Types {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record JourneyPoint(@JsonProperty("LineNumber") int lineNumber,
                               @JsonProperty("JourneyPatternPointNumber") int journeyPatternPointNumber) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Stop(@JsonProperty("StopPointNumber") int stopNumber,
                       @JsonProperty("StopAreaNumber") int stopAreaNumber,
                       @JsonProperty("StopPointName") String siteName) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Line(@JsonProperty("LineNumber") int lineNumber,
                       @JsonProperty("DefaultTransportModeCode") String defaultTransportModeCode) {}
}
