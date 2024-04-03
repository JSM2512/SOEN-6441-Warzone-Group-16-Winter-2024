package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.MapController;
import Controller.PlayerController;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;

import java.io.IOException;
import java.io.Serializable;

/**
 * The type Phase.
 */
public abstract class Phase implements Serializable {

    /**
     * The D current state.
     */
    CurrentState d_currentState;

    /**
     * The D map controller.
     */
    MapController d_mapController = new MapController();
    /**
     * The D gameplay controller.
     */
    PlayerController d_gameplayController = new PlayerController();

    /**
     * The D main game engine.
     */
    MainGameEngine d_mainGameEngine;


    Tournament d_tournament = new Tournament();


    /**
     * Instantiates a new Phase.
     *
     * @param p_currentState   the p current state
     * @param p_mainGameEngine the p main game engine
     */
    public Phase(CurrentState p_currentState, MainGameEngine p_mainGameEngine) {
        this.d_currentState = p_currentState;
        this.d_mainGameEngine = p_mainGameEngine;
    }

    /**
     * Init phase.
     */
    public abstract void initPhase(boolean p_isTournamentMode);

    /**
     * Gets d current state.
     *
     * @return the d current state
     */
    public CurrentState getD_currentState() {
        return d_currentState;
    }

    /**
     * Gets d main game engine.
     *
     * @return the d main game engine
     */
    public MainGameEngine getD_mainGameEngine() {
        return d_mainGameEngine;
    }

    /**
     * Handle command.
     *
     * @param p_inputCommand the p input command
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    public void handleCommand(String p_inputCommand) throws CommandValidationException, IOException {
        commandHandler(p_inputCommand, null);
    }

    /**
     * Handle command.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    public void handleCommand(String p_inputCommand, Player p_player) throws CommandValidationException, IOException {
        commandHandler(p_inputCommand, p_player);
    }

    /**
     * Command handler.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    public void commandHandler(String p_inputCommand, Player p_player) throws CommandValidationException, IOException {
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        String l_mainCommand = l_commandHandler.getMainCommand();
        boolean l_mapAvailable = false;
        if(d_currentState.getD_map() != null){
            l_mapAvailable = true;
        }
        d_currentState.updateLog(l_commandHandler.getMainCommand(),"command");
        switch (l_mainCommand){
            case "loadmap":
                loadMap(l_commandHandler);
                break;
            case "editmap":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    editMap(l_commandHandler);
                }
                break;
            case "savegame":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    saveGame(l_commandHandler, p_player);
                }
                break;
            case "editcountry":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_EDIT_COUNTRY);
                } else {
                    editCountry(l_commandHandler);
                }
                break;
            case "editcontinent":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                }
                else{
                    editContinent(l_commandHandler);
                }
                break;
            case "editneighbour":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                }
                else {
                    editNeighbourCountry(l_commandHandler);
                }
                break;
            case "showmap":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                }
                else {
                    showMap();
                }
                break;

            case "loadgame":
                loadGame(l_commandHandler, p_player);
                break;
            case "gameplayer":
                gamePlayer(l_commandHandler, p_player);
                break;
            case "assigncountries":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_ASSIGN_COUNTRIES);
                }
                else {
                    assignCountries(l_commandHandler, p_player, false, d_currentState);
                }
                break;
            case "validatemap":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    validateMap(l_commandHandler);
                }
                break;
            case "savemap":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    saveMap(l_commandHandler);
                }
                break;
            case "deploy":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    deploy(p_inputCommand, p_player);
                }
                break;
            case "advance":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    advance(p_inputCommand, p_player);
                }
                break;
            case "bomb":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    cardHandle(p_inputCommand, p_player);
                }
                break;
            case "blockade":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    cardHandle(p_inputCommand, p_player);
                }
                break;
            case "airlift":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                } else {
                    cardHandle(p_inputCommand, p_player);
                }
                break;
            case "negotiate":
                if (!l_mapAvailable) {
                    d_currentState.getD_modelLogger().setD_message("Entered command: negotiate. Map is not available. ","effect");
                } else {
                    cardHandle(p_inputCommand, p_player);
                }
                break;
            case "tournament":
                tournamentMode(l_commandHandler);
                break;
            case "exit":
                d_currentState.getD_modelLogger().setD_message("---------------Game Session Closed---------------","effect");
                System.out.println("Closing Game....");
                System.exit(0);
                break;
            default:
                d_mainGameEngine.setD_mainEngineLog("Invalid Command entered for this phase.","effect");
                break;
        }
    }

    public abstract void loadGame(CommandHandler p_commandHandler, Player p_player);


    protected abstract void saveGame(CommandHandler p_commandHandler, Player p_player) throws CommandValidationException;

    protected abstract void tournamentMode(CommandHandler p_commandHandler) throws CommandValidationException, IOException;

    /**
     * Card handle.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    protected abstract void cardHandle(String p_inputCommand, Player p_player);

    /**
     * Advance.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    protected abstract void advance(String p_inputCommand, Player p_player);

    /**
     * Deploy.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    protected abstract void deploy(String p_inputCommand, Player p_player);


    /**
     * Load map.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void loadMap(CommandHandler p_commandHandler) throws CommandValidationException;

    /**
     * Edit map.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    protected abstract void editMap(CommandHandler p_commandHandler) throws CommandValidationException, IOException;

    /**
     * Edit country.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void editCountry(CommandHandler p_commandHandler) throws CommandValidationException;

    /**
     * Edit continent.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void editContinent(CommandHandler p_commandHandler) throws CommandValidationException;

    /**
     * Edit neighbour country.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void editNeighbourCountry(CommandHandler p_commandHandler) throws CommandValidationException;

    /**
     * Show map.
     *
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void showMap() throws CommandValidationException;

    /**
     * Game player.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void gamePlayer(CommandHandler p_commandHandler,Player p_player) throws CommandValidationException, IOException;

    /**
     * Assign countries.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    protected abstract void assignCountries(CommandHandler p_commandHandler, Player p_player, Boolean p_isTournamentMode, CurrentState p_currentState) throws CommandValidationException, IOException;

    /**
     * Validate map.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void validateMap(CommandHandler p_commandHandler) throws CommandValidationException;

    /**
     * Save map.
     *
     * @param p_commandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void saveMap(CommandHandler p_commandHandler) throws CommandValidationException;

    /**
     * Print invalid command in phase.
     */
    public void printInvalidCommandInPhase(){
        d_mainGameEngine.setD_mainEngineLog("Invalid Command entered for this phase.","effect");
    }

}
