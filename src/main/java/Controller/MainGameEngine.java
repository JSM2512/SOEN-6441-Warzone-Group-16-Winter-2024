package Controller;

import Constants.ProjectConstants;
import Exceptions.CommandValidationException;
import Models.CurrentState;
import Models.Orders;
import Models.Player;
import Utils.CommandHandler;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * The type Main game engine.
 */
public class MainGameEngine {

    /**
     * The D map controller.
     */
    MapController d_mapController = new MapController();
    /**
     * The D current state.
     */
    CurrentState d_currentState = new CurrentState();
    /**
     * The D game player controller.
     */
    PlayerController d_gamePlayerController = new PlayerController();

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String... args){
        MainGameEngine l_mainGameEngine = new MainGameEngine();
        l_mainGameEngine.start();
    }

    /**
     * Start.
     */
    private void start() {
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("================================== MAIN MENU ===================================");
            System.out.println("1. Initiate the map: (Usage: 'loadmap <your_filename(.map)>')");
            System.out.println("2. Edit the Map: (Usage: 'editmap <filename>(.map)')");
            System.out.println("3. Validate the Map: (Usage: 'validatemap')");
            System.out.println("4. Show the Map: (Usage: 'showmap')");
            System.out.println("5. Save the Map: (Usage: 'savemap <file_name_same_used_in_loadmap')");
            System.out.println("6. Edit the Continent: (Usage: 'editcontinent -add/-remove <continent_name>')");
            System.out.println("7. Edit the Country: (Usage: 'editcountry -add/-remove <country_name>')");
            System.out.println("8. Edit the Neighbour: (Usage: 'editneighbour -add/-remove <country_id_1> <country_id_2>')");
            System.out.println("9. Add a player: (Usage: 'gameplayer -add/-remove <player_name>')");
            System.out.println("10. Assign countries and allocate armies to players: (Usage: 'assigncountries')");
            System.out.println("11. Exit the game: (Usage: 'exit')");
            System.out.println("");
            System.out.print("Enter your command: ");
            try{
                String l_inputCommand = l_bufferedReader.readLine();
                commandHandler(l_inputCommand);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Command handler.
     *
     * @param p_inputCommand the p input command
     * @throws Exception the exception
     */
    private void commandHandler(String p_inputCommand) throws Exception {
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        String l_mainCommand = l_commandHandler.getMainCommand();
        boolean l_mapAvailable = false;

        if(d_currentState.getD_map() != null){
            l_mapAvailable = true;
        }
        if(l_mainCommand.equals("loadmap")){
            loadMap(l_commandHandler);
        }
        else if(l_mainCommand.equals(("editmap"))){
            editMap(l_commandHandler);
        }
        else if(l_mainCommand.equals("editcountry")) {
            if (!l_mapAvailable) {
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_EDIT_COUNTRY);
            } else {
                editCountry(l_commandHandler);
            }
        }
        else if(l_mainCommand.equals("editcontinent")){
            if(!l_mapAvailable){
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
            }
            else{
                editContinent(l_commandHandler);
            }
        }
        else if(l_mainCommand.equals("editneighbour")){
            if(!l_mapAvailable){
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
            }
            else {
                editNeighbourCountry(l_commandHandler);
            }
        } 
        else if (l_mainCommand.equals("showmap")) {
            if(!l_mapAvailable){
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
            }
            else {
                MapView l_mapView = new MapView(d_currentState);
                l_mapView.showMap();
            }
        } else if (l_mainCommand.equals("gameplayer")) {
            if (!l_mapAvailable) {
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_PLAYERS);
            }
            else {
                gamePlayer(l_commandHandler);
            }
        }else if (l_mainCommand.equals("assigncountries")) {
            if (!l_mapAvailable) {
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_ASSIGN_COUNTRIES);
            }
            else {
                assignCountries(l_commandHandler);
            }
        }
        else if (l_mainCommand.equals("validatemap")) {
            if (!l_mapAvailable) {
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
            } else {
                validateMap(l_commandHandler);
            }
        }
        else if (l_mainCommand.equals("savemap")) {
            if (!l_mapAvailable) {
                System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
            } else {
                saveMap(l_commandHandler);
            }
        }
        else if("exit".equals(p_inputCommand)){

            System.out.println("Closing Game....");
            System.exit(0);
        }
    }

    /**
     * Assign countries.
     *
     * @param p_commandHandler the p command handler
     * @throws IOException the io exception
     */
    private void assignCountries(CommandHandler p_commandHandler) throws IOException {
        List<Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            d_gamePlayerController.assignCountries(d_currentState);
            d_gamePlayerController.assignArmies(d_currentState);
            startGame();
        }
    }

    /**
     * Start game.
     *
     * @throws IOException the io exception
     */
    private void startGame() throws IOException {
        if(d_currentState.getD_players() == null || d_currentState.getD_players().isEmpty()){
            System.out.println(ProjectConstants.NO_PLAYERS);
            return;
        }

        System.out.println(ProjectConstants.DEPLOY_ARMIES_MESSAGE);

            while(d_gamePlayerController.isUnallocatedArmiesExist(d_currentState)){
                for(Player l_eachPlayer : d_currentState.getD_players()){
                    if(l_eachPlayer.getD_unallocatedArmies() > 0){
                        l_eachPlayer.issueOrder();
                    }
                }
            }
            while(d_gamePlayerController.isUnexecutedOrdersExist(d_currentState)){
                for(Player l_eachPlayer : d_currentState.getD_players()){
                    Orders l_orderToExecute = l_eachPlayer.nextOrder();
                    if(l_orderToExecute != null){
                        l_orderToExecute.execute(l_eachPlayer);
                    }
                }
            }
        }
