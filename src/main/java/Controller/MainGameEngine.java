package Controller;

import Models.*;

import java.io.Serializable;

/**
 * The type Main game engine.
 */
public class MainGameEngine implements Serializable {

    /**
     * The D current state.
     */
    private CurrentState d_stateOfGame = new CurrentState();
    /**
     * The D current phase.
     */
    Phase d_currentPhase = new StartupPhase(d_stateOfGame, this);

    /**
     * The D is tournament mode.
     */
    boolean d_isTournamentMode = false;

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
     * Gets d state of game.
     *
     * @return the d state of game
     */
    public CurrentState getD_stateOfGame() {
        return d_stateOfGame;
    }

    /**
     * Sets d state of game.
     *
     * @param p_stateOfGame the p state of game
     */
    public void setD_stateOfGame(CurrentState p_stateOfGame) {
        this.d_stateOfGame = p_stateOfGame;
    }

    /**
     * Is d is tournament mode boolean.
     *
     * @return the boolean
     */
    public boolean isD_isTournamentMode() {
        return d_isTournamentMode;
    }

    /**
     * Sets d is tournament mode.
     *
     * @param p_isTournamentMode the p is tournament mode
     */
    public void setD_isTournamentMode(boolean p_isTournamentMode) {
        this.d_isTournamentMode = p_isTournamentMode;
    }

    /**
     * Set startup phase.
     */
    public void setStartupPhase() {
        this.setD_mainEngineLog("Startup Phase of the Game", "phase");
        setD_currentPhase(new StartupPhase(d_stateOfGame, this));
        getD_currentPhase().initPhase(d_isTournamentMode);
    }

    /**
     * Set issue order phase.
     *
     * @param p_isTournamentMode the p is tournament mode
     */
    public void setIssueOrderPhase(boolean p_isTournamentMode) {
        this.setD_mainEngineLog("Issue Order Phase", "phase");
        setD_currentPhase(new IssueOrderPhase(d_stateOfGame, this));
        getD_currentPhase().initPhase(p_isTournamentMode);
    }

    /**
     * Set order execution phase.
     */
    public void setOrderExecutionPhase() {
        this.setD_mainEngineLog("Order Execution Phase", "phase");
        setD_currentPhase(new OrderExecutionPhase(d_stateOfGame, this));
        getD_currentPhase().initPhase(d_isTournamentMode);
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String... args) {
        MainGameEngine l_mainGameEngine = new MainGameEngine();
        l_mainGameEngine.getD_currentPhase().getD_currentState().updateLog("Game Session Started", "start");
        l_mainGameEngine.setD_mainEngineLog("Startup Phase of the Game", "phase");
        l_mainGameEngine.start(l_mainGameEngine);
    }

    /**
     * Start.
     *
     * @param p_mainGameEngine the p main game engine
     */
    private void start(MainGameEngine p_mainGameEngine) {
        p_mainGameEngine.getD_currentPhase().initPhase(d_isTournamentMode);
    }

    /**
     * Set d main engine log.
     *
     * @param p_logForMainEngine the p log for main engine
     * @param p_logType          the p log type
     */
    public void setD_mainEngineLog(String p_logForMainEngine, String p_logType) {
        d_currentPhase.getD_currentState().updateLog(p_logForMainEngine, p_logType);
        String l_consoleMessage;
        if (p_logType.equalsIgnoreCase("phase")) {
            l_consoleMessage = "\n=============================== " + p_logForMainEngine + " ===============================\n";
        } else {
            l_consoleMessage = p_logForMainEngine;
        }
        System.out.println(l_consoleMessage);
    }

    /**
     * Load phase.
     *
     * @param p_phase the p phase
     */
    public void loadPhase(Phase p_phase) {
        d_currentPhase = p_phase;
        d_stateOfGame = p_phase.getD_currentState();
        getD_currentPhase().initPhase(d_isTournamentMode);
    }
}