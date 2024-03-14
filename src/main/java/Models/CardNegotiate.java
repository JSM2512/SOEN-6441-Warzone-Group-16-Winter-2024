package Models;

public class CardNegotiate implements Card{
    Player d_cardOwner;
    String d_targetPlayer;

    public CardNegotiate(Player p_player, String p_targetPlayer) {
        this.d_cardOwner = p_player;
        this.d_targetPlayer = p_targetPlayer;
    }

    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_messageType) {
        if (p_messageType.equals("error")) {
            System.err.println(p_orderExecutionLog);
        } else {
            System.out.println(p_orderExecutionLog);
        }
    }

    @Override
    public Boolean validOrderCheck(CurrentState p_currentState) {
        Player l_targetPlayer = p_currentState.getPlayerFromName(d_targetPlayer);
        if (l_targetPlayer == null) {
            this.setD_orderExecutionLog("Invalid! No player to negotiate","error");
            p_currentState.updateLog("Invalid! No player to negotiate","effect");
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
            d_cardOwner.setD_oneCardPerTurn(false);
            this.setD_orderExecutionLog("Negotiation Successful","default");
            p_currentState.updateLog("Negotiation Successful","effect");
        }
        else {
            this.setD_orderExecutionLog("Invalid! Negotiation Unsuccessful","error");
            p_currentState.updateLog("Invalid! Negotiation Unsuccessful","effect");
        }

    }

    @Override
    public boolean valid(CurrentState p_currentState) {
        return true;
    }
}
