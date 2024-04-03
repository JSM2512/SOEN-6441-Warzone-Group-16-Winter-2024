package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.PlayerController;
import Exceptions.CommandValidationException;
import Services.GameService;
import Utils.CommandHandler;
import Views.MapView;
import Views.TournamentView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * The type Startup phase.
 */
public class StartupPhase extends Phase{
    /**
     * The D game player controller.
     */
    PlayerController d_gamePlayerController = new PlayerController();

    /**
     * Instantiates a new Startup phase.
     *
     * @param p_currentState   the p current state
     * @param p_mainGameEngine the p main game engine
     */
    public StartupPhase(CurrentState p_currentState, MainGameEngine p_mainGameEngine) {
        super(p_currentState, p_mainGameEngine);
    }

    /**
     * Init phase.
     */
    @Override
    public void initPhase(boolean p_isTournamentMode) {
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(d_mainGameEngine.getD_currentPhase() instanceof StartupPhase){
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
           // tournament -Mtest.map -PHuman -G4 -D4
            System.out.println("11. Tournament Mode: (Usage: 'tournament -M <list_of_maps> -P <list_of_player_strategies> -G <number_of_games> -D <max_turns>')");
            System.out.println("11. Exit the game: (Usage: 'exit')");
            System.out.println("");
            System.out.print("Enter your command: ");
            try{
                String l_inputCommand = l_bufferedReader.readLine();
                handleCommand(l_inputCommand);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void saveGame(CommandHandler p_commandHandler, Player p_player) throws CommandValidationException {
        List<java.util.Map<String,String>> l_operationsList = p_commandHandler.getListOfOperations();
        if(l_operationsList == null || l_operationsList.isEmpty()){
            System.out.println(ProjectConstants.INVALID_SAVEGAME_COMMAND);
        }
        for(java.util.Map<String,String> l_map : l_operationsList){
            if(p_commandHandler.checkRequiredKey("Arguments", l_map)) {
                String l_fileName = l_map.get("Arguments");
                GameService.saveGame(this,l_fileName);
                d_mainGameEngine.setD_mainEngineLog("Game saved successfully to Filename : " + l_fileName, "effect");
            } else {
                throw new CommandValidationException(ProjectConstants.INVALID_SAVEGAME_COMMAND);
            }

        }
    }

    @Override
    protected void tournamentMode(CommandHandler p_commandHandler) throws CommandValidationException, IOException {
        if(d_currentState.getD_players() != null && d_currentState.getD_players().size() > 1){
            List<java.util.Map<String, String>> l_operationsList = p_commandHandler.getListOfOperations();
            boolean l_parsingSuccess = false;
            if(l_operationsList.isEmpty() && !d_tournament.requiredTournamentArgPresent(l_operationsList,p_commandHandler)){
                throw new CommandValidationException(ProjectConstants.INVALID_TOURNAMENT_MODE_COMMAND);
            } else{
                for(java.util.Map<String, String> l_singleOperation : l_operationsList) {
                    if (p_commandHandler.checkRequiredKey("arguments", l_singleOperation) && p_commandHandler.checkRequiredKey("operation", l_singleOperation)) {
                        l_parsingSuccess = d_tournament.parseTournamentCommand(d_currentState, l_singleOperation.get("operation"), l_singleOperation.get("arguments"), d_mainGameEngine);
                        if (!l_parsingSuccess) {
                            break;
                        }
                    } else {
                        throw new CommandValidationException(ProjectConstants.INVALID_TOURNAMENT_MODE_COMMAND);
                    }
                }
            }
            if(l_parsingSuccess){
                for(CurrentState l_eachState : d_tournament.getD_currentStateList()){
                    d_mainGameEngine.setD_mainEngineLog("Starting new game on the map " + l_eachState.getD_map().getD_mapName() +" ...........", "effect");
                    assignCountries(new CommandHandler("assigncountries"), null, true , l_eachState);

                    d_mainGameEngine.setD_mainEngineLog("Game completed on map : " + l_eachState.getD_map().getD_mapName() + "................\n ", "effect");
                }
                d_mainGameEngine.setD_mainEngineLog("******** Tournament Completed ********", "effect");
                TournamentView l_tournamentView = new TournamentView(d_tournament);
                l_tournamentView.viewTournament();
                d_tournament = new Tournament();
            }
        } else {
            d_mainGameEngine.setD_mainEngineLog("Please add 2 or more players to start the tournament", "effect");
        }
    }

    /**
     * Card handle.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void cardHandle(String p_inputCommand, Player p_player) {
        printInvalidCommandInPhase();
    }

    /**
     * Advance.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void advance(String p_inputCommand, Player p_player) {
        printInvalidCommandInPhase();
    }

    /**
     * Load map.
     *
     * @param p_commandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     */
    protected void loadMap(CommandHandler p_commandHandler) throws CommandValidationException {
        List<java.util.Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            throw new CommandValidationException("Invalid Command for load map");
        }
        for(java.util.Map<String,String> l_singleOperation : l_listOfOperations) {
            if(l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null) {
                Models.Map l_map = d_mapController.loadMap(d_currentState,l_singleOperation.get("Arguments"));
                if(l_map.validateMap()){
                    d_currentState.setD_loadCommand();
                    d_mainGameEngine.setD_mainEngineLog(l_singleOperation.get("Arguments")+" has been loaded to start the game", "effect");
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
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    @Override
    protected void editMap(CommandHandler p_commandHandler) throws CommandValidationException, IOException {
        List<java.util.Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        System.out.println(l_listOfOperations);
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new CommandValidationException("Invalid Command for edit map");
        }
        else{
            for(java.util.Map<String, String> l_singleOperation : l_listOfOperations){
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
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void editCountry(CommandHandler p_commandHandler) throws CommandValidationException {
        List<java.util.Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations.isEmpty())
        {
            throw new CommandValidationException("Invalid Command for edit Country");
        }
        else{
            for(java.util.Map<String, String> l_singleOperation: l_listOfOperations){
                if(p_commandHandler.checkRequiredKey("Arguments", l_singleOperation) && p_commandHandler.checkRequiredKey("Operation", l_singleOperation)){
                    d_mapController.editCountry(d_currentState, l_singleOperation.get("Operation"), l_singleOperation.get("Arguments"));
                }
            }
        }
    }

    /**
     * Edit continent.
     *
     * @param p_commandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void editContinent(CommandHandler p_commandHandler) throws CommandValidationException {
        List<java.util.Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new CommandValidationException("Invalid command entered for editmap.");
        }
        else{
            for(java.util.Map<String,String> l_singleOperation : l_listOfOperations){
                if(l_singleOperation.containsKey("Operation") && l_singleOperation.get("Operation")!=null && l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null){
                    d_mapController.editContinent(d_currentState, l_singleOperation.get("Operation"), l_singleOperation.get("Arguments"));
                }
            }
        }
    }

    /**
     * Edit neighbour country.
     *
     * @param p_CommandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void editNeighbourCountry(CommandHandler p_CommandHandler) throws CommandValidationException {
        List<java.util.Map<String,String>>  l_listOfOperations = p_CommandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new CommandValidationException("Invalid Command For editNeighbour");
        }else {
            for (java.util.Map<String ,String > l_singleOperation : l_listOfOperations){
                if(l_singleOperation.containsKey("Operation") && l_singleOperation.get("Operation")!=null && l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null){
                    d_mapController.editNeighbourCountry(d_currentState,l_singleOperation.get("Operation"),l_singleOperation.get("Arguments"));
                }
            }
        }
    }

    /**
     * Show map.
     *
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void showMap() throws CommandValidationException {
        MapView l_mapView = new MapView(d_currentState);
        l_mapView.showMap();
    }

    /**
     * Game player.
     *
     * @param p_commandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void gamePlayer(CommandHandler p_commandHandler, Player p_player) throws CommandValidationException, IOException {
        List<java.util.Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            System.out.println(ProjectConstants.INVALID_GAMEPLAYER_COMMAND);
        }
        else {
            for (java.util.Map<String, String> l_eachMap : l_listOfOperations) {
                if (l_eachMap.containsKey("Operation") && l_eachMap.containsKey("Arguments")) {
                    d_currentState.addOrRemoveGamePlayers(l_eachMap.get("Operation"), l_eachMap.get("Arguments"));
                }
            }
        }
    }

    /**
     * Assign countries.
     *
     * @param p_commandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     * @throws IOException                the io exception
     */
    @Override
    protected void assignCountries(CommandHandler p_commandHandler, Player p_player, Boolean p_isTournamentMode, CurrentState p_currentState) throws CommandValidationException, IOException {
        if(d_currentState != null && d_currentState.d_players != null && d_currentState.d_players.size() < 2){
            throw new CommandValidationException("Cannot assign Countries with only 1 Player");
        }
        else if(p_currentState.getD_loadCommand()){
            List<java.util.Map<String, String>> l_operationList = p_commandHandler.getListOfOperations();

            if(l_operationList.isEmpty() || p_isTournamentMode) {
                d_mainGameEngine.setD_stateOfGame(p_currentState);
                d_mainGameEngine.setD_isTournamentMode(p_isTournamentMode);
                if(d_gamePlayerController.assignCountries(p_currentState)){
                    d_gamePlayerController.assignArmies(p_currentState);
                    d_mainGameEngine.setIssueOrderPhase(p_isTournamentMode);
                }
            } else {
                throw new CommandValidationException("Invalid Command for assign countries");
            }
        }else {
            d_mainGameEngine.setD_mainEngineLog("Please load a valid map first", "effect");
        }
    }

    /**
     * Validate map.
     *
     * @param p_commandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void validateMap(CommandHandler p_commandHandler) throws CommandValidationException {
        List<java.util.Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            Models.Map l_map = d_currentState.getD_map();
            if (l_map == null) {
                System.out.println(ProjectConstants.MAP_NOT_FOUND);
            } else {
                if (l_map.validateMap()) {
                    System.out.println(ProjectConstants.VALID_MAP);
                    d_mainGameEngine.setD_mainEngineLog(ProjectConstants.VALID_MAP,"effect");
                } else {
                    System.out.println(ProjectConstants.INVALID_MAP);
                }
            }
        } else {
            System.out.println(ProjectConstants.INVALID_VALIDATE_COMMAND);
        }
    }

    /**
     * Save map.
     *
     * @param p_commandHandler the p command handler
     * @throws CommandValidationException the command validation exception
     */
    @Override
    protected void saveMap(CommandHandler p_commandHandler) throws CommandValidationException {
        List<java.util.Map<String, String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if (l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            System.out.println(ProjectConstants.INVALID_SAVEMAP_COMMAND);
        } else {
            for (java.util.Map<String, String> l_singleOperation : l_listOfOperations) {
                if (l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments") != null) {
                    boolean l_isMapSaved = d_mapController.saveMap(d_currentState, l_singleOperation.get("Arguments"));
                    if (l_isMapSaved) {
                        System.out.println("Map : " + d_currentState.getD_map().getD_mapName() + " saved successfully.");
                        d_mainGameEngine.setD_mainEngineLog("Required changes have been made in the map file", "effect");
                    } else {
                        System.out.println(ProjectConstants.SAVEMAP_FAILURE_MESSAGE);
                    }
                } else {
                    System.out.println(ProjectConstants.INVALID_SAVEMAP_COMMAND);
                }
            }
        }
    }

    /**
     * Deploy.
     *
     * @param p_inputCommand the p input command
     * @param p_player       the p player
     */
    @Override
    protected void deploy(String p_inputCommand, Player p_player) {
        printInvalidCommandInPhase();
    }
}
