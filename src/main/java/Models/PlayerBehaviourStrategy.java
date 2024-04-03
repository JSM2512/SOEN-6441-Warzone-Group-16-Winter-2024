package Models;

import java.io.IOException;
import java.io.Serializable;

public abstract class PlayerBehaviourStrategy implements Serializable {

    CurrentState d_currentState;

    Player d_player;

    public abstract String getPlayerBehaviour();

    public abstract String createOrder(Player p_player, CurrentState p_currentState) throws IOException;

    public abstract String createCardOrder(Player p_player, CurrentState p_currentState, String p_cardName);

    public abstract String createAdvanceOrder(Player p_player, CurrentState p_currentState);

    public abstract String createDeployOrder(Player p_player, CurrentState p_currentState);

}
