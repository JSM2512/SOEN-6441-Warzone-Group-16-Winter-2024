package Models;

import Utils.CommandHandler;
import Controller.PlayerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
     * Instantiates a new Player.
     *
     * @param p_name the p name
     */
    public Player(String p_name){
        this.d_name = p_name;
        this.d_unallocatedArmies = 0;
        this.d_orders = new ArrayList<Orders>();
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
     * Issue order.
     *
     * @throws IOException the io exception
     */
    public void issueOrder() throws IOException {
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please Enter command to deploy armies on the Map  for ----------->  Player : " + d_name + "   Armies left : "+d_unallocatedArmies);

        String l_command = l_bufferedReader.readLine();
        CommandHandler l_commandHandler = new CommandHandler(l_command);
        if(l_commandHandler.getMainCommand().equals("deploy")){
            if(l_command.split(" ").length == 3){
                PlayerController l_gamePlayerController = new PlayerController();
                l_gamePlayerController.createDeployOrder(l_command, this);
            }
        }
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
}
