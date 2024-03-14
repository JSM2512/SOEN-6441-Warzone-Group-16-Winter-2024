package Models;

import Controller.MainGameEngine;
import Controller.MapController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderExecutionPhaseTest {
    Map d_map;
    MapController d_mapController;
    CurrentState d_currentState;
    MainGameEngine d_mainGameEngine;
    Player d_player1;
    Player d_player2;
    OrderExecutionPhase d_orderExecutionPhase;

    @Before
    public void setUp() {
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mainGameEngine = new MainGameEngine();

        d_map = d_mapController.loadMap(d_currentState, "test.map");
        d_currentState.setD_map(d_map);

        d_player1 = new Player("Player1");
        d_player2 = new Player("Player2");
        List<Player> l_players = List.of(d_player1, d_player2);
        d_currentState.setD_players(l_players);

        List<Country> l_player1Countries = new ArrayList<>();
        List<Country> l_player2Countries = new ArrayList<>();
        d_map.getCountryByName("India").setD_armies(10);
        d_map.getCountryByName("China").setD_armies(0);
        l_player1Countries.add(d_map.getCountryByName("India"));
        l_player1Countries.add(d_map.getCountryByName("Nigeria"));
        l_player1Countries.add(d_map.getCountryByName("Morocco"));
        l_player2Countries.add(d_map.getCountryByName("China"));
        d_player1.setD_currentCountries(l_player1Countries);
        d_player2.setD_currentCountries(l_player2Countries);

        d_orderExecutionPhase = new OrderExecutionPhase(d_currentState, d_mainGameEngine);
    }

    @Test
    public void checkEndOfGame() {
        assertFalse(d_orderExecutionPhase.checkEndOfGame(d_currentState));
        Orders l_advanceOrder = new Advance("India", "China", 9, d_player1);
        l_advanceOrder.execute(d_currentState);
        assertTrue(d_orderExecutionPhase.checkEndOfGame(d_currentState));
    }
}