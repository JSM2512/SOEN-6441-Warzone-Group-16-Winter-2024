package Models;

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

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public Integer getD_unallocatedArmies() {
        return d_unallocatedArmies;
    }

    public void setD_unallocatedArmies(Integer d_unallocatedArmies) {
        this.d_unallocatedArmies = d_unallocatedArmies;
    }

    public List<Country> getD_currentCountries() {
        return d_currentCountries;
    }

    public void setD_currentCountries(List<Country> d_currentCountries) {
        this.d_currentCountries = d_currentCountries;
    }

    public List<Continent> getD_currentContinents() {
        return d_currentContinents;
    }

    public void setD_currentContinents(List<Continent> d_currentContinents) {
        this.d_currentContinents = d_currentContinents;
    }

    public List<Orders> getD_orders() {
        return d_orders;
    }

    public void setD_orders(List<Orders> d_orders) {
        this.d_orders = d_orders;
    }

}
