package Models;

import Controller.MainGameEngine;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;
import Views.MapView;

import java.io.IOException;

/**
 * The type Order execution phase.
 */
public class OrderExecutionPhase extends Phase{

    /**
     * Instantiates a new Phase.
     *
     * @param p_currentState   the p current state
     * @param p_mainGameEngine the p main game engine
     */
    public OrderExecutionPhase(CurrentState p_currentState, MainGameEngine p_mainGameEngine) {
        super(p_currentState, p_mainGameEngine);
    }

    /**
     * Init phase.
     */
    @Override
    public void initPhase() {
        if(d_mainGameEngine.getD_currentPhase() instanceof OrderExecutionPhase){
            executeOrders();
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
        printInvalidCommandInPhase();
    }

    /**
     * Deploy.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void deploy(String p_inputCommand, Player p_player) {
        printInvalidCommandInPhase();
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
        MapView l_mapView = new MapView(d_currentState);
        l_mapView.showMap();
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
     * Execute orders.
     */
    private void executeOrders() {
        while(d_gameplayController.isUnexecutedOrdersExist(d_currentState)){
            for(Player l_eachPlayer : d_currentState.getD_players()){
                Orders l_orderToExecute = l_eachPlayer.nextOrder();
                if(l_orderToExecute != null){
                    l_orderToExecute.execute(d_currentState);
                }
            }
        }
    }
}
