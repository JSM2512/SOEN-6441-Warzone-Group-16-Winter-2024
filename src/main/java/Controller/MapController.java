package Controller;

import Constants.ProjectConstants;
import Models.Continent;
import Models.Country;
import Models.CurrentState;
import Models.Map;
import Services.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Map controller.
 */
public class MapController implements Serializable {
    /**
     * The D current state.
     */
    CurrentState d_currentState = new CurrentState();

    /**
     * Instantiates a new Map controller.
     */
    public MapController() {
    }

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
            if(l_fileLines.contains("[Territories]")){
                MapReaderAdapter l_mapReaderAdapter = new MapReaderAdapter(new ConquestMapFileReader());
                l_mapReaderAdapter.parseMapFile(p_currentState, l_map, l_fileLines, p_fileName);
            }
            else if(l_fileLines.contains("[continents]")){
              new MapFileReader().parseMapFile(p_currentState, l_map, l_fileLines, p_fileName);
            }
        }
        return l_map;
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
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.FILE_NOT_FOUND,"error");
        } catch (IOException l_e) {
            System.out.println(ProjectConstants.CORRUPTED_FILE);
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.CORRUPTED_FILE,"error");
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
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.FILE_CREATED_SUCCESS,"effect");
            Map l_map=new Map();
            l_map.setD_mapName(p_editFileName);
            p_currentState.setD_map(l_map);
        }
        else{
            System.out.println(ProjectConstants.FILE_ALREADY_EXISTS);
            d_currentState.getD_modelLogger().setD_message("File Already exists","effect");
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
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.CANNOT_ADD_REMOVE_COUNTRY,"effect");
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
            Map l_updatedMap = addRemoveContinents(p_currentState,l_map,p_operation,p_arguments);
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
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.CANNOT_ADD_REMOVE_NEIGHBOUR,"effect");
        }
        return  p_mapToUpdate;
    }

    /**
     * Add remove continents map.
     *
     * @param p_currentState the p current state
     * @param p_mapToUpdate  the p map to update
     * @param p_operation    the p operation
     * @param p_arguments    the p arguments
     * @return the map
     */
    private Map addRemoveContinents(CurrentState p_currentState, Map p_mapToUpdate, String p_operation, String p_arguments) {
        if (p_operation.equals("add") && p_arguments.split(" ").length == 2){
            p_mapToUpdate.addContinent(p_arguments.split(" ")[0], Integer.parseInt(p_arguments.split(" ")[1]));
        } else if (p_operation.equals("remove") && p_arguments.split(" ").length == 1){
            p_mapToUpdate.removeContinent(p_arguments);
        } else {
            System.out.println(ProjectConstants.CANNOT_ADD_REMOVE_CONTINENT);
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.CANNOT_ADD_REMOVE_CONTINENT,"effect");
        }
        return  p_mapToUpdate;
    }


    /**
     * Save map boolean.
     *
     * @param p_currentState the p current state
     * @param p_fileName     the p file name
     * @return the boolean
     */
    public boolean saveMap(CurrentState p_currentState, String p_fileName){
        try {
            String l_mapFormat = null;
            if(!p_fileName.equalsIgnoreCase(p_currentState.getD_map().getD_mapName())){
                p_currentState.setLogMessage("Kindly provide same filename to save the map which you have given for edit.", "error");
                return false;
            }
            else{
                if(p_currentState.getD_map() != null){
                    Map l_currentMap = p_currentState.getD_map();
                    if(l_currentMap.validateMap()){
                        l_mapFormat = this.getFormatToSave();
                        Files.deleteIfExists(Paths.get(getFilePath(p_fileName)));
                        FileWriter l_writer = new FileWriter(getFilePath(p_fileName));

                        parseMapToFile(p_currentState, l_writer, l_mapFormat);
                        p_currentState.updateLog("Map saved successfully.","effect");
                        l_writer.close();
                    }
                }
                else{
                    p_currentState.updateLog("Map is not valid. Kindly provide valid map.","error");
                    return false;
                }
            }
            return true;
        }
        catch (Exception l_e){
            p_currentState.updateLog("Error in saving map","error");
            return false;
        }
    }

    /**
     * Parse map to file.
     *
     * @param p_currentState the p current state
     * @param p_writer       the p writer
     * @param p_mapFormat    the p map format
     * @throws IOException the io exception
     */
    private void parseMapToFile(CurrentState p_currentState, FileWriter p_writer, String p_mapFormat) throws IOException {
        if(p_mapFormat.equalsIgnoreCase("ConquestMap")) {
            MapWriterAdapter l_mapWriterAdapter = new MapWriterAdapter(new ConquestMapFileWriter());
            l_mapWriterAdapter.parseMapToFile(p_currentState, p_writer, p_mapFormat);
        }
        else{
            new MapFileWriter().parseMapToFile(p_currentState, p_writer, p_mapFormat);
        }
    }

    /**
     * Gets format to save.
     *
     * @return the format to save
     * @throws IOException the io exception
     */
    private String getFormatToSave() throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Kindly press 1 to save the map as 'Conquest Map Format' or else press 2 to save the map as 'Domination Map Format'");
        String l_nextOrder = l_reader.readLine();
        if(l_nextOrder.equalsIgnoreCase("1")){
            return "ConquestMap";
        } else if (l_nextOrder.equalsIgnoreCase("2")) {
            return "NormalMap";
        }
        else {
            System.err.println("Invalid input passed.");
            return this.getFormatToSave();
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
            d_currentState.getD_modelLogger().setD_message("No borders in the map.","effect");
        }
        else {
            for (String l_borderEntry : l_borders) {
                p_writer.write((l_borderEntry + System.lineSeparator()).getBytes());
            }
        }
    }

    /**
     * Reset map.
     *
     * @param p_currentState the p current state
     * @param p_mapFile      the p map file
     */
    public void resetMap(CurrentState p_currentState, String p_mapFile) {
        System.err.println("Map is not valid. Kindly provide valid map.");
        p_currentState.updateLog(p_mapFile + " map is not valid. Kindly provide valid map.","effect");
        p_currentState.setD_map(new Map());
    }
}
