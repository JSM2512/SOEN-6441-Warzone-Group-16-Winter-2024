package Controller;

import Constants.ProjectConstants;
import Models.Continent;
import Models.Country;
import Models.CurrentState;
import Models.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Map controller.
 */
public class MapController {

    /**
     * Load map map.
     *
     * @param p_currentState the p current state
     * @param p_fileName     the p file name
     * @return the map
     */
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
     * Load file list.
     *
     * @param p_fileName the p file name
     * @return the list
     */
    private List<String> loadFile(String p_fileName) {
        String l_fileLocation = getFilePath(p_fileName);
        List<String> l_fileLines = new ArrayList<>();
        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(l_fileLocation));
            l_fileLines = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (FileNotFoundException l_e) {
            System.out.println(ProjectConstants.FILE_NOT_FOUND);
        } catch (IOException l_e) {
            System.out.println(ProjectConstants.CORRUPTED_FILE);
        }
        return l_fileLines;
    }

    /**
     * Gets file path.
     *
     * @param p_fileName the p file name
     * @return the file path
     */
    public String getFilePath(String p_fileName) {
        String l_absolutePath=new File("").getAbsolutePath();
        l_absolutePath = l_absolutePath + File.separator + "src" + File.separator + "main" + File.separator + "maps" + File.separator + p_fileName;
        return l_absolutePath;
    }


    /**
     * Edit map.
     *
     * @param p_currentState the p current state
     * @param p_editFileName the p edit file name
     * @throws IOException the io exception
     */
    public void editMap(CurrentState p_currentState, String p_editFileName) throws IOException {
        String l_fileLocation = getFilePath(p_editFileName);
        File l_fileToEdit = new File(l_fileLocation);
        if(l_fileToEdit.createNewFile() == true){
            System.out.println(ProjectConstants.FILE_CREATED_SUCCESS);
            Map l_map=new Map();
            l_map.setD_mapName(p_editFileName);
            p_currentState.setD_map(l_map);
        }
        else{
            System.out.println(ProjectConstants.FILE_ALREADY_EXISTS);
            p_currentState.setD_map(this.loadMap( p_currentState , p_editFileName ));
            p_currentState.getD_map().setD_mapName(p_editFileName);
        }
    }

    /**
     * Edit country.
     *
     * @param p_currentState the p current state
     * @param p_operation    the p operation
     * @param p_argument     the p argument
     */
    public void editCountry(CurrentState p_currentState, String p_operation, String p_argument) {
        String l_filename = p_currentState.getD_map().getD_mapName();
        Map l_mapToBeEdited;
        if((p_currentState.getD_map().getD_mapCountries() == null && p_currentState.getD_map().getD_mapContinents() == null)){
            l_mapToBeEdited = this.loadMap(p_currentState, l_filename);
        }
        else {
            l_mapToBeEdited = p_currentState.getD_map();
        }

        if(!(l_mapToBeEdited ==null )) {
            Map l_updatedMap=addRemoveCountry(l_mapToBeEdited, p_operation, p_argument);
            p_currentState.setD_map(l_updatedMap);
            p_currentState.getD_map().setD_mapName(l_filename);
        }

    }

    /**
     * Add remove country map.
     *
     * @param p_mapToUpdate the p map to update
     * @param p_operation   the p operation
     * @param p_arguments   the p arguments
     * @return the map
     */
    private Map addRemoveCountry(Map p_mapToUpdate, String p_operation, String p_arguments) {
        if (p_operation.equals("add") && p_arguments.split(" ").length == 2){
            p_mapToUpdate.addCountry(p_arguments.split(" ")[0], p_arguments.split(" ")[1]);
        } else if (p_operation.equals("remove") && p_arguments.split(" ").length == 1){
            p_mapToUpdate.removeCountry(p_arguments);
        } else {
            System.out.println(ProjectConstants.CANNOT_ADD_REMOVE_COUNTRY);
        }
        return  p_mapToUpdate;
    }

    /**
     * Edit continent.
     *
     * @param p_currentState the p current state
     * @param p_operation    the p operation
     * @param p_arguments    the p arguments
     */
    public void editContinent(CurrentState p_currentState, String p_operation, String p_arguments) {
        String l_mapName = p_currentState.getD_map().getD_mapName();
        Map l_map;
        if(p_currentState.getD_map().getD_mapContinents() == null && p_currentState.getD_map().getD_mapCountries() == null ){
            l_map = this.loadMap(p_currentState,l_mapName);
        }
        else {
            l_map = p_currentState.getD_map();
        }
        if(l_map != null){
            Map l_updatedMap = addRemoveContinents(l_map,p_operation,p_arguments);
            p_currentState.setD_map((l_updatedMap));
            p_currentState.getD_map().setD_mapName(l_mapName);
        }
    }

    /**
     * Edit neighbour country.
     *
     * @param p_currentState the p current state
     * @param p_operation    the p operation
     * @param p_arguments    the p arguments
     */
    public void editNeighbourCountry(CurrentState p_currentState, String p_operation, String p_arguments) {
        String l_mapName = p_currentState.getD_map().getD_mapName();
        Map l_map;
        if(p_currentState.getD_map().getD_mapContinents() == null && p_currentState.getD_map().getD_mapCountries() == null ){
            l_map = this.loadMap(p_currentState,l_mapName);
        }
        else {
            l_map = p_currentState.getD_map();
        }
        if (l_map!= null){
            Map l_updateMap = addRemoveNeighbour(l_map,p_operation,p_arguments);
            p_currentState.setD_map(l_updateMap);
            p_currentState.getD_map().setD_mapName((l_mapName));
        }

    }

    /**
     * Add remove neighbour map.
     *
     * @param p_mapToUpdate the p map to update
     * @param p_operation   the p operation
     * @param p_arguments   the p arguments
     * @return the map
     */
    private Map addRemoveNeighbour(Map p_mapToUpdate, String p_operation, String p_arguments) {
        if (p_operation.equals("add") && p_arguments.split(" ").length == 2){
            p_mapToUpdate.addNeighbour(Integer.parseInt(p_arguments.split(" ")[0]), Integer.parseInt(p_arguments.split(" ")[1]));
        } else if (p_operation.equals("remove") && p_arguments.split(" ").length == 2){
            p_mapToUpdate.removeNeighbour(Integer.parseInt(p_arguments.split(" ")[0]), Integer.parseInt(p_arguments.split(" ")[1]));
        } else {
            System.out.println(ProjectConstants.CANNOT_ADD_REMOVE_NEIGHBOUR);
        }
        return  p_mapToUpdate;
    }

    /**
     * Add remove continents map.
     *
     * @param p_mapToUpdate the p map to update
     * @param p_operation   the p operation
     * @param p_arguments   the p arguments
     * @return the map
     */
    private Map addRemoveContinents(Map p_mapToUpdate, String p_operation, String p_arguments) {
        if (p_operation.equals("add") && p_arguments.split(" ").length == 2){
            p_mapToUpdate.addContinent(p_arguments.split(" ")[0], Integer.parseInt(p_arguments.split(" ")[1]));
        } else if (p_operation.equals("remove") && p_arguments.split(" ").length == 1){
            p_mapToUpdate.removeContinent(p_arguments);
        } else {
            System.out.println(ProjectConstants.CANNOT_ADD_REMOVE_CONTINENT);
        }
        return  p_mapToUpdate;
    }


    /**
     * Save map boolean.
     *
     * @param p_currentState the p current state
     * @param p_arguments    the p arguments
     * @return the boolean
     */
    public boolean saveMap(CurrentState p_currentState, String p_arguments){
        try {
            Map l_map = p_currentState.getD_map();
            if (l_map != null && l_map.validateMap()) {

                if (!l_map.getD_mapName().equals(p_arguments)) {
                    System.out.println("Name of the file must be same which you loaded in the first place i.e. : " + l_map.getD_mapName());
                }

                FileOutputStream l_writer = new FileOutputStream(getFilePath(p_arguments), false);
                l_writer.write("".getBytes());

                if (l_map.getD_mapContinents() != null || !l_map.getD_mapContinents().isEmpty()) {
                    saveContinentsOnMap(l_writer, p_currentState);
                } else {
                    System.out.println(ProjectConstants.NO_CONTINENT_IN_MAP);
                }
                if (l_map.getD_mapCountries() != null || !l_map.getD_mapCountries().isEmpty()) {
                    saveCountriesOnMap(l_writer, p_currentState);
                    saveCountryBordersOnMap(l_writer, p_currentState);
                } else {
                    System.out.println(ProjectConstants.NO_COUNTRY_IN_MAP);
                }
                l_writer.close();
            }
            else {
                System.out.println(ProjectConstants.INVALID_MAP);
                return false;
            }
            return true;
        }
        catch (IOException p_exception){
            System.out.println(p_exception.getMessage());
            return false;
        }
    }

    /**
     * Save continents on map.
     *
     * @param p_writer       the p writer
     * @param p_currentState the p current state
     * @throws IOException the io exception
     */
    private void saveContinentsOnMap(FileOutputStream p_writer, CurrentState p_currentState) throws IOException {
        p_writer.write((System.lineSeparator() + "[continents]" + System.lineSeparator()).getBytes());
        for(Continent l_eachContinent : p_currentState.getD_map().getD_mapContinents()){
            String l_content = l_eachContinent.getD_continentName() + " " + l_eachContinent.getD_continentValue();
            p_writer.write((l_content + System.lineSeparator()).getBytes());
        }
    }

    /**
     * Save countries on map.
     *
     * @param p_writer       the p writer
     * @param p_currentState the p current state
     * @throws IOException the io exception
     */
    private void saveCountriesOnMap(FileOutputStream p_writer, CurrentState p_currentState) throws IOException {
        p_writer.write((System.lineSeparator() + "[countries]" + System.lineSeparator()).getBytes());
        for(Country l_eachCountry : p_currentState.getD_map().getD_mapCountries()){
            String l_content = l_eachCountry.getD_countryID() + " " + l_eachCountry.getD_countryName() + " " + l_eachCountry.getD_continentID();
            p_writer.write((l_content + System.lineSeparator()).getBytes());
        }
    }

    /**
     * Save country borders on map.
     *
     * @param p_writer       the p writer
     * @param p_currentState the p current state
     * @throws IOException the io exception
     */
    private void saveCountryBordersOnMap(FileOutputStream p_writer, CurrentState p_currentState) throws IOException {
        p_writer.write((System.lineSeparator() + "[borders]" + System.lineSeparator()).getBytes());
        List<String> l_borders = new ArrayList<>();
        for(Country l_eachCountry : p_currentState.getD_map().getD_mapCountries()){
            if(l_eachCountry.getD_neighbouringCountriesId() != null && !l_eachCountry.getD_neighbouringCountriesId().isEmpty()){
                String l_eachCountryBorderEntry = l_eachCountry.getD_countryID().toString();
                for(Integer l_neighbour : l_eachCountry.getD_neighbouringCountriesId()){
                    l_eachCountryBorderEntry = l_eachCountryBorderEntry + " " + l_neighbour;
                }
                l_borders.add(l_eachCountryBorderEntry);
            }
        }
        if(l_borders.isEmpty()){
            System.out.println(ProjectConstants.NO_BORDER_IN_MAP);
        }
        else {
            for (String l_borderEntry : l_borders) {
                p_writer.write((l_borderEntry + System.lineSeparator()).getBytes());
            }
        }
    }
}
