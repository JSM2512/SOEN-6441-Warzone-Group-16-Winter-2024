package Models;

import java.util.*;

public class Map {

    String d_mapName;
    List<Country> d_mapCountries;
    List<Continent> d_mapContinents;

    public String getD_mapName() {
        return d_mapName;
    }

    public void setD_mapName(String p_mapName) {
        this.d_mapName = p_mapName;
    }

    public List<Country> getD_mapCountries() {
        return d_mapCountries;
    }

    public void setD_mapCountries(List<Country> p_mapCountries) {
        this.d_mapCountries = p_mapCountries;
    }

    public List<Continent> getD_mapContinents() {
        return d_mapContinents;
    }

    public void setD_mapContinents(List<Continent> p_mapContinents) {
        this.d_mapContinents = p_mapContinents;
    }


    @Override
    public String toString() {
        return "Map{" +
                "d_mapName='" + d_mapName + '\'' +
                ", d_mapCountries=" + d_mapCountries +
                ", d_mapContinents=" + d_mapContinents +
                '}';
    }

    public Country getCountryByName(String p_countryName){

        for(Country l_country : d_mapCountries){
            if(l_country.d_countryName.equals(p_countryName)){
                return l_country;
            }
        }
        System.out.println("The entered country is not present.");
        return null;
    }
    public Continent getContinentByName(String p_continentName){

        for(Continent l_continent : d_mapContinents){
            if(l_continent.d_continentName.equals(p_continentName)){
                return l_continent;
            }
        }
        System.out.println("The entered continent is not present.");
        return null;
    }

    public boolean validateMap() {
        if(validateCountriesAndContinents() && validateContinentSubgraph() && validateCountryConnections()){
            return true;
        }
        return false;
    }

