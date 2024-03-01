package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.PlayerController;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    public void initPhase() {
        if(d_mainGameEngine.getD_currentPhase() instanceof IssueOrderPhase){
            try {
                issueOrder();
            } catch (Exception p_e) {
                d_currentState.getD_modelLogger().setD_message(p_e.getMessage(),"Error");
            }
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
     * @param lCommandHandler the l command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void gamePlayer(CommandHandler lCommandHandler) throws CommandValidationException {
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
    protected void assignCountries(CommandHandler lCommandHandler) throws CommandValidationException, IOException {
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
    private void issueOrder() throws Exception {
        do {
            for (Player l_eachPlayer : d_currentState.getD_players()) {
                if(l_eachPlayer.hasMoreOrders()) {
                    l_eachPlayer.issueOrder(this);
                }
            }
        }while(d_gamePlayerController.checkForMoreOrders(d_currentState.getD_players()));
    }

    /**
     * Ask for orders.
     *
     * @param p_player the p player
     * @throws Exception the exception
     */
    public void askForOrders(Player p_player) throws Exception {
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please Enter command for Player : " + p_player.getD_name() + "   Armies left : " + p_player.getD_unallocatedArmies());
        System.out.println("1. Deploy Order Command : 'deploy <countryName> <noOfArmies>'");
        System.out.println("2. ");
        System.out.println();
        System.out.print("Enter your command: ");
        String l_commandEntered = l_bufferedReader.readLine();
        d_currentState.getD_modelLogger().setD_message("Player : " + p_player.getD_name() + " has entered command : " + l_commandEntered ,"Order-1");
        handleCommand(l_commandEntered, p_player);
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
                p_player.checkForMoreOrder();
            }
        }
    }
}
