package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.PlayerController;
import Exceptions.CommandValidationException;
import Services.GameService;
import Utils.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * The type Issue order phase.
 */
public class IssueOrderPhase extends Phase{

    /**
     * The D game player controller.
     */
    PlayerController d_gamePlayerController = new PlayerController();

    /**
     * Instantiates a new Issue order phase.
     *
     * @param p_currentState   the p current state
     * @param p_mainGameEngine the p main game engine
     */
    public IssueOrderPhase(CurrentState p_currentState, MainGameEngine p_mainGameEngine) {
        super(p_currentState, p_mainGameEngine);
    }

    /**
     * Init phase.
     */
    @Override
    public void initPhase(boolean p_isTournamentMode) {
        while(d_mainGameEngine.getD_currentPhase() instanceof IssueOrderPhase){
            try {
                issueOrder(p_isTournamentMode);
            } catch (Exception p_e) {
                d_currentState.updateLog(p_e.getMessage(),"error");
            }
        }

    }

    @Override
    public void loadGame(CommandHandler p_commandHandler, Player p_player) {
        printInvalidCommandInPhase();
    }

    @Override
    protected void tournamentMode(CommandHandler lCommandHandler) {
        printInvalidCommandInPhase();
    }

    /**
     * Card handle.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void cardHandle(String p_inputCommand, Player p_player) {
        if(p_player.getD_cardsOwnedByPlayer().contains(p_inputCommand.split(" ")[0])){
            p_player.handleCardCommand(p_inputCommand, d_currentState);
            d_mainGameEngine.setD_mainEngineLog(p_player.d_playerLog,"effect");
        }
    }

    /**
     * Load map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void loadMap(CommandHandler lCommandHandler) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Edit map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    @Override
    protected void editMap(CommandHandler lCommandHandler) throws CommandValidationException, IOException {
        printInvalidCommandInPhase();
    }

    /**
     * Edit country.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void editCountry(CommandHandler lCommandHandler) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Edit continent.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void editContinent(CommandHandler lCommandHandler) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Edit neighbour country.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void editNeighbourCountry(CommandHandler lCommandHandler) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Show map.
     *
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void showMap() throws CommandValidationException {

    }

    /**
     * Game player.
     *
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void gamePlayer(CommandHandler p_commandHandler, Player p_player) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Assign countries.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    @Override
    protected void assignCountries(CommandHandler lCommandHandler, Player p_player, Boolean p_isTournamentMode, CurrentState p_currentState) throws CommandValidationException, IOException {
        printInvalidCommandInPhase();
    }

    /**
     * Validate map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void validateMap(CommandHandler lCommandHandler) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Save map.
     *
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void saveMap(CommandHandler lCommandHandler) throws CommandValidationException {
        printInvalidCommandInPhase();
    }

    /**
     * Issue order.
     *
     * @throws Exception the exception
     */
    private void issueOrder(boolean p_isTournamentMode) throws Exception {
        do {
            for (Player l_eachPlayer : d_currentState.getD_players()) {
                if (l_eachPlayer.getD_currentCountries().size() == 0) {
                    l_eachPlayer.setMoreOrders(false);
                }
                if(l_eachPlayer.hasMoreOrders() && !l_eachPlayer.getD_name().equals("Neutral")) {
                    l_eachPlayer.issueOrder(this);
                    l_eachPlayer.checkForMoreOrder(p_isTournamentMode);
                }
            }
        }while(d_gamePlayerController.checkForMoreOrders(d_currentState.getD_players()));
        d_mainGameEngine.setOrderExecutionPhase();
    }

    /**
     * Ask for orders.
     *
     * @param p_player the p player
     * @throws Exception the exception
     */
    public void askForOrders(Player p_player) throws Exception {
        String l_commandEntered = p_player.getPlayerOrder(d_currentState);
        if (l_commandEntered != null) {
            d_currentState.updateLog("Player : " + p_player.getD_name() + " has entered command : " + l_commandEntered ,"order");
            handleCommand(l_commandEntered, p_player);
        }
        else {
            return;
        }
    }

    /**
     * Deploy.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void deploy(String p_inputCommand, Player p_player) {
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        if(l_commandHandler.getMainCommand().equals("deploy")){
            if(p_inputCommand.split(" ").length == 3){
                p_player.createDeployOrder(p_inputCommand);
                d_currentState.updateLog(p_player.getD_playerLog(), "effect");
            }
        }
    }

    /**
     * Advance.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void advance(String p_inputCommand, Player p_player) {
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        if(l_commandHandler.getMainCommand().equals("advance")){
            if(p_inputCommand.split(" ").length == 4){
                p_player.createAdvanceOrder(p_inputCommand, d_currentState);
                d_currentState.updateLog(p_player.getD_playerLog(), "effect");
            }
            else{
                System.err.println("Invalid! command for advance order.");
                d_currentState.updateLog("Invalid! command for advance order.","error");
            }
        }
    }

    @Override
    protected void saveGame(CommandHandler p_commandHandler, Player p_player) throws CommandValidationException {
        List<Map<String,String>> l_operationsList = p_commandHandler.getListOfOperations();
        if(l_operationsList == null || l_operationsList.isEmpty()){
            System.out.println(ProjectConstants.INVALID_SAVEGAME_COMMAND);
        }
        for(java.util.Map<String,String> l_map :l_operationsList){
            if(p_commandHandler.checkRequiredKey("arguments", l_map)) {
                String l_fileName = l_map.get("arguments");
                GameService.saveGame(this,l_fileName);
                d_mainGameEngine.setD_mainEngineLog("Game saved successfully to Filename : " + l_fileName, "effect");
            } else {
                throw new CommandValidationException(ProjectConstants.INVALID_SAVEGAME_COMMAND);
            }

        }
    }
}
