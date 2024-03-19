package Models;


/**
 * Model Class Orders.
 */
public interface Orders {

    /**
     * Execute.
     *
     * @param p_currentState the p current state
     */
    public void execute(CurrentState p_currentState);

    public String orderExecutionLog();

    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType);

    /**
     * Valid boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean valid(CurrentState p_currentState);

}
