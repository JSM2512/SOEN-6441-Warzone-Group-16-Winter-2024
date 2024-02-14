package Models;

import Controller.CommandHandler;
import Controller.PlayerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Player {
    String d_name;
    Integer d_unallocatedArmies;
    List<Country> d_currentCountries;
    List<Continent> d_currentContinents;
    List<Orders> d_orders;

    public Player(String p_name){
        this.d_name = p_name;
        this.d_unallocatedArmies = 0;
        this.d_orders = new ArrayList<Orders>();
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String p_name) {
        this.d_name = p_name;
    }

    public Integer getD_unallocatedArmies() {
        return d_unallocatedArmies;
    }

    public void setD_unallocatedArmies(Integer p_unallocatedArmies) {
        this.d_unallocatedArmies = p_unallocatedArmies;
    }

    public List<Country> getD_currentCountries() {
        return d_currentCountries;
    }

    public void setD_currentCountries(List<Country> p_currentCountries) {
        this.d_currentCountries = p_currentCountries;
    }

    public List<Continent> getD_currentContinents() {
        return d_currentContinents;
    }

    public void setD_currentContinents(List<Continent> p_currentContinents) {
        this.d_currentContinents = p_currentContinents;
    }

    public List<Orders> getD_orders() {
        return d_orders;
    }

    public void setD_orders(List<Orders> p_orders) {
        this.d_orders = p_orders;
    }

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

    public void issueOrder() throws IOException {
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please Enter command to deploy armies on the Map for -------------->Player : "+d_name+" Armies left:"+d_unallocatedArmies);
        String l_command = l_bufferedReader.readLine();
        CommandHandler l_commandHandler = new CommandHandler(l_command);
        if(l_commandHandler.getMainCommand().equals("deploy")){
            if(l_command.split(" ").length == 3){
                PlayerController l_gamePlayerController = new PlayerController();
                l_gamePlayerController.createDeployOrder(l_command, this);
            }
        }
    }
}
