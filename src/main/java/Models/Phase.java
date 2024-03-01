package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.MapController;
import Exceptions.CommandValidationException;
import Utils.CommandHandler;

import java.io.IOException;

public abstract class Phase {

    CurrentState d_currentState;

    MapController d_mapController = new MapController();

    MainGameEngine d_mainGameEngine;

    public Phase(CurrentState p_currentState, MainGameEngine p_mainGameEngine) {
        this.d_currentState = p_currentState;
        this.d_mainGameEngine = p_mainGameEngine;
    }

    public abstract void initPhase();

    /**
     * Command handler.
     *
     * @param p_inputCommand the p input command
     * @throws Exception the exception
     */
    public void commandHandler(String p_inputCommand) throws Exception {
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        String l_mainCommand = l_commandHandler.getMainCommand();
        boolean l_mapAvailable = false;
        if(d_currentState.getD_map() != null){
            l_mapAvailable = true;
        }
        switch (l_mainCommand){
            case "loadmap":
                loadMap(l_commandHandler);
                d_currentState.getD_modelLogger().setD_message("Map Loaded Success", "type-1");
                break;
            case "editmap":
                editMap(l_commandHandler);
                d_currentState.getD_modelLogger().setD_message("Editmap command executed successfully", "type-1");
                break;
            case "editcountry":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_EDIT_COUNTRY);
                    d_currentState.getD_modelLogger().setD_message("Entered command: editCountry. Map is not available. ","type-1");
                } else {
                    editCountry(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: editcountry. Country edited successfully","");
                }
                break;
            case "editcontinent":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                    d_currentState.getD_modelLogger().setD_message("Entered command: editcontinent. Map is not available. ","type-1");
                }
                else{
                    editContinent(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: editcontinent. Continent edited successfully. ","type-1");
                }
                break;
            case "editneighbour":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                    d_currentState.getD_modelLogger().setD_message("Entered command: editneighbour. Map is not available. ","type-1");
                }
                else {
                    editNeighbourCountry(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: editneighbour. Neighbour edited successfully. ","type-1");
                }
                break;
            case "showmap":
                if(!l_mapAvailable){
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                    d_currentState.getD_modelLogger().setD_message("Entered command: showmap. Map is not available. ","type-1");
                }
                else {
                    showMap();
                    d_currentState.getD_modelLogger().setD_message("Entered command: showmap. showmap executed successfully. ","type-1");
                }
                break;
            case "gameplayer":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_PLAYERS);
                    d_currentState.getD_modelLogger().setD_message("Entered command: gameplayer. Map is not available. ","type-1");
                }
                else {
                    gamePlayer(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: gameplayer. gameplayer executed successfully. ","type-1");
                }
                break;
            case "assigncountries":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE_ASSIGN_COUNTRIES);
                    d_currentState.getD_modelLogger().setD_message("Entered command: assigncountries. Map is not available. ","type-1");
                }
                else {
                    assignCountries(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: assigncountries. assigncountries executed successfully. ","type-1");
                }
                break;
            case "validatemap":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                    d_currentState.getD_modelLogger().setD_message("Entered command: validatemap. Map is not available. ","type-1");
                } else {
                    validateMap(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: validatemap. validatemap executed successfully. ","type-1");
                }
                break;
            case "savemap":
                if (!l_mapAvailable) {
                    System.out.println(ProjectConstants.MAP_NOT_AVAILABLE);
                    d_currentState.getD_modelLogger().setD_message("Entered command: savemap. Map is not available. ","type-1");
                } else {
                    saveMap(l_commandHandler);
                    d_currentState.getD_modelLogger().setD_message("Entered command: savemap. savemap executed successfully. ","type-1");
                }
                break;
            case "exit":
                d_currentState.getD_modelLogger().setD_message("Entered command: exit. Exited successfully.","Type3");
                d_currentState.getD_modelLogger().setD_message("---------------Game Session Closed---------------","Type3");
                System.out.println("Closing Game....");
                System.exit(0);
                break;
        }
    }

    protected abstract void loadMap(CommandHandler lCommandHandler) throws CommandValidationException;

    protected abstract void editMap(CommandHandler lCommandHandler) throws CommandValidationException, IOException;

    protected abstract void editCountry(CommandHandler lCommandHandler) throws CommandValidationException;

    protected abstract void editContinent(CommandHandler lCommandHandler) throws CommandValidationException;

    protected abstract void editNeighbourCountry(CommandHandler lCommandHandler) throws CommandValidationException;

    protected abstract void showMap() throws CommandValidationException;

    protected abstract void gamePlayer(CommandHandler lCommandHandler) throws CommandValidationException;

    protected abstract void assignCountries(CommandHandler lCommandHandler) throws CommandValidationException, IOException;

    protected abstract void validateMap(CommandHandler lCommandHandler) throws CommandValidationException;

    protected abstract void saveMap(CommandHandler lCommandHandler) throws CommandValidationException;

}
