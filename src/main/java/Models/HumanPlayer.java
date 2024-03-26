package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer extends PlayerBehaviourStrategy{
    @Override
    public String getPlayerBehaviour() {
        return "Human";
    }

    @Override
    public String createOrder(Player p_player, CurrentState p_currentState) throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please Enter the command to issue order for player : " + p_player.getD_name() + " or use showmap command to view the current state of the map.");
        String l_command = l_reader.readLine();
        return l_command;
    }

    @Override
    public String createCardOrder(Player p_player, CurrentState p_currentState, String p_cardName) {
        return null;
    }

    @Override
    public String createAdvanceOrder(Player p_player, CurrentState p_currentState) {
        return null;
    }

    @Override
    public String createDeployOrder(Player p_player, CurrentState p_currentState) {
        return null;
    }
}
