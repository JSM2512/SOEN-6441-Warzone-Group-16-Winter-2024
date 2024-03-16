package Models;

/**
 * The interface Card.
 */
public interface Card extends Orders{
    /**
     * Valid order check boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    Boolean validOrderCheck(CurrentState p_currentState);
}
