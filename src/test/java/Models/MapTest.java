package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Map test.
 */
public class MapTest {
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
     * Setup.
     */
    @Before
    public void setup(){
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }

    /**
     * Gets country by name.
     */
    @Test
    public void getCountryByName() {
        assertEquals(3, (int) d_map.getCountryByName("Morocco").getD_countryID());
        assertNotEquals(2, (int) d_map.getCountryByName("India").getD_countryID());
    }

    /**
     * Gets continent by name.
     */
    @Test
    public void getContinentByName() {
        assertEquals(10, (int) d_map.getContinentByName("Africa").getD_continentValue());
        assertNotEquals(10, (int) d_map.getContinentByName("Asia").getD_continentValue());
    }

    /**
     * Validate map.
     */
    @Test
    public void validateMap() {
        assertTrue(d_mapController.loadMap(d_currentState,"testconquest.map").validateMap());
        assertFalse(d_mapController.loadMap(d_currentState,"testInvalid.map").validateMap());
    }

    /**
     * Validate countries and continents.
     */
    @Test
    public void validateCountriesAndContinents() {
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        assertTrue(d_map.validateCountriesAndContinents());

        List<Continent> l_continents = new ArrayList<>();
        d_map.setD_mapContinents(l_continents);
        assertFalse(d_map.validateCountriesAndContinents());

    }

    /**
     * Add continent.
     */
    @Test
    public void addContinent() {
        d_map = new Map();
        d_map.addContinent("Europe",35);
        assertEquals("Europe",d_map.getD_mapContinents().get(0).getD_continentName());
    }

    /**
     * Remove continent.
     */
    @Test
    public void removeContinent() {
        assertEquals("Asia", d_map.getD_mapContinents().get(0).getD_continentName());
        d_map.removeContinent("Asia");
        assertEquals("Africa", d_map.getD_mapContinents().get(0).getD_continentName());
    }

    /**
     * Add country.
     */
    @Test
    public void addCountry() {
        d_map = new Map();
        d_map.addContinent("Asia",10);
        d_map.addCountry("Sri Lanka","Asia");
        assertEquals("Sri Lanka",d_map.getD_mapCountries().get(0).getD_countryName());

    }

    /**
     * Add neighbour.
     */
    @Test
    public void addNeighbour() {

        d_map.addNeighbour(1,3);

        List<Integer> l_neighbourCountryIdListIndia = d_map.getCountryByName("India").getD_neighbouringCountriesId();
        List<Integer> l_neighbourCountryIdListMorocco = d_map.getCountryByName("Morocco").getD_neighbouringCountriesId();

        assertEquals("[2, 4, 3]",l_neighbourCountryIdListIndia.toString());
        assertEquals("[4, 2, 1]",l_neighbourCountryIdListMorocco.toString());


    }

    /**
     * Remove neighbour.
     */
    @Test
    public void removeNeighbour() {
        d_map.removeNeighbour(1,4);

        List<Integer> l_neighbourCountryIdListIndia = d_map.getCountryByName("India").getD_neighbouringCountriesId();
        List<Integer> l_neighbourCountryIdListNigeria = d_map.getCountryByName("Nigeria").getD_neighbouringCountriesId();

        assertEquals("[2]",l_neighbourCountryIdListIndia.toString());
        assertEquals("[2, 3]",l_neighbourCountryIdListNigeria.toString());
    }

    /**
     * Remove country.
     */
    @Test
    public void removeCountry() {
        assertEquals("India",d_map.getD_mapCountries().get(0).getD_countryName());
        d_map.removeCountry("India");
        assertEquals("China",d_map.getD_mapCountries().get(0).getD_countryName());
    }

    /**
     * Validate continent subgraph connectivity.
     */
    @Test
    public void validateContinentSubgraphConnectivity(){
        assertTrue(d_map.validateContinentSubgraph());
        d_map.removeNeighbour(1,2);
        assertFalse(d_map.validateContinentSubgraph());
    }

    /**
     * Validate map is a connected graph of countries.
     */
    @Test
    public void validateMapIsAConnectedGraphOfCountries(){
        assertTrue(d_map.validateCountryConnections());
        d_map.removeNeighbour(3,2);
        d_map.removeNeighbour(3,4);
        assertFalse(d_map.validateCountryConnections());
    }
}