package Models;

import java.util.ArrayList;
import java.util.List;

public class CurrentState {
    List<Player> d_players;
    List<Orders> d_pendingOrders;
    Map d_map;

    public List<Player> getD_players() {
        return d_players;
    }

    public void setD_players(List<Player> p_players) {
        this.d_players = p_players;
    }

    public List<Orders> getD_pendingOrders() {
        return d_pendingOrders;
    }

    public void setD_pendingOrders(List<Orders> p_pendingOrders) {
        this.d_pendingOrders = p_pendingOrders;
    }

    public Map getD_map() {
        return d_map;
    }

    public void setD_map(Map d_map) {
        this.d_map = d_map;
    }

    public void addOrRemoveGamePlayers(String p_operation, String p_arguments) {
        System.out.println("current state player class");
        if(p_operation.equals("add")){
            addGamePlayer(p_arguments);
        }
    }
    public void addGamePlayer(String p_arguments){
        if (p_arguments.split(" ").length == 1) {
            String l_playerName = p_arguments.split(" ")[0];
            Player l_gamePlayer = new Player(l_playerName);
            if(d_players == null){
                d_players = new ArrayList<>();
                d_players.add(l_gamePlayer);
            }
            else {
                for(Player l_eachPlayer : d_players){
                    if(l_eachPlayer.getD_name().equals(l_playerName))
                    {
                        System.out.println("Name already exist, try some other name!");
                        return;
                    }
                }
                d_players.add(l_gamePlayer);

            }
            System.out.println(l_playerName+" added.");
        }
    }
}
