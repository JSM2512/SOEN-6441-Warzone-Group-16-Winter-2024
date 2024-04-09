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

    /**
     * Print order.
     */
    public void printOrder();

    /**
     * Order execution log string.
     *
     * @return the string
     */
    public String orderExecutionLog();

    /**
     * Sets d order execution log.
     *
     * @param p_orderExecutionLog the p order execution log
     * @param p_logType           the p log type
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType);

    /**
     * Valid boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean valid(CurrentState p_currentState);

}
