package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Continent test.
 */
public class ContinentTest {
    /**
     * The D current state.
     */
    CurrentState d_currentState;
    /**
     * The D map controller.
     */
    MapController d_mapController;
    /**
     * The D map name.
     */
    String d_mapName;
    /**
     * The D map.
     */
    Map d_map;

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
     * Add country.
     */
    @Test
    public void addCountry() {
        d_map.addCountry("Pakistan", "Asia");
        d_map.addCountry("Nepal", "Asia");
        assertEquals("Pakistan", d_map.getCountryByName("Pakistan").getD_countryName());
        assertEquals("Nepal", d_map.getCountryByName("Nepal").getD_countryName());
    }

    /**
     * Remove country.
     */
    @Test
    public void RemoveCountry() {
        d_map.addCountry("Pakistan", "Asia");
        d_map.addCountry("Nepal", "Asia");
        d_map.removeCountry("Pakistan");
        assertNull(d_map.getCountryByName("Pakistan"));
        d_map.removeCountry("Nepal");
        assertNotEquals(d_map.getD_mapCountries().get(d_map.getD_mapCountries().size() - 1).getD_countryName(), "Nepal");
    }
}