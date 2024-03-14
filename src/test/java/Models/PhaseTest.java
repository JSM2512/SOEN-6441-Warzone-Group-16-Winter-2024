package Models;

import Controller.MainGameEngine;
import Controller.MapController;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PhaseTest {

    CurrentState d_currentState;

    MainGameEngine d_mainGameEngine;

    StartupPhase d_startupPhase;

    Map d_map;

    Player d_player;

    MapController d_mapController;


    @Before
    public void setup(){
        d_mainGameEngine = new MainGameEngine();
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_startupPhase = new StartupPhase(d_currentState, d_mainGameEngine);
        d_map = d_mapController.loadMap(d_currentState, "test.map");
        d_player = new Player("Mehak");
        d_currentState.setD_map(d_map);
        List<Player> l_players = new ArrayList<>();
        l_players.add(d_player);
        d_currentState.setD_players(l_players);

    }

    @Test
    public void correctStartupPhase() throws CommandValidationException, IOException {
        assertEquals(d_mainGameEngine.getD_currentPhase().getClass(), d_startupPhase.getClass());
        d_mainGameEngine.getD_currentPhase().loadMap(new CommandHandler("loadmap test.map"));
        assertEquals(d_mainGameEngine.getD_currentPhase().getClass(), d_startupPhase.getClass());
    }
}
