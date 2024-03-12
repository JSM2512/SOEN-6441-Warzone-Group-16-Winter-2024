package Models;

import Constants.ProjectConstants;
import Utils.CommandHandler;
import Controller.PlayerController;

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
     * The D name.
     */
    String d_name;
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
    List<String> d_cardsOwnedByPlayer = new ArrayList<>();
    List<Player> d_negotiatePlayers = new ArrayList<>();

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

    public List<String> getD_cardsOwnedByPlayer() {
        return d_cardsOwnedByPlayer;
    }

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
            System.out.println("The country " + l_countryName + " does not belong to this player.");
        }
        else if (validateNoOfArmiesToDeploy(this, l_noOfArmiesToDeploy)) {
            System.out.println(ProjectConstants.INVALID_NO_OF_ARMIES);
//            d_currentState.getD_modelLogger().setD_message(ProjectConstants.INVALID_NO_OF_ARMIES,"Type 1");
        }
        else {
            Orders l_order = new Deploy(this, l_countryName, l_noOfArmiesToDeploy);
            d_orders.add(l_order);

            Integer l_unallocatedArmies = this.getD_unallocatedArmies() - l_noOfArmiesToDeploy;
            this.setD_unallocatedArmies(l_unallocatedArmies);

            System.out.println(ProjectConstants.ORDER_ADDED);
//            d_currentState.getD_modelLogger().setD_message(ProjectConstants.ORDER_ADDED,"Type 1");

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
            System.out.println("Advance order is added for execution for player " + this.getD_name());
        }
        else{
            System.out.println("Invalid Arguments passed for advance order.");
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
            System.out.println(ProjectConstants.NO_COUNTRY_IN_MAP);
            return false;
        }
        return true;
    }

    public void removeCard(String p_cardName){
        this.d_cardsOwnedByPlayer.remove(p_cardName);
    }

    public void handleCardCommand(String p_inputCommand, CurrentState p_currentState){
        switch (p_inputCommand.split(" ")[0]){
            case "bomb":
                Card l_bombOrder = new CardBomb(this, p_inputCommand.split(" ")[1]);
                if(l_bombOrder.validOrderCheck(p_currentState)){
                    this.d_orders.add(l_bombOrder);
                    // Logger Info needed
                    // Current State Log Needed
                }
                break;
            case "blockade":
                Card l_blockadeOrder = new CardBlockade(this, p_inputCommand.split(" ")[1]);
                if(l_blockadeOrder.validOrderCheck(p_currentState)){
                    this.d_orders.add(l_blockadeOrder);
                    // Logger Info needed
                    // Current State Log Needed
                }
                break;
            case "airlift":
                Card l_airliftOrder = new CardAirlift(this, p_inputCommand.split(" ")[1], p_inputCommand.split(" ")[2], Integer.parseInt(p_inputCommand.split(" ")[3]));
                if(l_airliftOrder.validOrderCheck(p_currentState)){
                    this.d_orders.add(l_airliftOrder);
                    // Logger Info needed
                    // Current State Log Needed
                }
                break;
            case "negotiate":
                Card l_negotiateOrder = new CardNegotiate(this, p_inputCommand.split(" ")[1]);
                if(l_negotiateOrder.validOrderCheck(p_currentState)){
                    this.d_orders.add(l_negotiateOrder);
                    // Logger Info needed
                    // Current State Log Needed
                }
                break;
        }
    }

    public void addNegotiatePlayer(Player p_negotiatePlayer){
        this.d_negotiatePlayers.add(p_negotiatePlayer);
    }

    public void assignCard(){
        Random l_random = new Random();
        this.d_cardsOwnedByPlayer.add(ProjectConstants.ALL_CARDS.get(l_random.nextInt(ProjectConstants.NO_OF_CARDS)));
        // Logger Info needed
    }
}