//    }

    /**
     * Game player.
     *
     * @param p_commandHandler the p command handler
     */
    private void gamePlayer(CommandHandler p_commandHandler) {
        List<Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            System.out.println(ProjectConstants.INVALID_GAMEPLAYER_COMMAND);
        }
        else {
            for (Map<String, String> l_eachMap : l_listOfOperations) {
                if (l_eachMap.containsKey("Operation") && l_eachMap.containsKey("Arguments")) {
                    d_currentState.addOrRemoveGamePlayers(l_eachMap.get("Operation"), l_eachMap.get("Arguments"));
                }
            }
        }
    }

    /**
     * Save map.
     *
     * @param p_commandHandler the p command handler
     * @throws IOException the io exception
     */
    private void saveMap(CommandHandler p_commandHandler) throws IOException {
        List<Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            System.out.println(ProjectConstants.INVALID_SAVEMAP_COMMAND);
        } else {
            for(Map<String,String> l_singleOperation : l_listOfOperations){
                if(l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null){
                    boolean l_isMapSaved = d_mapController.saveMap(d_currentState, l_singleOperation.get("Arguments"));
                    if(l_isMapSaved){
                        System.out.println("Map : "+d_currentState.getD_map().getD_mapName()+" saved successfully.");
                    }
                    else{
                        System.out.println(ProjectConstants.SAVEMAP_FAILURE_MESSAGE);
                    }
                }
                else {
                    System.out.println(ProjectConstants.INVALID_SAVEMAP_COMMAND);
                }
            }
        }
    }

    /**
     * Validate map.
     *
     * @param p_commandHandler the p command handler
     */
    private void validateMap(CommandHandler p_commandHandler) {
        List<Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            Models.Map l_map = d_currentState.getD_map();
            if (l_map == null) {
                System.out.println(ProjectConstants.MAP_NOT_FOUND);
            } else {
                if (l_map.validateMap()) {
                    System.out.println(ProjectConstants.VALID_MAP);
                } else {
                    System.out.println(ProjectConstants.INVALID_MAP);
                }
            }
        } else {
            System.out.println(ProjectConstants.INVALID_VALIDATE_COMMAND);
        }
    }

    /**
     * Edit neighbour country.
     *
     * @param p_CommandHandler the p command handler
     * @throws Exception the exception
     */
    private void editNeighbourCountry(CommandHandler p_CommandHandler) throws Exception {
        List<Map<String,String>>  l_listOfOperations = p_CommandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new CommandValidationException("Invalid Command For editNeighbour");
        }else {
            for (Map<String ,String > l_singleOperation : l_listOfOperations){
                if(l_singleOperation.containsKey("Operation") && l_singleOperation.get("Operation")!=null && l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null){
                    d_mapController.editNeighbourCountry(d_currentState,l_singleOperation.get("Operation"),l_singleOperation.get("Arguments"));
                }
            }
        }
    }

    /**
     * Edit continent.
     *
     * @param p_commandHandler the p command handler
     * @throws Exception the exception
     */
    private void editContinent(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new CommandValidationException("Invalid command entered for editmap.");
        }
        else{
            for(Map<String,String> l_singleOperation : l_listOfOperations){
                if(l_singleOperation.containsKey("Operation") && l_singleOperation.get("Operation")!=null && l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null){
                    d_mapController.editContinent(d_currentState, l_singleOperation.get("Operation"), l_singleOperation.get("Arguments"));
                }
            }
        }
    }

    /**
     * Load map.
     *
     * @param p_commandHandler the p command handler
     * @throws Exception the exception
     */
    private void loadMap(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        System.out.println(l_listOfOperations);
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            throw new CommandValidationException("Invalid Command for load map");
        }
        for(Map<String,String> l_singleOperation : l_listOfOperations) {
            if(l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null) {
                Models.Map l_map = d_mapController.loadMap(d_currentState,l_singleOperation.get("Arguments"));
                if(l_map.validateMap()){
                    System.out.println(ProjectConstants.VALID_MAP);
                }
                else{
                    System.out.println(ProjectConstants.INVALID_MAP);
                }
            }
        }
    }

    /**
     * Edit map.
     *
     * @param p_commandHandler the p command handler
     * @throws Exception the exception
     */
    private void editMap(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        System.out.println(l_listOfOperations);
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new CommandValidationException("Invalid Command for edit map");
        }
        else{
            for(Map<String, String> l_singleOperation : l_listOfOperations){
                if(p_commandHandler.checkRequiredKey("Arguments", l_singleOperation)){
                    d_mapController.editMap(d_currentState, l_singleOperation.get("Arguments"));
                }
                else{
                    throw new CommandValidationException("Invalid Command for edit map operation");
                }
            }
        }
    }

    /**
     * Edit country.
     *
     * @param p_commandHandler the p command handler
     * @throws Exception the exception
     */
    private void editCountry(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations.isEmpty())
        {
            throw new CommandValidationException("Invalid Command for edit Country");
        }
        else{
            for(Map<String, String> l_singleOperation: l_listOfOperations){
                if(p_commandHandler.checkRequiredKey("Arguments", l_singleOperation) && p_commandHandler.checkRequiredKey("Operation", l_singleOperation)){
                    d_mapController.editCountry(d_currentState, l_singleOperation.get("Operation"), l_singleOperation.get("Arguments"));
                }
            }
        }
    }
}
