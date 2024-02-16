package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Country test.
 */
public class CountryTest {

    /**
     * The D map.
     */
    Map d_map;
    /**
     * The D map name.
     */
    String d_mapName;
    /**
     * The D current state.
     */
    CurrentState d_currentState;
    /**
     * The D map controller.
     */
    MapController d_mapController;

    /**
     * Sets .
     */
    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }


    /**
     * Remove country neighbour if present.
     */
    @Test
    public void removeCountryNeighbourIfPresent() {
        Country l_country = d_map.getCountryByName("India");
        assertEquals("[2, 4]",l_country.getD_neighbouringCountriesId().toString());
        l_country.removeCountryNeighbourIfPresent(2);
        assertEquals("[4]",l_country.getD_neighbouringCountriesId().toString());
    }
}