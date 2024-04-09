package Services;

import Controller.MainGameEngine;
import Controller.MainGameEngineTest;
import Controller.MapController;
import Exceptions.CommandValidationException;
import Models.*;
import Utils.CommandHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameServiceTest {

    Player d_player1;

    Player d_player2;

    Player d_player3;
    Map d_map;
    MapController d_mapController;
    CurrentState d_currentState;

    MainGameEngine d_mainGameEngine = new MainGameEngine();
    String d_mapName;
    @Before
    public void setup() {
        d_mapController = new MapController();
        d_currentState = new CurrentState();
        d_map = new Map();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        d_player1 = new Player("Player-1");
        d_player2 = new Player("Player-2");
        d_player3 = new Player("Player-3");


    }
    @Test
    public void testSaveGame() throws CommandValidationException, IOException, ClassNotFoundException {
        StartupPhase d_currentPhase =new StartupPhase(d_currentState,d_mainGameEngine);

        List<Player> d_player_list = new ArrayList<>();
        d_player_list.add(d_player1);
        d_player_list.add(d_player2);
        d_currentState.setD_players(d_player_list);
        d_currentPhase.saveGame(new CommandHandler("savegame savedGame.txt"),d_player1);
        Phase d_phase  = new StartupPhase(new CurrentState(), new MainGameEngine());
        d_phase = GameService.loadGame("savedGame.txt");
        assertEquals(d_currentPhase.getD_currentState().getD_players().size(), d_phase.getD_currentState().getD_players().size());
    }

    @Test
    public void loadGameTest() throws CommandValidationException, IOException, ClassNotFoundException {
        StartupPhase d_currentPhase =new StartupPhase(d_currentState,d_mainGameEngine);
        List<Player> d_player_list = new ArrayList<>();
        d_player_list.add(d_player1);
        d_player_list.add(d_player2);
        d_player_list.add(d_player3);
        d_currentState.setD_players(d_player_list);
        d_currentPhase.getD_currentState().setD_players(d_player_list);
        d_currentPhase.saveGame(new CommandHandler("savegame loadGame.txt"),d_player1);
        Phase d_phase = new StartupPhase(new CurrentState(), new MainGameEngine());
        d_phase = GameService.loadGame("loadGame.txt");
        assertEquals(d_currentPhase.getD_currentState().getD_players().size(), d_phase.getD_currentState().getD_players().size());
    }
}