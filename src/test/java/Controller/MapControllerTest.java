package Controller;

import Models.CurrentState;
import Models.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Map controller test.
 */
public class MapControllerTest {

    /**
     * The D map.
     */
    Map d_map;
    /**
     * The D map controller.
     */
    MapController d_mapController;
    /**
     * The D current state.
     */
    CurrentState d_currentState;
    /**
     * The D map name.
     */
    String d_mapName;
    /**
     * The D player controller.
     */
    PlayerController d_playerController;

    /**
     * Setup.
     */
    @Before
    public void setup(){
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_playerController = new PlayerController();
        d_mapName = "test.map";
    }

    /**
     * Load map.
     */
    @Test
    public void loadMap() {
        d_mapController.loadMap(d_currentState, "Invalid.map");
        assertNull(d_currentState.getD_map());
        d_mapController.loadMap(d_currentState, "test.map");
        assertFalse(d_currentState.getD_map().toString().isEmpty());
    }

    /**
     * Edit country.
     */
    @Test
    public void editCountry() {
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        d_currentState.setD_map(d_map);
        d_mapController.editCountry(d_currentState, "add", "Paris Asia");
        assertEquals("Paris", d_map.getCountryByName("Paris").getD_countryName());
        d_mapController.editCountry(d_currentState, "remove", "Paris");
        assertNull(d_map.getCountryByName("Paris"));

    }

    /**
     * Edit continent.
     */
    @Test
    public void editContinent() {
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        d_currentState.setD_map(d_map);
        d_mapController.editContinent(d_currentState, "add", "Europe 30");
        assertEquals("Europe", d_map.getContinentByName("Europe").getD_continentName());
        d_mapController.editContinent(d_currentState, "remove", "Europe");
        assertNull(d_map.getContinentByName("Europe"));
    }

    /**
     * Edit neighbour country.
     */
    @Test
    public void editNeighbourCountry() {
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        d_currentState.setD_map(d_map);
        d_mapController.editNeighbourCountry(d_currentState, "add", "1 3");
        assertEquals("[2, 4, 3]", d_map.getCountryByName("India").getD_neighbouringCountriesId().toString());
        assertEquals("[4, 2, 1]", d_map.getCountryByName("Morocco").getD_neighbouringCountriesId().toString());

        d_mapController.editNeighbourCountry(d_currentState, "remove", "1 3");
        assertEquals("[2, 4]", d_map.getCountryByName("India").getD_neighbouringCountriesId().toString());
        assertEquals("[4, 2]", d_map.getCountryByName("Morocco").getD_neighbouringCountriesId().toString());
    }

    /**
     * Save map.
     */
    @Ignore
    @Test
    public void saveMap() {
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        d_currentState.setD_map(d_map);
        assertNull(d_map.getCountryByName("Paris"));

        d_mapController.editCountry(d_currentState, "add", "Paris Asia");
        d_mapController.editNeighbourCountry(d_currentState, "add", "5 2");
        assertEquals("Paris", d_map.getCountryByName("Paris").getD_countryName());

        d_currentState.setD_map(d_map);
        d_mapController.saveMap(d_currentState, d_mapName);
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        assertEquals("Paris", d_map.getCountryByName("Paris").getD_countryName());
        d_mapController.editCountry(d_currentState, "remove", "Paris");
        d_mapController.saveMap(d_currentState, d_mapName);
    }
}