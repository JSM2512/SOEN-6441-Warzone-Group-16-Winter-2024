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

    public void showMap() {
        if (d_continents != null) {
            System.out.println("************************************************************************************************************************");
            System.out.println("                                                   CONTINENT DETAILS                                                    ");
            System.out.println("************************************************************************************************************************");
            for (Continent l_eachContinent : d_continents) {
                System.out.println("****************************************************************************************************************************");
                System.out.println("| Continent ID : " + l_eachContinent.getD_continentID() + ". Continent Name : " + l_eachContinent.getD_continentName());
                System.out.println("****************************************************************************************************************************");
                System.out.println("| Country ID.     | Country Name                           | Armies                        | Connections                                           ");
                System.out.println("****************************************************************************************************************************");

                List<Country> l_countriesOfContinent = l_eachContinent.getD_countries();
                if (l_countriesOfContinent != null) {
                    for (Country l_eachCountry : l_countriesOfContinent) {
                        System.out.printf("| %-15s| %-40s| %-30s| ", l_eachCountry.getD_countryID(), l_eachCountry.getD_countryName(), l_eachCountry.getD_armies());
                        String l_neighbourCountryNames = "";
                        for (Integer l_neighbourID : l_eachCountry.getD_neighbouringCountriesId()) {
                            l_neighbourCountryNames += getCountryByID(l_neighbourID) + ", ";
                        }
                        System.out.println(l_neighbourCountryNames);
                    }
                    System.out.println("************************************************************************************************************************");
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println();
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
