package Models;

import Controller.MainGameEngine;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        while(d_mainGameEngine.getD_currentPhase() instanceof OrderExecutionPhase){
            executeOrders();
            MapView l_mapView = new MapView(d_currentState);
            l_mapView.showMap();

            if(this.checkEndOfGame(d_currentState)){
                System.exit(0);
            }

            while(d_currentState.getD_players() != null){
                System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

                try{
                    String l_continue = l_reader.readLine();
                    if(l_continue.equalsIgnoreCase("N")){
                        System.exit(0);
                    }
                    else if(l_continue.equalsIgnoreCase("Y")){
                        d_gameplayController.assignArmies(d_currentState);
                        d_mainGameEngine.setIssueOrderPhase();
                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                }
                catch (IOException l_e){
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    /**
     * Check end of game boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean checkEndOfGame(CurrentState p_currentState) {
        Integer l_totalCountries = p_currentState.getD_map().getD_mapCountries().size();
        Integer l_neutralCountries = 0;
        for(Player l_eachPlayer : p_currentState.getD_players()){
            if(l_eachPlayer.getD_name().equalsIgnoreCase("Neutral")){
                l_neutralCountries = l_eachPlayer.getD_currentCountries().size();
            }
        }
        for(Player l_eachPlayer : p_currentState.getD_players()){
            if(!l_eachPlayer.getD_name().equalsIgnoreCase("Neutral")) {
                if (l_eachPlayer.getD_currentCountries().size() + l_neutralCountries == l_totalCountries) {
                    d_mainGameEngine.setD_mainEngineLog(l_eachPlayer.getD_name() + " has won the game. Exiting the game....", "end");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Card handle.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void cardHandle(String p_inputCommand, Player p_player) {
        printInvalidCommandInPhase();
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
        addNeutralPlayer(d_currentState);
        d_mainGameEngine.setD_mainEngineLog("\nStarting Execution of Orders......", "start");
        while(d_gameplayController.isUnexecutedOrdersExist(d_currentState.getD_players())){
            for(Player l_eachPlayer : d_currentState.getD_players()){
                Orders l_orderToExecute = l_eachPlayer.nextOrder();
                if(l_orderToExecute != null){
                   // d_currentState.updateLog(l_orderToExecute.orderExecutionLog(), "effect");
                    l_orderToExecute.execute(d_currentState);
                }
            }
        }
        d_gameplayController.resetPlayerFlag(d_currentState.getD_players());
    }

    /**
     * Add neutral player.
     *
     * @param p_currentState the p current state
     */
    private void addNeutralPlayer(CurrentState p_currentState) {
        Player l_player = null;
        for(Player l_eachPlayer : p_currentState.getD_players()){
            if(l_eachPlayer.getD_name().equalsIgnoreCase("Neutral")){
                l_player = l_eachPlayer;
                break;
            }
        }
        if(l_player == null){
            Player l_neutralPlayer = new Player("Neutral");
            l_neutralPlayer.setMoreOrders(false);
            p_currentState.getD_players().add(l_neutralPlayer);
        }
        else{
            return;
        }
    }
}
