package Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Card blockade test.
 */
public class CardBlockadeTest {
    /**
     * The D current state.
     */
    CurrentState d_currentState;

    /**
     * The D card blockade order 1.
     */
    CardBlockade d_cardBlockadeOrder1;
    /**
     * The D card blockade order 2.
     */
    CardBlockade d_cardBlockadeOrder2;
    /**
     * The D player 1.
     */
    Player d_player1;

    /**
     * Sets .
     */
    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_player1 = new Player("player1");

        Player d_player2 = new Player("Neutral");

        List<Player> l_playersList = new ArrayList<>();

        l_playersList.add(d_player1);
        l_playersList.add(d_player2);
        List<Country> l_countryList1 = new ArrayList<>();

        Country l_country1 = new Country(1,"India",1);
        l_countryList1.add(l_country1);


        Country l_country2 = new Country(3,"Nigeria",1);
        l_countryList1.add(l_country2);

        List<Country> l_mapCountries = new ArrayList<>();
        Country l_sourceCountry = new Country(1,"India",1);
        Country l_countryToBeBlocked = new Country(3,"Nigeria",1);
        l_countryToBeBlocked.setD_armies(5);
        l_mapCountries.add(l_sourceCountry);
        l_mapCountries.add(l_countryToBeBlocked);


        Map l_map = new Map();
        l_map.setD_mapCountries(l_countryList1);
        l_map.setD_mapCountries(l_mapCountries);
        d_player1.setD_currentCountries(l_countryList1);
        d_currentState.setD_players(l_playersList);
        d_currentState.setD_map(l_map);

        d_cardBlockadeOrder1 = new CardBlockade(d_player1, "Nigeria");
        d_cardBlockadeOrder2 = new CardBlockade(d_player1, "Brazil");
    }

    /**
     * Valid order check.
     */
    @Test
    public void validOrderCheck() {
        boolean l_actualBoolean = d_cardBlockadeOrder1.valid(d_currentState);
        assertTrue(l_actualBoolean);

        boolean l_actualBoolean2 = d_cardBlockadeOrder2.valid(d_currentState);
        assertFalse(l_actualBoolean2);
    }

    /**
     * Execute.
     */
    @Test
    public void execute() {
        d_cardBlockadeOrder1.execute(d_currentState);
        Country l_blockedCountry = d_currentState.getD_map().getCountryByName("Nigeria");

        assertEquals("15", l_blockedCountry.getD_armies().toString());
    }
}