package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model Class Player.
 */
public class Player {
    /**
     * The D player log.
     */
    public String d_playerLog;
    /**
     * The D name.
     */
    String d_name;

    /**
     * Gets d player log.
     *
     * @return the d player log
     */
    public String getD_playerLog() {
        return d_playerLog;
    }

    /**
     * The D unallocated armies.
     */
    Integer d_unallocatedArmies;
    /**
     * The D current countries.
     */
    List<Country> d_currentCountries;
    /**
     * The D current continents.
     */
    List<Continent> d_currentContinents;
    /**
     * The D orders.
     */
    List<Orders> d_orders;

    /**
     * The D more orders.
     */
    boolean d_moreOrders;
    /**
     * The D cards owned by player.
     */
    List<String> d_cardsOwnedByPlayer = new ArrayList<>();
    /**
     * The D negotiate players.
     */
    List<Player> d_negotiatePlayers = new ArrayList<>();
    /**
     * The D one card per turn.
     */
    boolean d_oneCardPerTurn = false;

    /**
     * Instantiates a new Player.
     *
     * @param p_name the p name
     */
    public Player(String p_name){
        this.d_name = p_name;
        this.d_unallocatedArmies = 0;
        this.d_orders = new ArrayList<Orders>();
        this.d_moreOrders = true;
        this.d_currentCountries= new ArrayList<>();
        this.d_currentContinents = new ArrayList<>();
    }

    /**
     * Sets d player log.
     *
     * @param p_orderExecutionLog the p order execution log
     * @param p_messageType       the p message type
     */
    public void setD_playerLog(String p_orderExecutionLog, String p_messageType) {
        MainGameEngine mainGameEngine = new MainGameEngine();
        mainGameEngine.setD_mainEngineLog(p_orderExecutionLog, p_messageType);
        if (p_messageType.equals("error")) {
            System.err.println(p_orderExecutionLog);
        } else {
            System.out.println(p_orderExecutionLog);
        }
    }

    /**
     * Gets d negotiate players.
     *
     * @return the d negotiate players
     */
    public List<Player> getD_negotiatePlayers() {
        return d_negotiatePlayers;
    }

