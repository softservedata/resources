package org.registrator.community.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.registrator.community.exceptions.ExternalApiCallException;

import java.io.IOException;
import java.net.URL;
import java.util.TimeZone;

/**
 * Implementation of time zone search by position based on web-service timezonedb.com
 */
public class TimeZoneSearchTimeDB implements TimeZoneSearch {
    private static final String TIME_ZONE_API =
            "http://api.timezonedb.com/" +
            "?key=9PNPBC03RVRQ&by=position&lat=%s&lng=%s&format=json";
    private final CityPosition cityPosition;

    public TimeZoneSearchTimeDB(CityPosition cityPosition) {
        this.cityPosition = cityPosition;
    }

    @Override
    public TimeZone findTimeZoneByPosition() throws ExternalApiCallException {
        String hostname = String.format(TIME_ZONE_API, cityPosition.getLat(), cityPosition.getLon());
        ObjectMapper mapper = new ObjectMapper();
        TimeDbResponse response;
        try {
            response = mapper.readValue(new URL(hostname), TimeDbResponse.class);
        } catch (IOException e) {
            throw new ExternalApiCallException("Error when calling external API", e);
        }
        if (!"OK".equals(response.getStatus())){
            throw new ExternalApiCallException("Call to timezonedb.com failed, reason: " + response.getStatus());
        }

        return TimeZone.getTimeZone(response.getZoneName());
    }

    static class TimeDbResponse {
        private String status;
        private String message;
        private String countryCode;
        private String zoneName;
        private String abbreviation;
        private String gmtOffset;
        private String dst;
        private String timestamp;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getZoneName() {
            return zoneName;
        }

        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getGmtOffset() {
            return gmtOffset;
        }

        public void setGmtOffset(String gmtOffset) {
            this.gmtOffset = gmtOffset;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

}


