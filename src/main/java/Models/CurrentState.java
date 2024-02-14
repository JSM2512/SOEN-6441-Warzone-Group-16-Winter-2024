package Models;

import java.util.ArrayList;
import java.util.List;

public class CurrentState {
    List<Player> d_players;
    Map d_map;

    public List<Player> getD_players() {
        return d_players;
    }

    public void setD_players(List<Player> p_players) {
        this.d_players = p_players;
    }

    public Map getD_map() {
        return d_map;
    }

    public void setD_map(Map d_map) {
        this.d_map = d_map;
    }

    public void addOrRemoveGamePlayers(String p_operation, String p_arguments) {
        if(p_operation.equals("add")){
            addGamePlayer(p_arguments);
        }
        else if (p_operation.equals("remove")) {
            removeGamePlayer(p_arguments);
        }
        else {
            System.out.println("Wrong command entered. Use either '-add playerName' or '-remove playerName' in command.");
        }
    }

    private void removeGamePlayer(String p_arguments) {
        if (p_arguments.split(" ").length == 1) {
            String l_playerName = p_arguments.split(" ")[0];
            if(d_players == null || d_players.isEmpty()){
                System.out.println("No Player exist in the game yet.");
            }
            else {
                boolean l_isPresent = false;
                for(Player l_eachPlayer : d_players){
                    if(l_eachPlayer.getD_name().equals(l_playerName)){
                        l_isPresent = true;
                    }
                }
                if(l_isPresent){
                    d_players.remove(getPlayerFromName(l_playerName));
                    System.out.println("Player : "+l_playerName+" removed from the game.");
                }
                else {
                    System.out.println("Player : "+l_playerName+" does not exist.");
                }
            }
        }
    }

    private Player getPlayerFromName(String p_playerName) {
        for(Player l_eachPlayer : d_players){
            if(l_eachPlayer.getD_name().equals(p_playerName)){
                return l_eachPlayer;
            }
        }
        return null;
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
