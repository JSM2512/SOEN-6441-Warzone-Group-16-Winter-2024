package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContinentTest {
    CurrentState d_currentState;
    MapController d_mapController;
    String d_mapName;
    Map d_map;

    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }

    @Test
    public void addCountry() {
        d_map.addCountry("Pakistan", "Asia");
        d_map.addCountry("Nepal", "Asia");
        assertEquals("Pakistan", d_map.getCountryByName("Pakistan").getD_countryName());
        assertEquals("Nepal", d_map.getCountryByName("Nepal").getD_countryName());
    }

    @Test
    public void testRemoveCountry() {
        d_map.addCountry("Pakistan", "Asia");
        d_map.addCountry("Nepal", "Asia");
        d_map.removeCountry("Pakistan");
        assertNull(d_map.getCountryByName("Pakistan"));
        d_map.removeCountry("Nepal");
        assertNotEquals(d_map.getD_mapCountries().get(d_map.getD_mapCountries().size() - 1).getD_countryName(), "Nepal");
    }
}