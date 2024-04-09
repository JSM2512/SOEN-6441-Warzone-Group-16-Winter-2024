package Models;

import Constants.ProjectConstants;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Model Class Map.
 */
public class Map implements Serializable {

    /**
     * The D map name.
     */
    String d_mapName;
    /**
     * The D map countries.
     */
    List<Country> d_mapCountries;
    /**
     * The D map continents.
     */
    List<Continent> d_mapContinents;

    /**
     * Instantiates a new Map.
     */
    public Map() {
    }

    /**
     * Gets d map name.
     *
     * @return the d map name
     */
    public String getD_mapName() {
        return d_mapName;
    }

    /**
     * Sets d map name.
     *
     * @param p_mapName the p map name
     */
    public void setD_mapName(String p_mapName) {
        this.d_mapName = p_mapName;
    }

    /**
     * Gets d map countries.
     *
     * @return the d map countries
     */
    public List<Country> getD_mapCountries() {
        return d_mapCountries;
    }

    /**
     * Sets d map countries.
     *
     * @param p_mapCountries the p map countries
     */
    public void setD_mapCountries(List<Country> p_mapCountries) {
        this.d_mapCountries = p_mapCountries;
    }

    /**
     * Gets d map continents.
     *
     * @return the d map continents
     */
    public List<Continent> getD_mapContinents() {
        return d_mapContinents;
    }

    /**
     * Sets d map continents.
     *
     * @param p_mapContinents the p map continents
     */
    public void setD_mapContinents(List<Continent> p_mapContinents) {
        this.d_mapContinents = p_mapContinents;
    }


    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Map{" +
                "d_mapName='" + d_mapName + '\'' +
                ", d_mapCountries=" + d_mapCountries +
                ", d_mapContinents=" + d_mapContinents +
                '}';
    }

    /**
     * Get country by name country.
     *
     * @param p_countryName the p country name
     * @return the country
     */
    public Country getCountryByName(String p_countryName){

        for(Country l_country : d_mapCountries){
            if(l_country.d_countryName.equals(p_countryName)){
                return l_country;
            }
        }
        System.out.println(ProjectConstants.COUNTRY_DOES_NOT_EXIST);
        return null;
    }

    /**
     * Get continent by name continent.
     *
     * @param p_continentName the p continent name
     * @return the continent
     */
    public Continent getContinentByName(String p_continentName){

        for(Continent l_continent : d_mapContinents){
            if(l_continent.d_continentName.equals(p_continentName)){
                return l_continent;
            }
        }
        System.out.println(ProjectConstants.CONTINENT_DOES_NOT_EXIST);
        return null;
    }

    /**
     * Validate map boolean.
     *
     * @return the boolean
     */
    public boolean validateMap() {
        if(validateCountriesAndContinents() && validateContinentSubgraph() && validateCountryConnections()){
            return true;
        }
        return false;
    }

    /**
     * Validate country connections boolean.
     *
     * @return the boolean
     */
    public boolean validateCountryConnections() {
        if(d_mapCountries == null || d_mapCountries.isEmpty()){
            return false;
        }
        HashMap<Integer,Boolean> l_visited = new HashMap<>();
        for(Country l_eachCountry : d_mapCountries){
            l_visited.put(l_eachCountry.getD_countryID(),false);
        }

        dfsCountry(d_mapCountries.get(0), l_visited);
        for(java.util.Map.Entry<Integer,Boolean> l_entry : l_visited.entrySet()){
            if(!l_entry.getValue()){
                System.out.println("Country : "+getCountry(l_entry.getKey()).getD_countryName()+" is not reachable");
            }
        }
        return !l_visited.containsValue(false);
    }

    /**
     * Dfs country.
     *
     * @param p_country the p country
     * @param p_visited the p visited
     */
    private void dfsCountry(Country p_country, HashMap<Integer, Boolean> p_visited) {
        p_visited.put(p_country.getD_countryID(),true);
        for(Country l_eachCountry : getAdjacentCountries(p_country)){
            if(!p_visited.get(l_eachCountry.getD_countryID())){
                dfsCountry(l_eachCountry,p_visited);
            }
        }
    }

