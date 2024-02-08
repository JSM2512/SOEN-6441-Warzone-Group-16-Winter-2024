package Views;

import Models.*;

import java.util.List;

public class MapView {
    Map d_map;
    List<Country> d_countries;
    List<Continent> d_continents;
    List<Player> d_players;
    CurrentState d_currentState;

    public MapView(CurrentState p_currentState) {
        this.d_currentState = p_currentState;
        this.d_map = p_currentState.getD_map();
        this.d_countries = p_currentState.getD_map().getD_mapCountries();
        this.d_continents = p_currentState.getD_map().getD_mapContinents();
    }

    public void showMap(){
        if(d_continents != null){
            System.out.println("+======================================================================================================================+");
            System.out.println("                                                   CONTINENT DETAILS                                                    ");
            System.out.println("+======================================================================================================================+");
            int l_continentIndx = 1;
            for(Continent l_eachContinent : d_continents) {
                System.out.println("+======================================================================================================================+");
                System.out.println("| " + l_continentIndx + " Continent Name : " + l_eachContinent.getD_continentName());
                System.out.println("+======================================================================================================================+");
                System.out.println("| No.     | Country Name                        | Armies        | Connections                                           ");
                System.out.println("+======================================================================================================================+");

                List<Country> l_countriesOfContinent = l_eachContinent.getD_countries();
                if (l_countriesOfContinent != null) {
                    int l_indx = 1;
                    for (Country l_eachCountry : l_countriesOfContinent) {
                        System.out.print("| " + l_indx + "       | " + l_eachCountry.getD_countryName() + "                             | " + l_eachCountry.getD_armies() + "          | ");
                        String l_neighbourCountryNames = "";
                        for (Integer l_neighbourID : l_eachCountry.getD_neighbouringCountriesId()) {
                            l_neighbourCountryNames += getCountryByID(l_neighbourID) + ", ";
                        }
                        System.out.println(l_neighbourCountryNames);
                        l_indx++;
                    }
                }
                l_continentIndx++;
            }
        }
    }

    private String getCountryByID(Integer p_neighbourID) {
        for(Country l_eachCountry : d_map.getD_mapCountries()){
            if(l_eachCountry.getD_countryID().equals(p_neighbourID)){
                return l_eachCountry.getD_countryName();
            }
        }
        return "null";
    }
}
