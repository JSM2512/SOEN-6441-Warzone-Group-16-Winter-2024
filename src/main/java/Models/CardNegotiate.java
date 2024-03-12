package Models;

public class CardNegotiate implements Card{
    Player d_cardOwner;
    String d_targetPlayer;

    public CardNegotiate(Player p_player, String p_targetPlayer) {
        this.d_cardOwner = p_player;
        this.d_targetPlayer = p_targetPlayer;
    }

    @Override
    public Boolean validOrderCheck(CurrentState p_currentState) {
        Player l_targetPlayer = p_currentState.getPlayerFromName(d_targetPlayer);
        if (l_targetPlayer == null) {
            // Logger Info Required
            return false;
        }
        return true;
    }

    @Override
    public void execute(CurrentState p_currentState) {
        Player l_targetPlayer = p_currentState.getPlayerFromName(d_targetPlayer);
        if (valid(p_currentState)) {
            l_targetPlayer.addNegotiatePlayer(d_cardOwner);
            d_cardOwner.addNegotiatePlayer(l_targetPlayer);
            d_cardOwner.removeCard("negotiate");
            // Logger Info needed
            // Current state log update.
        }
        else {
            // Logger Info needed
        }

    }

    @Override
    public boolean valid(CurrentState p_currentState) {
        return true;
    }
}
