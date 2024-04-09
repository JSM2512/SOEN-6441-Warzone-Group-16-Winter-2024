package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.MapController;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tournament.
 */
public class Tournament implements Serializable {
    /**
     * The D map controller.
     */
    MapController d_mapController = new MapController();
    /**
     * The D current state list.
     */
    List<CurrentState> d_currentStateList = new ArrayList<>();

    /**
     * Gets d map controller.
     *
     * @return the d map controller
     */
    public MapController getD_mapController() {
        return d_mapController;
    }

    /**
     * Sets d map controller.
     *
     * @param p_mapController the p map controller
     */
    public void setD_mapController(MapController p_mapController) {
        this.d_mapController = p_mapController;
    }

    /**
     * Gets d current state list.
     *
     * @return the d current state list
     */
    public List<CurrentState> getD_currentStateList() {
        return d_currentStateList;
    }

    /**
     * Sets d current state list.
     *
     * @param p_currentStates the p current states
     */
    public void setD_currentStateList(List<CurrentState> p_currentStates) {
        this.d_currentStateList = p_currentStates;
    }

    /**
     * Parse tournament command boolean.
     *
     * @param p_currentState   the p current state
     * @param p_operation      the p operation
     * @param p_argument       the p argument
     * @param p_maingameEngine the p maingame engine
     * @return the boolean
     * @throws CommandValidationException the command validation exception
     */
    public boolean parseTournamentCommand(CurrentState p_currentState, String p_operation, String p_argument, MainGameEngine p_maingameEngine) throws CommandValidationException {
        if(p_operation.equalsIgnoreCase("M")){
            return parseMapArguments(p_argument, p_maingameEngine);
        }
        if(p_operation.equalsIgnoreCase("P")){
            return parseStrategyArguments(p_currentState, p_argument, p_maingameEngine);
        }
        if(p_operation.equalsIgnoreCase("G")){
            return parseNoOfGameArgument(p_argument, p_maingameEngine);
        }
        if(p_operation.equalsIgnoreCase("D")){
            return parseNoOfTurnArgument(p_argument, p_maingameEngine);
        }
        throw new CommandValidationException(ProjectConstants.INVALID_TOURNAMENT_MODE_COMMAND);
    }

