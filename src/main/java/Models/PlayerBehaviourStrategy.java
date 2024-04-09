package Models;

import java.io.IOException;
import java.io.Serializable;

/**
 * The type Player behaviour strategy.
 */
public abstract class PlayerBehaviourStrategy implements Serializable {

    /**
     * The D current state.
     */
    CurrentState d_currentState;

    /**
     * The D player.
     */
    Player d_player;

    /**
     * Instantiates a new Player behaviour strategy.
     */
    public PlayerBehaviourStrategy() {
    }

    /**
     * Gets player behaviour.
     *
     * @return the player behaviour
     */
    public abstract String getPlayerBehaviour();

    /**
     * Create order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the string
     * @throws IOException the io exception
     */
    public abstract String createOrder(Player p_player, CurrentState p_currentState) throws IOException;

    /**
     * Create card order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @param p_cardName     the p card name
     * @return the string
     */
    public abstract String createCardOrder(Player p_player, CurrentState p_currentState, String p_cardName);

    /**
     * Create advance order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the string
     */
    public abstract String createAdvanceOrder(Player p_player, CurrentState p_currentState);

    /**
     * Create deploy order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the string
     */
    public abstract String createDeployOrder(Player p_player, CurrentState p_currentState);

}
