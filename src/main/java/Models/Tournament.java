package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.MapController;
import Exceptions.CommandValidationException;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    MapController d_mapController = new MapController();
    List<CurrentState> d_currentStateList = new ArrayList<>();

    public MapController getD_mapController() {
        return d_mapController;
    }

    public void setD_mapController(MapController p_mapController) {
        this.d_mapController = p_mapController;
    }

    public List<CurrentState> getD_currentStateList() {
        return d_currentStateList;
    }

    public void setD_currentStateList(List<CurrentState> p_currentStates) {
        this.d_currentStateList = p_currentStates;
    }

    public boolean parseTournamentCommand(CurrentState p_currentState, String p_operation, String p_argument, MainGameEngine p_maingameEngine) throws CommandValidationException {
        if(p_operation.equalsIgnoreCase("M")){
            return parseMapArguments(p_argument, p_maingameEngine);
        }
        if(p_operation.equalsIgnoreCase("P")){
            return parseStrategyArguments(p_currentState, p_argument, p_maingameEngine);
        }
        if(p_operation.equalsIgnoreCase("G")){
            return parseNoOfGameArgument(p_argument, p_maingameEngine);
        }
        if(p_operation.equalsIgnoreCase("D")){
            return parseNoOfTurnArgument(p_argument, p_maingameEngine);
        }
        throw new CommandValidationException(ProjectConstants.INVALID_TOURNAMENT_MODE_COMMAND);
    }

    private boolean parseNoOfTurnArgument(String p_argument, MainGameEngine p_maingameEngine) {
        return true;
    }

    private boolean parseNoOfGameArgument(String p_argument, MainGameEngine p_maingameEngine) {
        return true;
    }

    private boolean parseStrategyArguments(CurrentState p_currentState, String p_argument, MainGameEngine p_maingameEngine) {
        return true;
    }

    private boolean parseMapArguments(String p_argument, MainGameEngine p_maingameEngine) {
        String[] l_listOfMapFiles = p_argument.split(" ");
        int l_mapFileSize = l_listOfMapFiles.length;

        if(l_mapFileSize >= 1 && l_mapFileSize <= 5){
            for(String l_mapFile : l_listOfMapFiles){
                CurrentState l_currentState = new CurrentState();
                Map l_map = d_mapController.loadMap(l_currentState, l_mapFile);
                l_map.setD_mapName(l_mapFile);
                if(l_map.validateMap()){
                    l_currentState.setD_loadCommand();
                    p_maingameEngine.setD_mainEngineLog("Map :" + l_mapFile + " Loaded Successfully","effect");
                    d_currentStateList.add(l_currentState);
                }
                else {
                    d_mapController.resetMap(l_currentState, l_mapFile);
                    return false;
                }
            }
        }
        else {
            p_maingameEngine.setD_mainEngineLog(ProjectConstants.INVALID_MAP_FILE_COUNT,"effect");
            return false;
        }
        return true;
    }
}
