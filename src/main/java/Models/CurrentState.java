package Models;

import Constants.ProjectConstants;
import Exceptions.CommandValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Model Class Current state.
 */
public class CurrentState {
    /**
     * The D players.
     */
    List<Player> d_players;
    /**
     * The D map.
     */
    Map d_map;

    ModelLogger d_modelLogger = new ModelLogger();

    /**
     * Gets d players.
     *
     * @return the d players
     */
    public List<Player> getD_players() {
        return d_players;
    }

    /**
     * Sets d players.
     *
     * @param p_players the p players
     */
    public void setD_players(List<Player> p_players) {
        this.d_players = p_players;
    }

    /**
     * Gets d map.
     *
     * @return the d map
     */
    public Map getD_map() {
        return d_map;
    }

    /**
     * Sets d map.
     *
     * @param d_map the d map
     */
    public void setD_map(Map d_map) {
        this.d_map = d_map;
    }

    /**
     * Add or remove game players.
     *
     * @param p_operation the p operation
     * @param p_arguments the p arguments
     */
    public void addOrRemoveGamePlayers(String p_operation, String p_arguments) {
        if(p_operation.equals("add")){
            addGamePlayer(p_arguments);
        }
        else if (p_operation.equals("remove")) {
            removeGamePlayer(p_arguments);
        }
        else {
            try {
                throw new CommandValidationException("Wrong command entered. Use either '-add playerName' or '-remove playerName' in command.");
            } catch (CommandValidationException p_e) {
                System.out.println(p_e.getMessage());
            }
        }
    }

    /**
     * Remove game player.
     *
     * @param p_arguments the p arguments
     */
    private void removeGamePlayer(String p_arguments) {
        if (p_arguments.split(" ").length == 1) {
            String l_playerName = p_arguments.split(" ")[0];
            if(d_players == null || d_players.isEmpty()){
                System.out.println(ProjectConstants.NO_PLAYER_IN_GAME);
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

    /**
     * Gets player from name.
     *
     * @param p_playerName the p player name
     * @return the player from name
     */
    private Player getPlayerFromName(String p_playerName) {
        for(Player l_eachPlayer : d_players){
            if(l_eachPlayer.getD_name().equals(p_playerName)){
                return l_eachPlayer;
            }
        }
        return null;
    }

    /**
     * Add game player.
     *
     * @param p_arguments the p arguments
     */
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
                        System.out.println(ProjectConstants.NAME_ALREADY_EXISTS);
                        return;
                    }
                }
                d_players.add(l_gamePlayer);

            }
            System.out.println(l_playerName+" added.");
        }
    }
}
