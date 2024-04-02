package Services;

import Models.Continent;
import Models.Country;
import Models.CurrentState;
import Models.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MapFileReader {
    public void parseMapFile(CurrentState p_currentState, Map p_map, List<String> p_fileLines, String p_fileName) {
        List<String> l_continentData = getContinentData(p_fileLines);
        List<String> l_countryData = getCountryData(p_fileLines);
        List<String> l_borderData = getBorderData(p_fileLines);

        List<Continent> l_continents = modifyContinentData(l_continentData);
        List<Country> l_countries = modifyCountryData(l_countryData);

        l_countries = updateCountryBorderData(l_borderData, l_countries);
        l_continents = updateContinentCountries(l_continents, l_countries);

        p_map.setD_mapContinents(l_continents);
        p_map.setD_mapCountries(l_countries);
        p_map.setD_mapName(p_fileName);

        p_currentState.setD_map(p_map);
    }


    /**
     * Gets continent data.
     *
     * @param p_fileLines the p file lines
     * @return the continent data
     */
    private List<String> getContinentData(List<String> p_fileLines) {
        List<String> l_continentData = p_fileLines.subList(p_fileLines.indexOf("[continents]") + 1, p_fileLines.indexOf("[countries]") - 1);
        return l_continentData;
    }

    /**
     * Gets country data.
     *
     * @param p_fileLines the p file lines
     * @return the country data
     */
    private List<String> getCountryData(List<String> p_fileLines) {
        List<String> l_countryData = p_fileLines.subList(p_fileLines.indexOf("[countries]") + 1, p_fileLines.indexOf("[borders]") - 1);
        return l_countryData;
    }

    /**
     * Gets border data.
     *
     * @param p_fileLines the p file lines
     * @return the border data
     */
    private List<String> getBorderData(List<String> p_fileLines) {
        List<String> l_borderData = p_fileLines.subList(p_fileLines.indexOf("[borders]") + 1, p_fileLines.size());
        return  l_borderData;
    }


    /**
     * Modify continent data list.
     *
     * @param p_continentData the p continent data
     * @return the list
     */
    private List<Continent> modifyContinentData(List<String> p_continentData) {
        List<Continent> l_continents = new ArrayList<>();
        int l_continentIds = 1;
        for(String l_continent : p_continentData) {
            String[] l_continentDataList = l_continent.split(" ");
            String l_continentName = l_continentDataList[0];
            int l_continentValue = Integer.parseInt(l_continentDataList[1]);
            l_continents.add(new Continent(l_continentIds,l_continentName,l_continentValue));
            l_continentIds++;
        }
        return l_continents;
    }

    /**
     * Modify country data list.
     *
     * @param p_countryData the p country data
     * @return the list
     */
    private List<Country> modifyCountryData(List<String> p_countryData) {
        List<Country> l_countries = new ArrayList<>();
        for(String l_country : p_countryData) {
            String[] l_countryDataList = l_country.split(" ");
            int l_countryId = Integer.parseInt(l_countryDataList[0]);
            String l_countryName = l_countryDataList[1];
            int l_continentId = Integer.parseInt(l_countryDataList[2]);
            l_countries.add(new Country(l_countryId,l_countryName,l_continentId));
        }
        return l_countries;
    }

    /**
     * Update continent countries list.
     *
     * @param p_continents the p continents
     * @param p_countries  the p countries
     * @return the list
     */
    private List<Continent> updateContinentCountries(List<Continent> p_continents, List<Country> p_countries) {
        for(Country l_eachCountry : p_countries) {
            for(Continent l_eachContinent : p_continents) {
                if(l_eachCountry.getD_continentID().equals(l_eachContinent.getD_continentID())){
                    l_eachContinent.setCountry(l_eachCountry);
                }
            }

        }
        return p_continents;
    }

    /**
     * Update country border data list.
     *
     * @param p_borderData the p border data
     * @param p_countries  the p countries
     * @return the list
     */
    private List<Country> updateCountryBorderData(List<String> p_borderData, List<Country> p_countries) {
        LinkedHashMap<Integer,List<Integer>> l_borderDataMap = new LinkedHashMap<Integer,List<Integer>>();

        for(String l_eachCountryNeighbour : p_borderData) {
            if(l_eachCountryNeighbour != null ){
                ArrayList<Integer> l_neighbourCountries = new ArrayList<>();
                String[] l_borderDataSplit = l_eachCountryNeighbour.split(" ");
                for(int i=1;i<l_borderDataSplit.length;i++){
                    l_neighbourCountries.add(Integer.valueOf(l_borderDataSplit[i]));
                }
                l_borderDataMap.put(Integer.valueOf(l_borderDataSplit[0]), l_neighbourCountries);
            }
        }
        for(Country l_eachCountry : p_countries){
            l_eachCountry.setD_neighbouringCountriesId(l_borderDataMap.get(l_eachCountry.getD_countryID()));
        }
        return p_countries;
    }
}
