package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * The type Current state test.
 */
public class CurrentStateTest {
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
        d_mapController = new MapController();
        d_currentState = new CurrentState();
        d_map = new Map();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }

    /**
     * Add or remove game players.
     *
     * @throws IOException the io exception
     */
    @Test
    public void addOrRemoveGamePlayers() throws IOException {
        InputStream originalSystemIn = System.in;
        d_currentState.setD_players(new ArrayList<>());
        ByteArrayInputStream in = new ByteArrayInputStream("Aggressive\n".getBytes());
        System.setIn(in);
        d_currentState.addOrRemoveGamePlayers("add", "Player1");
        assertEquals(1, d_currentState.getD_players().size());
        d_currentState.addOrRemoveGamePlayers("remove", "Player1");
        assertEquals(0, d_currentState.getD_players().size());
        System.setIn(originalSystemIn);
    }
}