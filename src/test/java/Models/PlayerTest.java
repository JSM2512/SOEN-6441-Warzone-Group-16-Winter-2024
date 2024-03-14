package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Player test.
 */
public class PlayerTest {
    /**
     * The D player.
     */
    Player d_player;
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
     * Sets .
     */
    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_player = new Player("Player 1");
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState,d_mapName);
    }

    /**
     * Sets continent.
     */
    @Test
    public void setContinent() {
        d_player.setContinent(d_map.getD_mapContinents().get(0));
        assertEquals("Asia",d_player.getD_currentContinents().get(0).getD_continentName());
        assertNotEquals("Europe",d_player.getD_currentContinents().get(0).getD_continentName());
    }

    /**
     * Next order.
     */
    @Test
    public void nextOrder() {
        Orders l_order1 = new Deploy(d_player,"India",3);
        Orders l_order2 = new Deploy(d_player,"China",4);
        List<Orders> l_orderlist = new ArrayList<>();
        l_orderlist.add(l_order1);
        l_orderlist.add(l_order2);
        d_player.setD_orders(l_orderlist);
        assertEquals(l_order1, d_player.nextOrder());
        assertEquals(l_order2, d_player.nextOrder());
    }
}