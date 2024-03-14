package Models;

import Controller.MapController;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class AdvanceOrderTest {
    CurrentState d_currentState;
    Player d_player1;
    Player d_player2;
    Map d_map;
    MapController d_mapController = new MapController();

    @Before
    public void setup(){
        d_player1 = new Player("mehak");
        d_player2 = new Player("amir");
        d_currentState = new CurrentState();

        d_map = d_mapController.loadMap(d_currentState, "test.map");
        List<Player> l_players = new ArrayList<>();
        l_players.add(d_player1);
        l_players.add(d_player2);
        d_currentState.setD_players(l_players);
        d_currentState.setD_map(d_map);
        List<Country> l_player1Countries = new ArrayList<>();
        List<Country> l_player2Countries = new ArrayList<>();
        d_map.getCountryByName("India").setD_armies(10);
        d_map.getCountryByName("China").setD_armies(0);
        l_player1Countries.add(d_map.getCountryByName("India"));
        l_player2Countries.add(d_map.getCountryByName("China"));
        d_player1.setD_currentCountries(l_player1Countries);
        d_player2.setD_currentCountries(l_player2Countries);
    }

    @Test
    public void execute(){
        Orders l_advanceOrder = new Advance("India","China",9,d_player1);
        l_advanceOrder.execute(d_currentState);
        Assert.assertEquals("9",d_map.getCountryByName("China").getD_armies().toString());
    }
}
