package Views;

import Models.*;

import java.util.List;

/**
 * The type Map view.
 */
public class MapView {
    /**
     * The D map.
     */
    Map d_map;
    /**
     * The D countries.
     */
    List<Country> d_countries;
    /**
     * The D continents.
     */
    List<Continent> d_continents;
    /**
     * The D current state.
     */
    CurrentState d_currentState;

    /**
     * Instantiates a new Map view.
     *
     * @param p_currentState the p current state
     */
    public MapView(CurrentState p_currentState) {
        this.d_currentState = p_currentState;
        this.d_map = p_currentState.getD_map();
        this.d_countries = p_currentState.getD_map().getD_mapCountries();
        this.d_continents = p_currentState.getD_map().getD_mapContinents();
    }

    /**
     * Show map.
     */
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
                            l_neighbourCountryNames += d_map.getCountryNameByID(l_neighbourID) + ", ";
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
}