    /**
     * Gets adjacent countries.
     *
     * @param p_country the p country
     * @return the adjacent countries
     */
    private List<Country> getAdjacentCountries(Country p_country) {
        List<Country> l_adjacentCountries = new ArrayList<>();
        if(!p_country.getD_neighbouringCountriesId().isEmpty()){
            for(int i=0;i<p_country.getD_neighbouringCountriesId().size();i++){
                l_adjacentCountries.add( getCountry( p_country.getD_neighbouringCountriesId().get(i)));
            }
        }
        return l_adjacentCountries;
    }

    /**
     * Validate continent subgraph boolean.
     *
     * @return the boolean
     */
    public boolean validateContinentSubgraph() {
        for(Continent l_eachContinent : d_mapContinents){
            if(l_eachContinent.d_countries == null || l_eachContinent.d_countries.isEmpty()){
                System.out.println("Continent: "+l_eachContinent.getD_continentName()+" has no countries.");
                return false;
            }
            else{
                if(!connectivityOfCountriesInContinent(l_eachContinent)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Connectivity of countries in continent boolean.
     *
     * @param p_EachContinent the p each continent
     * @return the boolean
     */
    private boolean connectivityOfCountriesInContinent(Continent p_EachContinent) {
        HashMap<Integer,Boolean> l_visited = new HashMap<>();
        for(Country l_eachCountry : p_EachContinent.d_countries){
            l_visited.put(l_eachCountry.d_countryID,false);
        }
        dfsSubgraph(p_EachContinent.d_countries.get(0), l_visited, p_EachContinent);

        for(java.util.Map.Entry<Integer,Boolean> l_entry : l_visited.entrySet()){
            if(!l_entry.getValue()){
                Country l_country = getCountry(l_entry.getKey());
                System.out.println("Country : "+l_country.d_countryName+" is not reachable.");
            }
        }
        return !l_visited.containsValue(false);
    }

    /**
     * Gets country.
     *
     * @param p_countryID the p country id
     * @return the country
     */
    private Country getCountry(Integer p_countryID) {
        for(Country l_country : d_mapCountries){
            if(l_country.getD_countryID().equals(p_countryID)){
                return l_country;
            }
        }
        return null;
    }


    /**
     * Dfs subgraph.
     *
     * @param p_country   the p country
     * @param p_visited   the p visited
     * @param p_continent the p continent
     */
    private void dfsSubgraph(Country p_country, HashMap<Integer, Boolean> p_visited, Continent p_continent) {
        p_visited.put(p_country.d_countryID, true);
        for(Country l_eachConnectedCountry : p_continent.getD_countries()){
            if(p_country.getD_neighbouringCountriesId().contains(l_eachConnectedCountry.getD_countryID())){
                if(!p_visited.get(l_eachConnectedCountry.getD_countryID())){
                    dfsSubgraph(l_eachConnectedCountry, p_visited, p_continent);
                }
            }
        }
    }

    /**
     * Validate countries and continents boolean.
     *
     * @return the boolean
     */
    public boolean validateCountriesAndContinents() {
        if(d_mapContinents == null || d_mapContinents.isEmpty()){
            System.out.println(ProjectConstants.NO_CONTINENT_IN_MAP);
            return false;
        }
        else if(d_mapCountries == null || d_mapCountries.isEmpty()){
            System.out.println(ProjectConstants.NO_COUNTRY_IN_MAP);
            return false;
        }
        else{
            for(Country l_eachCountry : d_mapCountries){
                if(l_eachCountry.getD_neighbouringCountriesId().isEmpty()){
                    System.out.println("Country: "+l_eachCountry.getD_countryName()+" does not have any neighbours.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Add continent.
     *
     * @param p_mapContinentName the p map continent name
     * @param p_continentValue   the p continent value
     */
    public void addContinent(String p_mapContinentName, Integer p_continentValue) {
        if (d_mapContinents == null){
            d_mapContinents = new ArrayList<>();
            Continent l_newContinent = new Continent(1,p_mapContinentName,p_continentValue);
            d_mapContinents.add(l_newContinent);
        }
        else{
            for(Continent l_continent :d_mapContinents) {
                if(l_continent.getD_continentName().equals(p_mapContinentName)){
                    System.out.println("Continent : "+p_mapContinentName+" already exists.");
                    return;
                }
            }
            int l_mapContinentId = getMaxContinentID() + 1;
            Continent l_newContinent = new Continent(l_mapContinentId,p_mapContinentName,p_continentValue);
            d_mapContinents.add(l_newContinent);
            System.out.println("Continent " + l_newContinent.getD_continentName() +" inserted Successfully!");
        }
    }

    /**
     * Gets max country id.
     *
     * @return the max country id
     */
    private int getMaxCountryID() {
        if(d_mapCountries == null){
            d_mapCountries = new ArrayList<>();
            return 0;
        } else if (d_mapCountries.isEmpty()) {
            return 0;
        }
        else {
            int l_max = Integer.MIN_VALUE;
            for (Country l_eachCountry : d_mapCountries) {
                if (l_max < l_eachCountry.getD_countryID()) {
                    l_max = l_eachCountry.getD_countryID();
                }
            }
            return l_max;
        }
    }

    /**
     * Gets max continent id.
     *
     * @return the max continent id
     */
    private int getMaxContinentID() {
        if(d_mapContinents == null){
            d_mapContinents = new ArrayList<>();
            return 0;
        } else if (d_mapContinents.isEmpty()) {
            return 0;
        }
        else {
            int l_max = Integer.MIN_VALUE;
            for (Continent l_eachContinent : d_mapContinents) {
                if (l_max < l_eachContinent.getD_continentID()) {
                    l_max = l_eachContinent.getD_continentID();
                }
            }
            return l_max;
        }
    }

    /**
     * Remove continent.
     *
     * @param p_mapContinentName the p map continent name
     */
    public void removeContinent(String p_mapContinentName) {
        if(d_mapContinents == null && d_mapContinents.isEmpty()){
            System.out.println("Continent : "+p_mapContinentName+" does not exists.");
        }
        else{
            boolean l_isPresent = false;
            for(Continent l_continent : d_mapContinents){
                if(l_continent.getD_continentName().equals(p_mapContinentName)){
                    l_isPresent = true;
                }
            }
            if(l_isPresent){
                if(getContinentByName(p_mapContinentName).getD_countries() != null && !getContinentByName(p_mapContinentName).getD_countries().isEmpty()) {
                    for (Country l_country : getContinentByName(p_mapContinentName).getD_countries()) {
                        removeAllCountryNeighbours(l_country);
                        d_mapCountries.remove(l_country);
                    }
                }
                d_mapContinents.remove(getContinentByName(p_mapContinentName));
                System.out.println("Continent " + p_mapContinentName +" removed successfully!");
            }
        }
    }

    /**
     * Remove all country neighbours.
     *
     * @param p_country the p country
     */
    private void removeAllCountryNeighbours(Country p_country) {
        p_country.getD_neighbouringCountriesId().clear();
        for(Country l_eachCountry : d_mapCountries){
            if(l_eachCountry.getD_neighbouringCountriesId().contains(p_country.getD_countryID())){
                l_eachCountry.getD_neighbouringCountriesId().remove(p_country.getD_countryID());
            }
        }
    }


    /**
     * Add country.
     *
     * @param p_countryName   the p country name
     * @param p_continentName the p continent name
     */
    public void addCountry(String p_countryName, String p_continentName) {
        if (d_mapCountries == null){
            d_mapCountries = new ArrayList<>();
        }
        else{
            for(Country l_country : d_mapCountries) {
                if(l_country.getD_countryName().equals(p_countryName)){
                    System.out.println("Country : "+p_countryName+" already exists.");
                    return;
                }
            }
        }
        int l_countryID = getMaxCountryID() + 1;
        int l_continentID = getContinentIDByName(p_continentName);
        if(l_continentID != -1) {
            Country l_newCountry = new Country(l_countryID, p_countryName, l_continentID);
            d_mapCountries.add(l_newCountry);
            for (Continent l_continent : d_mapContinents) {
                if (l_continent.getD_continentID() == l_continentID) {
                    l_continent.addCountry(l_newCountry);
                    System.out.println("Country " + l_newCountry.getD_countryName() +" inserted Successfully!");
                }
            }
        }
        else{
            System.out.println(ProjectConstants.CONTINENT_DOES_NOT_EXIST);
        }
    }

    /**
     * Gets continent id by name.
     *
     * @param p_continentName the p continent name
     * @return the continent id by name
     */
    private int getContinentIDByName(String p_continentName) {
        if(d_mapContinents == null){
            System.out.println(ProjectConstants.NO_CONTINENT_IN_MAP);
            return -1;
        }
        else{
            for(Continent l_eachContinent : d_mapContinents){
                if(l_eachContinent.getD_continentName().equals(p_continentName)){
                    return l_eachContinent.getD_continentID();
                }
            }
        }
        return -1;
    }

    /**
     * Add neighbour.
     *
     * @param p_countryID   the p country id
     * @param p_neighbourID the p neighbour id
     */
    public void addNeighbour(int p_countryID, int p_neighbourID) {
        if(d_mapCountries == null){
            System.out.println(ProjectConstants.NO_COUNTRY_IN_MAP);
        } else if (getCountry(p_countryID) == null || getCountry(p_neighbourID) == null) {
            if(getCountry(p_countryID) == null){
                System.out.println("Country with ID : "+p_countryID+" does not exists in the Map.");
            }
            else{
                System.out.println("Country with ID : "+p_neighbourID+" does not exists in the Map.");
            }
        } else{
            getCountry(p_countryID).addCountryNeighbour(p_neighbourID);
            System.out.println("Country " + p_countryID + " added as neighbor to " + p_neighbourID);
            getCountry(p_neighbourID).addCountryNeighbour(p_countryID);
            System.out.println("Country " + p_neighbourID + " added as neighbor to " + p_countryID);
        }
    }

    /**
     * Remove neighbour.
     *
     * @param p_countryID   the p country id
     * @param p_neighbourID the p neighbour id
     */
    public void removeNeighbour(int p_countryID, int p_neighbourID) {
        if (d_mapCountries == null) {
            System.out.println(ProjectConstants.NO_COUNTRY_IN_MAP);
        } else if (getCountry(p_countryID) == null || getCountry(p_neighbourID) == null) {
            if (getCountry(p_countryID) == null) {
                System.out.println("Country with ID : " + p_countryID + " does not exists in the Map.");
            } else {
                System.out.println("Country with ID : " + p_neighbourID + " does not exists in the Map.");
            }
        } else {
            getCountry(p_countryID).removeCountryNeighbourIfPresent(p_neighbourID);
            System.out.println("Country " + p_countryID + " removed as neighbor to " + p_neighbourID);
            getCountry(p_neighbourID).removeCountryNeighbourIfPresent(p_countryID);
            System.out.println("Country " + p_neighbourID + " removed as neighbor to " + p_countryID);
        }
    }

    /**
     * Remove country.
     *
     * @param p_removeCountryName the p remove country name
     */
    public void removeCountry(String p_removeCountryName) {
        if(d_mapCountries == null || d_mapCountries.isEmpty()){
            System.out.println("Country : " + p_removeCountryName + " does not exist.");
        }

        if(getCountryByName(p_removeCountryName) != null){
            for(Continent l_eachContinent : d_mapContinents){
                if(l_eachContinent.getD_continentID().equals(getCountryByName(p_removeCountryName).getD_continentID())){
                    l_eachContinent.removeCountry(getCountryByName(p_removeCountryName));
                }
            }
            removeAllCountryNeighbours(getCountryByName(p_removeCountryName));
            d_mapCountries.remove(getCountryByName(p_removeCountryName));
            System.out.println("Country " + p_removeCountryName +" removed successfully!");
        }
    }

    /**
     * Gets country name by id.
     *
     * @param p_neighbourID the p neighbour id
     * @return the country name by id
     */
    public String getCountryNameByID(Integer p_neighbourID) {
        for(Country l_eachCountry : d_mapCountries){
            if(l_eachCountry.getD_countryID().equals(p_neighbourID)){
                return l_eachCountry.getD_countryName();
            }
        }
        return "null";
    }

    /**
     * Get country by id country.
     *
     * @param p_countryId the p country id
     * @return the country
     */
    public Country getCountryById(Integer p_countryId){
        for(Country l_country : d_mapCountries){
            if(l_country.d_countryID.equals(p_countryId)){
                return l_country;
            }
        }
        System.out.println(ProjectConstants.COUNTRY_DOES_NOT_EXIST);
        return null;
    }

    /**
     * Gets continent by id.
     *
     * @param p_continentID the p continent id
     * @return the continent by id
     */
    public Continent getContinentById(Integer p_continentID) {
        for(Continent l_eachContinent :d_mapContinents){
            if(l_eachContinent.d_continentID.equals(p_continentID)){
                return l_eachContinent;
            }
        }
        return null;
    }
}