    /**
     * Parse no of turn argument boolean.
     *
     * @param p_argument       the p argument
     * @param p_maingameEngine the p maingame engine
     * @return the boolean
     */
    private boolean parseNoOfTurnArgument(String p_argument, MainGameEngine p_maingameEngine) {
        int l_maxNoOfTurns = Integer.parseInt(p_argument.split(" ")[0]);
        if(l_maxNoOfTurns >= 10 && l_maxNoOfTurns <= 50){
            for(CurrentState l_currentState : d_currentStateList){
                l_currentState.setD_maxNumberOfTurns(l_maxNoOfTurns);
                l_currentState.setD_numberOfTurnsLeft(l_maxNoOfTurns);
            }
            return true;
        }else {
            p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_TURN_COUNT,"effect");
            return false;
        }
    }

    /**
     * Parse no of game argument boolean.
     *
     * @param p_argument       the p argument
     * @param p_maingameEngine the p maingame engine
     * @return the boolean
     */
    private boolean parseNoOfGameArgument(String p_argument, MainGameEngine p_maingameEngine) {
        int l_noOfGames = Integer.parseInt(p_argument.split(" ")[0]);
        if(l_noOfGames >= 1 && l_noOfGames <= 5){
            List<CurrentState> l_additionalCurrentStates = new ArrayList<>();
            for(int l_gameNumber =0 ; l_gameNumber < l_noOfGames -1; l_gameNumber++) {
                for (CurrentState l_eachState : d_currentStateList) {
                    CurrentState l_eachStateToAdd = new CurrentState();
                    Map l_loadedMap = d_mapController.loadMap(l_eachStateToAdd, l_eachState.getD_map().getD_mapName());
                    l_loadedMap.setD_mapName(l_eachState.getD_map().getD_mapName());
                    List<Player> l_playersToCopy = getPlayersToAdd(l_eachState.getD_players());
                    l_eachStateToAdd.setD_players(l_playersToCopy);
                    l_eachStateToAdd.setD_loadCommand();
                    l_additionalCurrentStates.add(l_eachStateToAdd);
                }
            }
            d_currentStateList.addAll(l_additionalCurrentStates);
            return true;
        }else {
            p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_GAME_COUNT,"effect");
            return false;
        }
    }

    /**
     * Parse strategy arguments boolean.
     *
     * @param p_currentState   the p current state
     * @param p_argument       the p argument
     * @param p_maingameEngine the p maingame engine
     * @return the boolean
     */
    private boolean parseStrategyArguments(CurrentState p_currentState, String p_argument, MainGameEngine p_maingameEngine) {
        String[] l_listOfStrategies = p_argument.split(" ");
        int l_strategySize = l_listOfStrategies.length;
        List<Player> l_playersInGame = new ArrayList<>();
        List<String> l_uniqueStrategies = new ArrayList<>();
        for(String l_strategy : l_listOfStrategies){
            if(l_uniqueStrategies.contains(l_strategy)){
                p_maingameEngine.setD_mainEngineLog(ProjectConstants.DUPLICATE_STRATEGY,"effect");
                return false;
            }
            l_uniqueStrategies.add(l_strategy);
            if(!ProjectConstants.TOURNAMENT_PLAYER_BEHAVIOUR.contains(l_strategy)){
                p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_STRATEGY,"effect");
                return false;
            }
        }
        if(l_strategySize >= 2 && l_strategySize <= 4){
            setTournamentPlayers(p_maingameEngine, l_listOfStrategies, p_currentState.getD_players(), l_playersInGame);
        }
        else {
            p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_STRATEGY_COUNT,"effect");
            return false;
        }
        if(l_playersInGame.size() < 2){
            p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_PLAYER_NUMBER,"effect");
            return false;
        }
        for(CurrentState l_currentState : d_currentStateList){
            l_currentState.setD_players(getPlayersToAdd(l_playersInGame));
        }

        return true;
    }

    /**
     * Sets tournament players.
     *
     * @param p_maingameEngine   the p maingame engine
     * @param p_listOfStrategies the p list of strategies
     * @param p_players          the p players
     * @param p_playersInGame    the p players in game
     */
    private void setTournamentPlayers(MainGameEngine p_maingameEngine, String[] p_listOfStrategies, List<Player> p_players, List<Player> p_playersInGame) {
        for(String l_strategy : p_listOfStrategies) {
           for(Player l_eachPlayer : p_players){
               if(l_eachPlayer.getD_playerBehaviourStrategy().getPlayerBehaviour().equalsIgnoreCase(l_strategy)){
                   p_playersInGame.add(l_eachPlayer);
                   p_maingameEngine.setD_mainEngineLog("Player : "+l_eachPlayer.getD_name()+" added to the game with strategy : "+l_eachPlayer.getD_playerBehaviourStrategy().getPlayerBehaviour(),"effect");
               }
           }
        }
    }

    /**
     * Gets players to add.
     *
     * @param p_playersInGame the p players in game
     * @return the players to add
     */
    private List<Player> getPlayersToAdd(List<Player> p_playersInGame) {
        List<Player> l_players = new ArrayList<>();
        for(Player l_player : p_playersInGame){
            Player l_newPlayer = new Player(l_player.getD_name());

            if(l_player.getD_playerBehaviourStrategy() instanceof AggressivePlayer){
                l_newPlayer.setD_playerBehaviourStrategy(new AggressivePlayer());
            } else if (l_player.getD_playerBehaviourStrategy() instanceof BenevolentPlayer){
                l_newPlayer.setD_playerBehaviourStrategy(new BenevolentPlayer());
            } else if (l_player.getD_playerBehaviourStrategy() instanceof CheaterPlayer){
                l_newPlayer.setD_playerBehaviourStrategy(new CheaterPlayer());
            } else if (l_player.getD_playerBehaviourStrategy() instanceof RandomPlayer){
                l_newPlayer.setD_playerBehaviourStrategy(new RandomPlayer());
            }
            l_players.add(l_newPlayer);
        }
        return l_players;
    }

    /**
     * Parse map arguments boolean.
     *
     * @param p_argument       the p argument
     * @param p_maingameEngine the p maingame engine
     * @return the boolean
     */
    private boolean parseMapArguments(String p_argument, MainGameEngine p_maingameEngine) {
        String[] l_listOfMapFiles = p_argument.split(" ");
        int l_mapFileSize = l_listOfMapFiles.length;

        if(l_mapFileSize >= 1 && l_mapFileSize <= 5){
            for(String l_mapFile : l_listOfMapFiles){
                CurrentState l_currentState = new CurrentState();
                Map l_map = d_mapController.loadMap(l_currentState, l_mapFile);
                l_map.setD_mapName(l_mapFile);
                if(l_map.validateMap()){
                    l_currentState.setD_loadCommand();
                    p_maingameEngine.setD_mainEngineLog("Map :" + l_mapFile + " Loaded Successfully","effect");
                    d_currentStateList.add(l_currentState);
                }
                else {
                    d_mapController.resetMap(l_currentState, l_mapFile);
                    return false;
                }
            }
        }
        else {
            p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_MAP_FILE_COUNT,"effect");
            return false;
        }
        return true;
    }

    /**
     * Required tournament arg present boolean.
     *
     * @param p_operationsList the p operations list
     * @param p_commandHandler the p command handler
     * @return the boolean
     */
    public boolean requiredTournamentArgPresent(List<java.util.Map<String, String>> p_operationsList, CommandHandler p_commandHandler) {
        String l_argumentKey = new String();
        if(p_operationsList.size() != 4){
            return false;
        }
        for(java.util.Map<String, String> l_operation : p_operationsList){
            if(p_commandHandler.checkRequiredKey("Arguments", l_operation) && p_commandHandler.checkRequiredKey("Operation", l_operation)){
                l_argumentKey.concat(l_operation.get("Operation"));
            }
        }
        if(!l_argumentKey.equalsIgnoreCase("MPGD")){
            return false;
        }
        return true;
    }
}