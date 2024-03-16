package Controller;

import Models.Country;
import Models.CurrentState;
import Models.Map;
import Models.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Player controller test.
 */
public class PlayerControllerTest {
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
     * The D player 1.
     */
    Player d_player1;
    /**
     * The D player 2.
     */
    Player d_player2;
    /**
     * The D map name.
     */
    String d_mapName;
    /**
     * The D player controller.
     */
    PlayerController d_playerController;
    /**
     * The L player list.
     */
    List<Player> l_playerList = new ArrayList<>();

    /**
     * Setup.
     */
    @Before
    public void setup(){
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_playerController = new PlayerController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
        d_player1 = new Player("Player1");
        d_player2 = new Player("Player2");
    }

    /**
     * Assign countries.
     */
    @Test
    public void assignCountries() {
        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_currentState.setD_players(l_playerList);
        d_playerController.assignCountries(d_currentState);
        assertEquals(2, d_player1.getD_currentCountries().size());
        assertEquals(2, d_player2.getD_currentCountries().size());
    }

    /**
     * Assign continent to players.
     */
    @Test
    public void assignContinentToPlayers() {
        l_playerList.add(d_player1);
        d_player1.setD_currentCountries(d_map.getD_mapCountries());
        d_currentState.setD_players(l_playerList);
        d_playerController.assignContinentToPlayers(l_playerList, d_map.getD_mapContinents());
        assertEquals(2, d_player1.getD_currentContinents().size());
        d_player1.getD_currentContinents().clear();
        d_player1.getD_currentCountries().remove(0);
        d_playerController.assignContinentToPlayers(l_playerList, d_map.getD_mapContinents());
        assertEquals(1, d_player1.getD_currentContinents().size());
    }

    /**
     * Assign random countries to players.
     */
    @Test
    public void assignRandomCountriesToPlayers(){
        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_playerController.assignRandomCountriesToPlayers(l_playerList, d_map.getD_mapCountries(), 2);
        assertEquals(2, d_player1.getD_currentCountries().size());
        assertEquals(2, d_player2.getD_currentCountries().size());
        List<Country> l_countryList = new ArrayList<>(d_player1.getD_currentCountries());
        l_countryList.remove(0);
        d_player1.setD_currentCountries(l_countryList);
        assertEquals(1, d_player1.getD_currentCountries().size());
    }

    /**
     * Gets no of armies.
     */
    @Test
    public void getNoOfArmies() {
        List<Country> l_countryList = new ArrayList<>();
        l_countryList.add(d_map.getCountryByName("India"));
        l_countryList.add(d_map.getCountryByName("China"));
        d_player1.setD_currentCountries(l_countryList);

        l_countryList = new ArrayList<>();
        l_countryList.add(d_map.getCountryByName("Morocco"));
        l_countryList.add(d_map.getCountryByName("Nigeria"));
        d_player2.setD_currentCountries(l_countryList);

        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_currentState.setD_players(l_playerList);
        d_playerController.assignContinentToPlayers(l_playerList, d_map.getD_mapContinents());
        d_playerController.assignArmies(d_currentState);

        assertEquals(18, d_playerController.getNoOfArmies(d_player1));
        assertEquals(13, d_playerController.getNoOfArmies(d_player2));

        d_player1.createDeployOrder("deploy India 10");
        assertEquals(8, d_player1.getD_unallocatedArmies().intValue());

        d_player2.createDeployOrder("deploy Morocco 8");
        assertEquals(5, d_player2.getD_unallocatedArmies().intValue());

        d_player1.createDeployOrder("deploy China 8");
        assertEquals(0, d_player1.getD_unallocatedArmies().intValue());

        d_player2.createDeployOrder("deploy Nigeria 5");
        assertEquals(0, d_player2.getD_unallocatedArmies().intValue());

    }

    /**
     * Create deploy order.
     */
    @Test
    public void createDeployOrder() {
        l_playerList.add(d_player1);
        d_currentState.setD_players(l_playerList);
        d_player1.setD_currentCountries(d_map.getD_mapCountries());
        d_playerController.assignCountries(d_currentState);
        d_playerController.assignArmies(d_currentState);
        d_player1.createDeployOrder("deploy India 10");
        d_player1.createDeployOrder("deploy China 8");
        assertEquals(10, d_player1.getD_unallocatedArmies().intValue());
        assertEquals(2, d_player1.getD_orders().size());
    }

    /**
     * Validate invalid deploy order.
     */
    @Test
    public void validateInvalidDeployOrder() {
        l_playerList.add(d_player1);
        d_currentState.setD_players(l_playerList);
        d_player1.setD_currentCountries(d_map.getD_mapCountries());
        d_playerController.assignCountries(d_currentState);
        d_playerController.assignArmies(d_currentState);
        d_player1.createDeployOrder("deploy India 10");
        assertEquals(18, d_player1.getD_unallocatedArmies().intValue());
        d_player1.createDeployOrder("deploy China 20");
        assertEquals(18, d_player1.getD_unallocatedArmies().intValue());
    }
}