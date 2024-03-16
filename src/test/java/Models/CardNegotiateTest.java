package Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Card negotiate test.
 */
public class CardNegotiateTest {
    /**
     * The D map.
     */
    Map d_map;
    /**
     * The D player 1.
     */
    Player d_player1;
    /**
     * The D player 2.
     */
    Player d_player2;
    /**
     * The D card negotiate.
     */
    CardNegotiate d_cardNegotiate;
    /**
     * The D current state.
     */
    CurrentState d_currentState;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        d_currentState = new CurrentState();
        d_player1 = new Player("player1");
        d_player2 = new Player("player2");

        List<Country> l_countryList1 = new ArrayList<>();
        List<Country> l_countryList2 = new ArrayList<>();
        List<Country> l_mapCountries = new ArrayList<>();

        Country l_country1 = new Country(1,"India",1);
        l_country1.setD_armies(10);
        l_countryList1.add(l_country1);
        l_mapCountries.add(l_country1);
        d_player1.setD_currentCountries(l_countryList1);

        Country l_country2 = new Country(2,"Morocco",1);
        l_country2.setD_armies(5);
        l_countryList2.add(l_country2);
        l_mapCountries.add(l_country2);
        d_player2.setD_currentCountries(l_countryList2);

        l_country2.addCountryNeighbour(1);
        l_country1.addCountryNeighbour(2);

        d_cardNegotiate = new CardNegotiate(d_player1, "player2");
        List<Player> l_playerList = new ArrayList<>();
        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_currentState.setD_players(l_playerList);
    }

    /**
     * Execute.
     */
    @Test
    public void execute() {
        d_cardNegotiate.execute(d_currentState);
        Orders advanceOrder = new Advance("India","Morocco",5,d_player1);
        assertFalse(advanceOrder.valid(d_currentState));
        assertEquals(d_player2.d_name,d_player1.d_negotiatePlayers.get(0).d_name);
    }
}