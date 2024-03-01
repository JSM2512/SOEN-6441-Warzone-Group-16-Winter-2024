package Models;

import Controller.MainGameEngine;
import Controller.MapController;
import Controller.PlayerController;

public abstract class Phase {

    CurrentState d_currentState;

    MapController d_mapController;

    PlayerController d_playerController;

    MainGameEngine d_mainGameEngine;

    public abstract void initPhase();

}
