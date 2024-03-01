package Controller;

import Models.*;

/**
 * The type Main game engine.
 */
public class MainGameEngine {

    /**
     * The D current state.
     */
    CurrentState d_currentState = new CurrentState();
    Phase d_currentPhase = new StartupPhase(d_currentState, this);

    public Phase getD_currentPhase() {
        return d_currentPhase;
    }

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
        d_currentState.getD_modelLogger().setD_message("---------------Game Session Started---------------","Type3");
        MainGameEngine l_mainGameEngine = new MainGameEngine();
        l_mainGameEngine.getD_currentPhase().initPhase();
    }
}
