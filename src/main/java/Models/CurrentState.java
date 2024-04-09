package Models;

import Constants.ProjectConstants;
import Exceptions.CommandValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model Class Current state.
 */
public class CurrentState implements Serializable {
    /**
     * The D players.
     */
    List<Player> d_players;
    /**
     * The D map.
     */
    Map d_map;

    /**
     * The D max number of turns.
     */
    int d_maxNumberOfTurns = 0;

    /**
     * The D number of turns left.
     */
    int d_numberOfTurnsLeft = 0;

    /**
     * The D winner.
     */
    Player d_winner;

    /**
     * The D players failed.
     */
    List<Player> d_playersFailed = new ArrayList<>();

    /**
     * Gets d winner.
     *
     * @return the d winner
     */
    public Player getD_winner() {
        return d_winner;
    }

    /**
     * Sets d winner.
     *
     * @param d_winner the d winner
     */
    public void setD_winner(Player d_winner) {
        this.d_winner = d_winner;
    }

    /**
     * Gets d players failed.
     *
     * @return the d players failed
     */
    public List<Player> getD_playersFailed() {
        return d_playersFailed;
    }

    /**
     * Sets d players failed.
     *
     * @param d_playersFailed the d players failed
     */
    public void setD_playersFailed(List<Player> d_playersFailed) {
        this.d_playersFailed = d_playersFailed;
    }

    /**
     * The D model logger.
     */
    ModelLogger d_modelLogger = new ModelLogger();
    /**
     * The D load command.
     */
    Boolean d_loadCommand = false;

    /**
     * Instantiates a new Current state.
     */
    public CurrentState() {
    }

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
     * Gets d max number of turns.
     *
     * @return the d max number of turns
     */
    public int getD_maxNumberOfTurns() {
        return d_maxNumberOfTurns;
    }

    /**
     * Sets d max number of turns.
     *
     * @param p_maxNumberOfTurns the p max number of turns
     */
    public void setD_maxNumberOfTurns(int p_maxNumberOfTurns) {
        this.d_maxNumberOfTurns = p_maxNumberOfTurns;
    }

    /**
     * Gets d number of turns left.
     *
     * @return the d number of turns left
     */
    public int getD_numberOfTurnsLeft() {
        return d_numberOfTurnsLeft;
    }

    /**
     * Sets d number of turns left.
     *
     * @param p_numberOfTurnsLeft the p number of turns left
     */
    public void setD_numberOfTurnsLeft(int p_numberOfTurnsLeft) {
        this.d_numberOfTurnsLeft = p_numberOfTurnsLeft;
    }

    /**
     * Get recent log string.
     *
     * @return the string
     */
    public String getRecentLog(){
        return d_modelLogger.getD_message();
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
     * Get d model logger model logger.
     *
     * @return the model logger
     */
    public ModelLogger getD_modelLogger(){
        return  this.d_modelLogger;
    }

    /**
     * Gets d model logger message.
     *
     * @return the d model logger message
     */
    public String getD_modelLoggerMessage() {
        return d_modelLogger.getD_message();
    }

    /**
     * Sets log message.
     *
     * @param p_modelLoggerMessage the p model logger message
     * @param p_messageType        the p message type
     */
    public void setLogMessage(String p_modelLoggerMessage, String p_messageType) {
        d_modelLogger.setD_message(p_modelLoggerMessage, p_messageType);
    }

    /**
     * Gets d load command.
     *
     * @return the d load command
     */
    public Boolean getD_loadCommand() {
        return d_loadCommand;
    }

    /**
     * Sets d load command.
     */
    public void setD_loadCommand() {
        this.d_loadCommand = true;
    }

    /**
     * Add or remove game players.
     *
     * @param p_operation the p operation
     * @param p_arguments the p arguments
     * @throws IOException the io exception
     */
    public void addOrRemoveGamePlayers(String p_operation, String p_arguments) throws IOException {
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
    public Player getPlayerFromName(String p_playerName) {
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
     * @throws IOException the io exception
     */
    public void addGamePlayer(String p_arguments) throws IOException {
        if (p_arguments.split(" ").length == 1) {
            String l_playerName = p_arguments.split(" ")[0];
            Player l_gamePlayer = new Player(l_playerName);
            if(d_players == null){
                d_players = new ArrayList<>();
            }
            else {
                for (Player l_eachPlayer : d_players) {
                    if (l_eachPlayer.getD_name().equals(l_playerName)) {
                        System.out.println(ProjectConstants.NAME_ALREADY_EXISTS);
                        return;
                    }
                }
            }
            String l_playerStrategy = getPlayerStrategy(l_gamePlayer);
            switch (l_playerStrategy){
                case "Aggressive":
                    l_gamePlayer.setD_playerBehaviourStrategy(new AggressivePlayer());
                    break;
                case "Benevolent":
                    l_gamePlayer.setD_playerBehaviourStrategy(new BenevolentPlayer());
                    break;
                case "Random":
                    l_gamePlayer.setD_playerBehaviourStrategy(new RandomPlayer());
                    break;
                case "Cheater":
                    l_gamePlayer.setD_playerBehaviourStrategy(new CheaterPlayer());
                    break;
                case "Human":
                    l_gamePlayer.setD_playerBehaviourStrategy(new HumanPlayer());
                    break;
                default:
                    updateLog("Invalid player strategy entered.", "error");
            }
            d_players.add(l_gamePlayer);
            System.out.println("Player : "+l_playerName+" added to the game with Strategy :" + l_playerStrategy);
        }
    }

    /**
     * Gets player strategy.
     *
     * @param p_gamePlayer the p game player
     * @return the player strategy
     * @throws IOException the io exception
     */
    private String getPlayerStrategy(Player p_gamePlayer) throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the strategy for player : "+p_gamePlayer.getD_name()+"(Aggressive, Benevolent, Random, Cheater, Human)");
        String l_playerStrategy = l_reader.readLine();
        if (!ProjectConstants.PLAYER_BEHAVIOR.contains(l_playerStrategy)) {
            this.updateLog("Invalid player strategy entered.", "error");
            return getPlayerStrategy(p_gamePlayer);
        }
        return l_playerStrategy;
    }

    /**
     * Gets player from country name.
     *
     * @param p_targetCountryName the p target country name
     * @return the player from country name
     */
    public Player getPlayerFromCountryName(String p_targetCountryName) {
        for(Player l_eachPlayer : d_players){
            for(Country l_eachCountry : l_eachPlayer.getD_currentCountries()){
                if(l_eachCountry.getD_countryName().equals(p_targetCountryName)){
                    return l_eachPlayer;
                }
            }
        }
        return null;
    }

    /**
     * Update log.
     *
     * @param p_logMessage the p log message
     * @param p_logType    the p log type
     */
    public void updateLog(String p_logMessage,String p_logType){
        d_modelLogger.setD_message(p_logMessage,p_logType);
    }
}
