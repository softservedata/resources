package org.registrator.community.controller;

import org.registrator.community.dto.TimeZoneDTO;
import org.registrator.community.service.TimeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller for providing REST service for suggesting time zone by name or city
 */
@Controller
public class TimeZoneSelection {
    @Autowired
    private TimeZoneService timeZoneService;
    /**
     * Find the list of suitable time zones by name or by city
     *
     * @param searchValue either name of the time zone or city name to look up for time zone
     *                    name of the city must be in current locale of the user
     * @return list of suitable time zones
     */
    @ResponseBody
    @RequestMapping(value = "/timeZones", method = RequestMethod.GET)
    public List<TimeZoneDTO> getTimeZonesSuggestions(@RequestParam("value") String searchValue) {
        List<TimeZoneDTO> result = timeZoneService.findByName(searchValue);
        return result;
    }


}
