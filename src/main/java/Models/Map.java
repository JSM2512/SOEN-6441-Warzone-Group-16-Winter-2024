package Models;

import java.sql.SQLOutput;
import java.util.List;
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
}
