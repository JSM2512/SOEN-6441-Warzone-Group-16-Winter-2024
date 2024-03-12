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
    /**
     * The D current phase.
     */
    Phase d_currentPhase = new StartupPhase(d_currentState, this);

    /**
     * Gets d current phase.
     *
     * @return the d current phase
     */
    public Phase getD_currentPhase() {
        return d_currentPhase;
    }

    /**
     * Sets d current phase.
     *
     * @param d_currentPhase the d current phase
     */
    public void setD_currentPhase(Phase d_currentPhase) {
        this.d_currentPhase = d_currentPhase;
    }

    /**
     * Set issue order phase.
     */
    public void setIssueOrderPhase(){
        this.d_currentState.getD_modelLogger().setD_message("----------------------Issue Order Phase--------------------","Phase-2");
        setD_currentPhase(new IssueOrderPhase(d_currentState, this));
        getD_currentPhase().initPhase();
    }

    /**
     * Set order execution phase.
     */
    public void setOrderExecutionPhase(){
        this.d_currentState.getD_modelLogger().setD_message("----------------------Order Execution Phase--------------------","Phase-3");
        setD_currentPhase(new OrderExecutionPhase(d_currentState, this));
        getD_currentPhase().initPhase();
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
        d_currentState.getD_modelLogger().setD_message("-------------------Game Session Started-----------------","Phase-1");
        MainGameEngine l_mainGameEngine = new MainGameEngine();
        l_mainGameEngine.getD_currentPhase().initPhase();
    }
}
