package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
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
    public void setContinent() {
        d_player.setContinent(d_map.getD_mapContinents().get(0));
        assertEquals("Asia",d_player.getD_currentContinents().get(0).getD_continentName());
        assertNotEquals("Europe",d_player.getD_currentContinents().get(0).getD_continentName());
    }

    @Test
    public void nextOrder() {
        Orders l_order1 = new Orders("deploy","India",3);
        Orders l_order2 = new Orders("deploy","China",4);
        List<Orders> l_orderlist = new ArrayList<>();
        l_orderlist.add(l_order1);
        l_orderlist.add(l_order2);
        d_player.setD_orders(l_orderlist);
        assertEquals(l_order1, d_player.nextOrder());
        assertEquals(l_order2, d_player.nextOrder());
    }
}