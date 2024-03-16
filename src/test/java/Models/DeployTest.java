package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Deploy test.
 */
public class DeployTest {
    /**
     * The D map.
     */
    Map d_map;
    /**
     * The D player.
     */
    Player d_player;
    /**
     * The D current state.
     */
    CurrentState d_currentState;
    /**
     * The D map controller.
     */
    MapController d_mapController;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        d_map = new Map();
        d_mapController = new MapController();
        d_player = new Player("Player1");
        d_currentState = new CurrentState();
        d_map = d_mapController.loadMap(d_currentState,"test.map");
        List<Country> l_countryList = d_map.getD_mapCountries();
        d_player.setD_currentCountries(l_countryList);
    }

    /**
     * Execute.
     */
    @Test
    public void execute() {
        Deploy l_deploy = new Deploy(d_player,"India",5);
        l_deploy.execute(d_currentState);
        assertEquals("5",d_currentState.getD_map().getD_mapCountries().get(0).getD_armies().toString());
    }
}
