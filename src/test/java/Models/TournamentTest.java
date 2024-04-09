package Models;

import Controller.MainGameEngine;
import Exceptions.CommandValidationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TournamentTest {

    Player d_player1;

    /**
     * Second Player.
     */
    Player d_player2;

    /**
     * Game State.
     */
    CurrentState d_currentState;

    Tournament d_tournament = new Tournament();

    @Before
    public void setup() {
        d_currentState = new CurrentState();
        d_player1 = new Player("a");
//        d_player1.setStrategy(new RandomPlayer());
        d_player2 = new Player("b");
//        d_player2.setStrategy(new RandomPlayer());

        d_currentState.setD_players(Arrays.asList(d_player1, d_player2));
    }


  @Test
    public void testInvalidMapArgs() throws CommandValidationException {
        Tournament l_tournament = new Tournament();
        assertFalse(l_tournament.parseTournamentCommand(d_currentState,"M","test.map test.map test.map test.map test.map test.map",new MainGameEngine()));
      assertTrue(l_tournament.parseTournamentCommand(d_currentState,"M","test.map test.map test.map test.map",new MainGameEngine()));
  }
}