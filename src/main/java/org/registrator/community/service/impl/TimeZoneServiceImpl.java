package org.registrator.community.service.impl;

import org.registrator.community.dto.TimeZoneDTO;
import org.registrator.community.exceptions.ExternalApiCallException;
import org.registrator.community.service.TimeZoneService;
import org.registrator.community.time.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeZoneServiceImpl implements TimeZoneService {
    @Override
    public List<TimeZoneDTO> findByName(String searchValue) {

        List<TimeZoneDTO> result = Collections.emptyList();
        if ((searchValue != null) && (!searchValue.isEmpty())) {
            result = Arrays.stream(TimeZone.getAvailableIDs())
                    .filter(id -> id.contains(searchValue))
                    .map(TimeZoneDTO::from)
                    .collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<TimeZoneDTO> findByCity(String searchValue, Locale locale) throws ExternalApiCallException {
        CityPositionSearch cityPositionSearch = new CityPositionSearchOSM(searchValue, locale);
        List<CityPosition> points = cityPositionSearch.findCities();
        List<TimeZoneDTO> result = new ArrayList<>();

        for (CityPosition cityPosition: points ) {
            TimeZoneSearch timeZoneSearch = new TimeZoneSearchTimeDB(cityPosition);
            TimeZone timeZone = timeZoneSearch.findTimeZoneByPosition();
            result.add(new TimeZoneDTO(timeZone.getID(), timeZone.getID(), cityPosition.getName(locale.getLanguage())));
        }
        return result;
    }

    @Override
    public List<TimeZoneDTO> findByNameOrCity(String searchValue, Locale locale) throws Exception {
        List<TimeZoneDTO> result = findByName(searchValue);
        if (result.size() > 0) {
            return result;
        }

        return findByCity(searchValue, locale);
    }
}