    private boolean validateCountryConnections() {
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

    private void dfsCountry(Country p_country, HashMap<Integer, Boolean> p_visited) {
        p_visited.put(p_country.getD_countryID(),true);
        for(Country l_eachCountry : getAdjacentCountries(p_country)){
            if(!p_visited.get(l_eachCountry.getD_countryID())){
                dfsCountry(l_eachCountry,p_visited);
            }
        }
    }

    private List<Country> getAdjacentCountries(Country p_country) {
        List<Country> l_adjacentCountries = new ArrayList<>();
        if(!p_country.getD_neighbouringCountriesId().isEmpty()){
            for(int i=0;i<p_country.getD_neighbouringCountriesId().size();i++){
                l_adjacentCountries.add( getCountry( p_country.getD_neighbouringCountriesId().get(i)));
            }
        }
        return l_adjacentCountries;
    }

    private boolean validateContinentSubgraph() {
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

    private Country getCountry(Integer p_countryID) {
        for(Country l_country : d_mapCountries){
            if(l_country.getD_countryID().equals(p_countryID)){
                return l_country;
            }
        }
        return null;
    }


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

    private boolean validateCountriesAndContinents() {
        if(d_mapContinents == null || d_mapContinents.isEmpty()){
            System.out.println("Map does not have Continents");
            return false;
        }
        else if(d_mapCountries == null || d_mapCountries.isEmpty()){
            System.out.println("Map does not have Countries");
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

    public void addContinent(Integer p_mapContinentID, Integer p_continentValue) {
        if (d_mapContinents == null){
            d_mapContinents = new ArrayList<>();
            Continent l_newContinent = new Continent(p_mapContinentID,p_continentValue);
            d_mapContinents.add(l_newContinent);
        }
        else{
            for(Continent l_continent : d_mapContinents) {
                if(l_continent.getD_continentID() == p_mapContinentID){
                    System.out.println("Continent : "+l_continent.getD_continentName()+" with ID : "+p_mapContinentID+" already exists.");
                    return;
                }
            }
            Continent l_newContinent = new Continent(p_mapContinentID,p_continentValue);
            d_mapContinents.add(l_newContinent);
            System.out.println(d_mapContinents);
        }
    }

    private int getMaxContinentID() {
        int l_max = Integer.MIN_VALUE;
        for(Continent l_eachContinent : d_mapContinents){
            if(l_max < l_eachContinent.getD_continentID()){
                l_max = l_eachContinent.getD_continentID();
            }
        }
        return l_max;
    }

    public void removeContinent(int p_mapContinentID) {
        if(d_mapContinents == null || d_mapContinents.isEmpty()){
            System.out.println("Continent with continent ID : "+p_mapContinentID+" does not exists.");
        }
        else{
            boolean l_isPresent = false;
            for(Continent l_continent : d_mapContinents){
                if(l_continent.getD_continentID().equals(p_mapContinentID)){
                    l_isPresent = true;
                }
            }
            if(l_isPresent){
                if(getContinent(p_mapContinentID).getD_countries() != null && !getContinent(p_mapContinentID).getD_countries().isEmpty()){
                    for(Country l_country : getContinent(p_mapContinentID).getD_countries()){
                        removeAllCountryNeighbours(l_country);
                        d_mapCountries.remove(l_country);
                    }
                }
                d_mapContinents.remove(getContinent(p_mapContinentID));
                System.out.println(d_mapContinents);
            }
        }
    }

    private String getContinentNameById(int p_mapContinentID) {
        if(d_mapContinents == null || d_mapContinents.isEmpty()){
            System.out.println("Continent with ID : " + p_mapContinentID + " does not exist.");
        }
        else{
            for(Continent l_eachContinent : d_mapContinents){
                if(l_eachContinent.getD_continentID() == p_mapContinentID){
                    return l_eachContinent.getD_continentName();
                }
            }
        }
        return "";
    }

    private Continent getContinent(int p_mapContinentID) {
        for(Continent l_eachContinent : d_mapContinents){
            if(l_eachContinent.getD_continentID().equals(p_mapContinentID)){
                return l_eachContinent;
            }
        }
        return null;
    }

    private void removeAllCountryNeighbours(Country p_country) {
        p_country.getD_neighbouringCountriesId().clear();
        for(Country l_eachCountry : d_mapCountries){
            if(l_eachCountry.getD_neighbouringCountriesId().contains(p_country.getD_countryID())){
                l_eachCountry.getD_neighbouringCountriesId().remove(p_country.getD_countryID());
            }
        }
    }


    public void addCountry(int p_countryId, int p_continentId) {
        if (d_mapCountries == null){
            d_mapCountries = new ArrayList<>();
        }
        else{
            for(Country l_country : d_mapCountries) {
                if(l_country.getD_countryID().equals(p_countryId)){
                    System.out.println("Country : "+p_countryId+" already exists.");
                    return;
                }
            }
        }
        Country l_newCountry = new Country(p_countryId, p_continentId);
        d_mapCountries.add(l_newCountry);
        for(Continent l_continent : d_mapContinents){
            if(l_continent.getD_continentID() == p_continentId){
                l_continent.addCountry(l_newCountry);
            }
        }
    }

    public void removeCountry(String pArguments) {
    }
    public void addNeighbour(int p_countryID, int p_neighbourID) {
        if(d_mapCountries == null){
            System.out.println("No country in Map.");
        } else if (getCountry(p_countryID) == null || getCountry(p_neighbourID) == null) {
            if(getCountry(p_countryID) == null){
                System.out.println("Country with ID : "+p_countryID+" does not exists in the Map.");
            }
            else{
                System.out.println("Country with ID : "+p_neighbourID+" does not exists in the Map.");
            }
        } else{
            getCountry(p_countryID).addNeighbours(p_neighbourID);
        }
    }

    public void removeNeighbour(int p_countryID, int p_neighbourID) {
        if (d_mapCountries == null) {
            System.out.println("No country in Map.");
        } else if (getCountry(p_countryID) == null || getCountry(p_neighbourID) == null) {
            if (getCountry(p_countryID) == null) {
                System.out.println("Country with ID : " + p_countryID + " does not exists in the Map.");
            } else {
                System.out.println("Country with ID : " + p_neighbourID + " does not exists in the Map.");
            }
        } else {
            getCountry(p_countryID).removeNeighbours(p_neighbourID);
        }
    }
    public void removeCountry(int p_removeCountryId) {
        if(d_mapCountries == null || d_mapCountries.isEmpty()){
            System.out.println("Country with country ID : " + p_removeCountryId + " does not exist.");
        }

        if(getCountry(p_removeCountryId) != null){
            for(Continent l_eachContinent : d_mapContinents){
                if(l_eachContinent.getD_continentID().equals(getCountry(p_removeCountryId).getD_continentID())){
                    l_eachContinent.removeCountry(getCountry(p_removeCountryId));
                }
            }

            removeAllCountryNeighbours(getCountry(p_removeCountryId));

            d_mapCountries.remove(getCountry(p_removeCountryId));
            System.out.println(d_mapCountries);
            System.out.println(d_mapContinents);

        }
    }
}
