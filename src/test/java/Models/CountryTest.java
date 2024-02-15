package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTest {

    Map d_map;
    String d_mapName;
    CurrentState d_currentState;
    MapController d_mapController;

    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }


    @Test
    public void removeCountryNeighbourIfPresent() {
        Country l_country = d_map.getCountryByName("India");
        assertEquals("[2, 4]",l_country.getD_neighbouringCountriesId().toString());
        l_country.removeCountryNeighbourIfPresent(2);
        assertEquals("[4]",l_country.getD_neighbouringCountriesId().toString());
    }
}