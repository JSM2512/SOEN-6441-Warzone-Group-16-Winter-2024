package Controller;

import Models.Continent;
import Models.Country;
import Models.CurrentState;
import Models.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


public class MapController {

    public Map loadMap(CurrentState p_currentState, String p_fileName) {
        Map l_map=new Map();
        List<String> l_fileLines = loadFile(p_fileName);
        if(!l_fileLines.isEmpty()) {
            List<String> l_continentData = getContinentData(l_fileLines);
            List<String> l_countryData = getCountryData(l_fileLines);
            List<String> l_borderData = getBorderData(l_fileLines);

            List<Continent> l_continents = modifyContinentData(l_continentData);
            List<Country> l_countries = modifyCountryData(l_countryData);

            l_countries = updateCountryBorderData(l_borderData,l_countries);
            l_continents = updateContinentCountries(l_continents ,l_countries);

            l_map.setD_mapContinents(l_continents);
            l_map.setD_mapCountries(l_countries);
            l_map.setD_mapName(p_fileName);

            p_currentState.setD_map(l_map);
        }
        return l_map;
    }

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

    private List<String> getContinentData(List<String> p_fileLines) {
        List<String> l_continentData = p_fileLines.subList(p_fileLines.indexOf("[continents]") + 1, p_fileLines.indexOf("[countries]") - 1);
        return l_continentData;
    }

    private List<String> getCountryData(List<String> p_fileLines) {
        List<String> l_countryData = p_fileLines.subList(p_fileLines.indexOf("[countries]") + 1, p_fileLines.indexOf("[borders]") - 1);
        return l_countryData;
    }

    private List<String> getBorderData(List<String> p_fileLines) {
        List<String> l_borderData = p_fileLines.subList(p_fileLines.indexOf("[borders]") + 1, p_fileLines.size());
        return  l_borderData;
    }

    private List<String> loadFile(String p_fileName) {
        String l_fileLocation = getFilePath(p_fileName);
        List<String> l_fileLines = new ArrayList<>();
        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(l_fileLocation));
            l_fileLines = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (FileNotFoundException l_e) {
            System.out.println("File not Found!");
        } catch (IOException l_e) {
            System.out.println("File not Found or corrupted file!");
        }
        return l_fileLines;
    }

    private String getFilePath(String p_fileName) {
        String l_absolutePath=new File("").getAbsolutePath();
        l_absolutePath= l_absolutePath + File.separator + "src" + File.separator + "main" + File.separator + "maps" + File.separator + p_fileName;
        return l_absolutePath;
    }


    public void editMap(CurrentState p_currentState, String p_editFileName) throws IOException {
        String l_fileLocation = getFilePath(p_editFileName);
        File l_fileToEdit = new File(l_fileLocation);
        if(l_fileToEdit.createNewFile() == true){
            System.out.println("File has been created");
            Map l_map=new Map();
            l_map.setD_mapName(p_editFileName);
            p_currentState.setD_map(l_map);
        }
        else{
            System.out.println("File already exists");
            p_currentState.setD_map(this.loadMap( p_currentState , p_editFileName ));
            p_currentState.getD_map().setD_mapName(p_editFileName);
        }
    }

    public void editCountry(CurrentState p_currentState, String p_operation, String p_argument) {
        System.out.println(p_operation);
        System.out.println(p_argument);
    }
}