    /**
     * Gets d name.
     *
     * @return the d name
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * Sets d name.
     *
     * @param p_name the p name
     */
    public void setD_name(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Gets d unallocated armies.
     *
     * @return the d unallocated armies
     */
    public Integer getD_unallocatedArmies() {
        return d_unallocatedArmies;
    }

    /**
     * Sets d unallocated armies.
     *
     * @param p_unallocatedArmies the p unallocated armies
     */
    public void setD_unallocatedArmies(Integer p_unallocatedArmies) {
        this.d_unallocatedArmies = p_unallocatedArmies;
    }

    /**
     * Gets d current countries.
     *
     * @return the d current countries
     */
    public List<Country> getD_currentCountries() {
        return d_currentCountries;
    }

    /**
     * Sets d current countries.
     *
     * @param p_currentCountries the p current countries
     */
    public void setD_currentCountries(List<Country> p_currentCountries) {
        this.d_currentCountries = p_currentCountries;
    }

    /**
     * Gets d current continents.
     *
     * @return the d current continents
     */
    public List<Continent> getD_currentContinents() {
        return d_currentContinents;
    }

    /**
     * Sets d current continents.
     *
     * @param p_currentContinents the p current continents
     */
    public void setD_currentContinents(List<Continent> p_currentContinents) {
        this.d_currentContinents = p_currentContinents;
    }

    /**
     * Gets d orders.
     *
     * @return the d orders
     */
    public List<Orders> getD_orders() {
        return d_orders;
    }

    /**
     * Sets d orders.
     *
     * @param p_orders the p orders
     */
    public void setD_orders(List<Orders> p_orders) {
        this.d_orders = p_orders;
    }

    /**
     * Has more orders boolean.
     *
     * @return the boolean
     */
    public boolean hasMoreOrders() {
        return d_moreOrders;
    }

    /**
     * Sets more orders.
     *
     * @param p_moreOrders the p more orders
     */
    public void setMoreOrders(boolean p_moreOrders) {
        this.d_moreOrders = p_moreOrders;
    }

    /**
     * Reset negotiation.
     */
    public void resetNegotiation(){
        d_negotiatePlayers.clear();
    }

    /**
     * Is d one card per turn boolean.
     *
     * @return the boolean
     */
    public boolean isD_oneCardPerTurn() {
        return d_oneCardPerTurn;
    }

    /**
     * Sets d one card per turn.
     *
     * @param d_oneCardPerTurn the d one card per turn
     */
    public void setD_oneCardPerTurn(boolean d_oneCardPerTurn) {
        this.d_oneCardPerTurn = d_oneCardPerTurn;
    }

    /**
     * Sets continent.
     *
     * @param p_continent the p continent
     */
    public void setContinent(Continent p_continent) {
        if(d_currentContinents == null){
            d_currentContinents = new ArrayList<>();
            d_currentContinents.add(p_continent);
        }
        else{
            boolean isPresent = false;
            for(Continent l_eachContinent : d_currentContinents){
                if(l_eachContinent.getD_continentName().equals(p_continent.getD_continentName())){
                    isPresent = true;
                }
            }
            if(!isPresent){
                d_currentContinents.add(p_continent);
            }
            else{
                System.out.println("Continent : "+p_continent.getD_continentName()+" already assigned to Player : "+d_name);
            }
        }
    }

    /**
     * Gets d cards owned by player.
     *
     * @return the d cards owned by player
     */
    public List<String> getD_cardsOwnedByPlayer() {
        return d_cardsOwnedByPlayer;
    }

    /**
     * Sets d cards owned by player.
     *
     * @param d_cardsOwnedByPlayer the d cards owned by player
     */
    public void setD_cardsOwnedByPlayer(List<String> d_cardsOwnedByPlayer) {
        this.d_cardsOwnedByPlayer = d_cardsOwnedByPlayer;
    }

    /**
     * Issue order.
     *
     * @param p_issueOrderPhase the p issue order phase
     * @throws Exception the exception
     */
    public void issueOrder(IssueOrderPhase p_issueOrderPhase) throws Exception {
        p_issueOrderPhase.askForOrders(this);
    }


    /**
     * Next order orders.
     *
     * @return the orders
     */
    public Orders nextOrder() {
        if(d_orders == null || d_orders.isEmpty()){
            return null;
        }
        Orders l_latestOrder = d_orders.get(0);
        d_orders.remove(l_latestOrder);
        return l_latestOrder;
    }

    /**
     * Create deploy order.
     *
     * @param p_command the p command
     */
    public void createDeployOrder(String p_command) {
        if (this.getD_orders() == null || this.getD_orders().isEmpty()) {
            d_orders = new ArrayList<>();
        } else {
            d_orders = this.getD_orders();
        }

        String l_countryName = p_command.split(" ")[1];
        int l_noOfArmiesToDeploy = Integer.parseInt(p_command.split(" ")[2]);

        if (!validateCountryBelongstoPlayer(this, l_countryName)) {
            this.setD_playerLog("The country " + l_countryName + " does not belong to this player.","error");
        }
        else if (validateNoOfArmiesToDeploy(this, l_noOfArmiesToDeploy)) {
            this.setD_playerLog(ProjectConstants.INVALID_NO_OF_ARMIES,"error");
        }
        else {
            Orders l_order = new Deploy(this, l_countryName, l_noOfArmiesToDeploy);
            d_orders.add(l_order);

            Integer l_unallocatedArmies = this.getD_unallocatedArmies() - l_noOfArmiesToDeploy;
            this.setD_unallocatedArmies(l_unallocatedArmies);

            this.setD_playerLog(ProjectConstants.ORDER_ADDED,"effect");
        }
    }

    /**
     * Validate country belongsto player boolean.
     *
     * @param p_player      the p player
     * @param p_countryName the p country name
     * @return the boolean
     */
    private boolean validateCountryBelongstoPlayer(Player p_player, String p_countryName) {
        for (Country l_eachCountry : p_player.getD_currentCountries()) {
            if (l_eachCountry.getD_countryName().equals(p_countryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate no of armies to deploy boolean.
     *
     * @param p_player           the p player
     * @param p_noOfDeployArmies the p no of deploy armies
     * @return the boolean
     */
    private boolean validateNoOfArmiesToDeploy(Player p_player, int p_noOfDeployArmies){
        if(p_player.getD_unallocatedArmies() < p_noOfDeployArmies){
            return true;
        }
        return false;
    }

    /**
     * Check for more order.
     */
    public void checkForMoreOrder() {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Do you still want to give order for player : " + this.getD_name() + " in the next turn?");
        System.out.println("Press Y for Yes and N for No");
        try {
            String l_check = l_reader.readLine();
            if(l_check.equalsIgnoreCase("Y") || l_check.equalsIgnoreCase("N")){
                this.setMoreOrders(l_check.equalsIgnoreCase("Y"));
            }
            else{
                System.err.println("Invalid Input Entered");
                this.checkForMoreOrder();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Create advance order.
     *
     * @param p_inputCommand the p input command
     * @param p_currentState the p current state
     */
    public void createAdvanceOrder(String p_inputCommand, CurrentState p_currentState) {
        String l_sourceCountry = p_inputCommand.split(" ")[1];
        String l_targetCountry = p_inputCommand.split(" ")[2];
        Integer l_noOfArmies = Integer.valueOf(p_inputCommand.split(" ")[3]);
        if(checkCountryPresent(l_sourceCountry, p_currentState) &&
            checkCountryPresent(l_targetCountry, p_currentState) &&
            l_noOfArmies > 0 &&
            checkAdjacentCountry(l_sourceCountry,l_targetCountry,p_currentState)){
            this.d_orders.add(new Advance(l_sourceCountry, l_targetCountry, l_noOfArmies, this));
            this.setD_playerLog("Advance order is added for execution for player " + this.getD_name(),"effect");
        }
        else{
            this.setD_playerLog("Invalid Arguments passed for advance order.","error");
        }
    }

    /**
     * Check adjacent country boolean.
     *
     * @param p_sourceCountry the p source country
     * @param p_targetCountry the p target country
     * @param p_currentState  the p current state
     * @return the boolean
     */
    private boolean checkAdjacentCountry(String p_sourceCountry, String p_targetCountry, CurrentState p_currentState) {
        Country l_sourceCountry = p_currentState.getD_map().getCountryByName(p_sourceCountry);
        Country l_targetCountry = p_currentState.getD_map().getCountryByName(p_targetCountry);

        if(!l_sourceCountry.getD_neighbouringCountriesId().contains(l_targetCountry.getD_countryID())){
            this.setD_playerLog("Target Country is not adjacent to Source Country","error");
            return false;
        }
        return true;

    }

    /**
     * Check country present boolean.
     *
     * @param p_countryName  the p country name
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean checkCountryPresent(String p_countryName, CurrentState p_currentState){
        if(p_currentState.getD_map().getCountryByName(p_countryName) == null){
            this.setD_playerLog(ProjectConstants.NO_COUNTRY_IN_MAP,"error");
            return false;
        }
        return true;
    }

    /**
     * Remove card.
     *
     * @param p_cardName the p card name
     */
    public void removeCard(String p_cardName){
        this.d_cardsOwnedByPlayer.remove(p_cardName);
    }

    /**
     * Handle card command.
     *
     * @param p_inputCommand the p input command
     * @param p_currentState the p current state
     */
    public void handleCardCommand(String p_inputCommand, CurrentState p_currentState){
        if (checkCardArguments(p_inputCommand)) {
            switch (p_inputCommand.split(" ")[0]) {
                case "bomb":
                    Card l_bombOrder = new CardBomb(this, p_inputCommand.split(" ")[1]);
                    if (l_bombOrder.validOrderCheck(p_currentState)) {
                        this.d_orders.add(l_bombOrder);
                        this.setD_playerLog("Bomb order is added for execution for player " + this.getD_name(), "effect");
                        p_currentState.updateLog("Bomb order is added for execution for player " + this.getD_name(), "effect");
                    }
                    break;
                case "blockade":
                    Card l_blockadeOrder = new CardBlockade(this, p_inputCommand.split(" ")[1]);
                    if (l_blockadeOrder.validOrderCheck(p_currentState)) {
                        this.d_orders.add(l_blockadeOrder);
                        this.setD_playerLog("Blockade order is added for execution for player " + this.getD_name(), "effect");
                        p_currentState.updateLog("Blockade order is added for execution for player " + this.getD_name(), "effect");
                    }
                    break;
                case "airlift":
                    Card l_airliftOrder = new CardAirlift(this, p_inputCommand.split(" ")[1], p_inputCommand.split(" ")[2], Integer.parseInt(p_inputCommand.split(" ")[3]));
                    if (l_airliftOrder.validOrderCheck(p_currentState)) {
                        this.d_orders.add(l_airliftOrder);
                        this.setD_playerLog("Airlift order is added for execution for player " + this.getD_name(), "effect");
                        p_currentState.updateLog("Airlift order is added for execution for player " + this.getD_name(), "effect");
                    }
                    break;
                case "negotiate":
                    Card l_negotiateOrder = new CardNegotiate(this, p_inputCommand.split(" ")[1]);
                    if (l_negotiateOrder.validOrderCheck(p_currentState)) {
                        this.d_orders.add(l_negotiateOrder);
                        this.setD_playerLog("Negotiate order is added for execution for player " + this.getD_name(), "effect");
                        p_currentState.updateLog("Negotiate order is added for execution for player " + this.getD_name(), "effect");
                    }
                    break;
            }
        }
        else {
            this.setD_playerLog("Invalid Arguments passed for card order.","error");
        }
    }

    /**
     * Check card arguments boolean.
     *
     * @param p_inputCommand the p input command
     * @return the boolean
     */
    private boolean checkCardArguments(String p_inputCommand) {
        if(p_inputCommand.split(" ")[0].equalsIgnoreCase("negotiate") && p_inputCommand.split(" ").length != 2){
            this.setD_playerLog("Invalid Arguments passed for negotiate order.","error");
            return false;
        }
        else if(p_inputCommand.split(" ")[0].equalsIgnoreCase("airlift") && p_inputCommand.split(" ").length != 4){
            this.setD_playerLog("Invalid Arguments passed for airlift order.","error");
            return false;
        }
        else if(p_inputCommand.split(" ")[0].equalsIgnoreCase("blockade") && p_inputCommand.split(" ").length != 2){
            this.setD_playerLog("Invalid Arguments passed for blockade order.","error");
            return false;
        }
        else if(p_inputCommand.split(" ")[0].equalsIgnoreCase("bomb") && p_inputCommand.split(" ").length != 2){
            this.setD_playerLog("Invalid Arguments passed for bomb order.","error");
            return false;
        }
        return true;
    }

    /**
     * Add negotiate player.
     *
     * @param p_negotiatePlayer the p negotiate player
     */
    public void addNegotiatePlayer(Player p_negotiatePlayer){
        this.d_negotiatePlayers.add(p_negotiatePlayer);
    }

    /**
     * Assign card.
     */
    public void assignCard(){
        if (!d_oneCardPerTurn) {
            Random l_random = new Random();
            int str = l_random.nextInt(ProjectConstants.NO_OF_CARDS);
            this.d_cardsOwnedByPlayer.add(ProjectConstants.ALL_CARDS.get(str));
            d_oneCardPerTurn = true;
            this.setD_playerLog("Card : " + ProjectConstants.ALL_CARDS.get(str) + " assigned to player : " + this.getD_name(), "effect");
        }
        else {
            this.setD_playerLog("Card cannot be assigned to player : " + this.getD_name() + " as one card per turn is already assigned", "error");
        }
    }

    /**
     * Negotiation validation boolean.
     *
     * @param p_targetCountryName the p target country name
     * @return the boolean
     */
    public boolean negotiationValidation(String p_targetCountryName) {
        boolean l_canAttack = true;
        for(Player l_eachPlayer : d_negotiatePlayers){
            if(l_eachPlayer.getCountryNames().contains(p_targetCountryName)){
                l_canAttack = false;
            }
        }
        return l_canAttack;
    }

    /**
     * Gets country names.
     *
     * @return the country names
     */
    public List<String> getCountryNames() {
        List<String> l_countryNames = new ArrayList<>();
        for(Country l_eachCountry : d_currentCountries){
            l_countryNames.add(l_eachCountry.getD_countryName());
        }
        return l_countryNames;
    }
}
