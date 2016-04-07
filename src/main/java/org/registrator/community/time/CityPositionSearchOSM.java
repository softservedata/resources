package org.registrator.community.time;

import org.registrator.community.exceptions.ExternalApiCallException;
import org.registrator.community.time.osm.WrapperOSM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class CityPositionSearchOSM implements CityPositionSearch {
    private static final String queryTemplate = "node[place=city]['name:%s'~'^%s'];out;";
    private static final Logger LOGGER = LoggerFactory.getLogger(CityPositionSearchOSM.class);

    private final String cityName;
    private final Locale locale;

    public CityPositionSearchOSM(String cityName, Locale locale) {
        this.cityName = cityName;
        this.locale = locale;
    }

    @Override
    public List<CityPosition> findCities() throws ExternalApiCallException {

        List<CityPosition> nodesOSM;
        String query = String.format(queryTemplate, locale.getLanguage(), cityName);
        try {
            nodesOSM = WrapperOSM.getNodes(WrapperOSM.getNodesViaOverpass(query));
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            throw new ExternalApiCallException("Couldn't execute OpenStreetMap query", ex);
        }

        LOGGER.info(String.format("City points search by name '%s', found %s", cityName, nodesOSM));
        return nodesOSM;
    }
}
