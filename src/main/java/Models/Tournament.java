package Models;

import Constants.ProjectConstants;
import Controller.MainGameEngine;
import Controller.MapController;
import Exceptions.CommandValidationException;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    MapController d_mapController = new MapController();
    List<CurrentState> d_currentStates = new ArrayList<>();

    public MapController getD_mapController() {
        return d_mapController;
    }

    public void setD_mapController(MapController p_mapController) {
        this.d_mapController = p_mapController;
    }

    public List<CurrentState> getD_currentStates() {
        return d_currentStates;
    }

    public void setD_currentStates(List<CurrentState> p_currentStates) {
        this.d_currentStates = p_currentStates;
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
        return true;
    }
}
