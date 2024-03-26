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
     * Instantiates a new Main game engine.
     */
    public MainGameEngine() {
    }

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
        this.setD_mainEngineLog("Issue Order Phase","phase");
        setD_currentPhase(new IssueOrderPhase(d_currentState, this));
        getD_currentPhase().initPhase();
    }

    /**
     * Set order execution phase.
     */
    public void setOrderExecutionPhase(){
        this.setD_mainEngineLog("Order Execution Phase","phase");
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
        l_mainGameEngine.getD_currentPhase().getD_currentState().updateLog("Game Session Started","start");
        l_mainGameEngine.setD_mainEngineLog("Startup Phase of the Game","phase");
        l_mainGameEngine.start(l_mainGameEngine);
    }

    /**
     * Start.
     *
     * @param p_mainGameEngine the p main game engine
     */
    private void start(MainGameEngine p_mainGameEngine){
        p_mainGameEngine.getD_currentPhase().initPhase();
    }

    /**
     * Set d main engine log.
     *
     * @param p_logForMainEngine the p log for main engine
     * @param p_logType          the p log type
     */
    public void setD_mainEngineLog(String p_logForMainEngine,String p_logType){
        d_currentPhase.getD_currentState().updateLog(p_logForMainEngine,p_logType);
        String l_consoleMessage;
        if (p_logType.equalsIgnoreCase("phase")){
            l_consoleMessage = "\n=============================== "+p_logForMainEngine+" ===============================\n";
        }else {
            l_consoleMessage = p_logForMainEngine;
        }
        System.out.println(l_consoleMessage);
    }
}
