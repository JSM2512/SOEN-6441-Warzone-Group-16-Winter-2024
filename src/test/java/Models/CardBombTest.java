package Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CardBombTest {
    CurrentState d_currentState;

    CardBomb d_cardBombOrder;

    Card d_cardBombOrder1;

    Player d_player1;
    Player d_player2;

    Map l_map;

    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_player1 = new Player("Amir");
        d_player2 = new Player("Mehak");

        List<Country> l_countryList1 = new ArrayList<>();
        List<Country> l_countryList2 = new ArrayList<>();
        List<Country> l_mapCountries = new ArrayList<>();


        Country l_country1 = new Country(1,"India",1);
        l_country1.setD_armies(10);
        l_countryList1.add(l_country1);
        l_mapCountries.add(l_country1);

        Country l_country2 = new Country(2,"Morocco",1);
        l_country2.setD_armies(5);
        l_countryList1.add(l_country2);
        l_mapCountries.add(l_country2);

        l_country2.addCountryNeighbour(1);
        l_country1.addCountryNeighbour(2);

        Country l_country3 = new Country(3,"China",2);
        l_country3.setD_armies(5);
        l_countryList2.add(l_country3);
        l_mapCountries.add(l_country3);

        Country l_country4 = new Country(4,"Japan",2);
        l_country4.setD_armies(15);
        l_countryList2.add(l_country4);
        l_mapCountries.add(l_country4);

        l_country3.addCountryNeighbour(4);
        l_country4.addCountryNeighbour(3);
        l_country4.addCountryNeighbour(2);
        l_country2.addCountryNeighbour(4);


        l_map = new Map();
        l_map.setD_mapCountries(l_mapCountries);
        d_player1.setD_currentCountries(l_countryList1);
        d_player2.setD_currentCountries(l_countryList2);
        d_currentState.setD_map(l_map);

        d_cardBombOrder = new CardBomb(d_player1, "Japan");
        d_cardBombOrder1 = new CardBomb(d_player1,"India");

    }

    @Test
    public void executeCardBomb() {
        d_cardBombOrder.execute(d_currentState);
        Country l_bombedCountry = d_currentState.getD_map().getCountryByName("Japan");

        assertEquals("7", l_bombedCountry.getD_armies().toString());
    }

    @Test
    public void validOrderCheckCardBomb() {
        assertFalse(d_cardBombOrder1.valid(d_currentState));
        assertTrue(d_cardBombOrder.valid(d_currentState));
    }
}
