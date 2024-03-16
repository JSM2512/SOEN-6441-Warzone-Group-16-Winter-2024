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
     * Valid boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean valid(CurrentState p_currentState);

}
