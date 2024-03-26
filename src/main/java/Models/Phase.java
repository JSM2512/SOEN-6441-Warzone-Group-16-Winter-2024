package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.MapController;
import Controller.PlayerController;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;

import java.io.IOException;

/**
 * The type Phase.
 */
public abstract class Phase {

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
    public abstract void initPhase();

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
                editMap(l_commandHandler);
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
            case "gameplayer":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_PLAYERS);
                }
                else {
                    gamePlayer(l_commandHandler);
                }
                break;
            case "assigncountries":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_ASSIGN_COUNTRIES);
                }
                else {
                    assignCountries(l_commandHandler);
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

    protected abstract void tournamentMode(CommandHandler lCommandHandler);

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
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void loadMap(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Edit map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    protected abstract void editMap(CommandHandler lCommandHandler) throws CommandValidationException, IOException;

    /**
     * Edit country.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void editCountry(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Edit continent.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void editContinent(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Edit neighbour country.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void editNeighbourCountry(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Show map.
     *
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void showMap() throws CommandValidationException;

    /**
     * Game player.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void gamePlayer(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Assign countries.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    protected abstract void assignCountries(CommandHandler lCommandHandler) throws CommandValidationException, IOException;

    /**
     * Validate map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void validateMap(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Save map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    protected abstract void saveMap(CommandHandler lCommandHandler) throws CommandValidationException;

    /**
     * Print invalid command in phase.
     */
    public void printInvalidCommandInPhase(){
        d_mainGameEngine.setD_mainEngineLog("Invalid Command entered for this phase.","effect");
    }

}
