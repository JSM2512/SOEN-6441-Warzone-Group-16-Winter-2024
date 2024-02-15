package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrdersTest {
    Player d_player;
    Map d_map;
    MapController d_mapController;
    CurrentState d_currentState;
    String d_mapName;

    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_player = new Player("Player 1");
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState,d_mapName);
    }

    @Test
    public void execute() {
        d_player.setD_currentCountries(d_map.getD_mapCountries());
        Orders l_order1 = new Orders("deploy","India",3);
        Orders l_order2 = new Orders("deploy","China",4);
        l_order1.execute(d_player);
        assertEquals("3", d_map.getCountryByName("India").getD_armies().toString());
        l_order2.execute(d_player);
        assertEquals("4", d_map.getCountryByName("China").getD_armies().toString());
        assertNotEquals("3", d_map.getCountryByName("Morocco").getD_armies().toString());
    }
}