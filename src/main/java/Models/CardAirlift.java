package Models;

public class CardAirlift implements Card{
    Integer d_armyCount;
    String d_sourceCountryName;
    Player d_cardOwner;
    String d_targetCountryName;

    public CardAirlift(Player p_cardOwner, String p_sourceCountryName,String p_targetCountryName, Integer p_armyCount) {
        this.d_cardOwner = p_cardOwner;
        this.d_targetCountryName = p_targetCountryName;
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_armyCount = p_armyCount;
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
        Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
        if (l_targetCountry == null) {
            this.setD_orderExecutionLog("Invalid! Target country does not exist", "error");
            p_currentState.updateLog("Invalid! Target country does not exist", "effect");
            return false;
        }
        Country l_sourceCountry = p_currentState.getD_map().getCountryByName(d_sourceCountryName);
        if (l_sourceCountry == null) {
            this.setD_orderExecutionLog("Invalid! Source country does not exist", "error");
            p_currentState.updateLog("Invalid! Source country does not exist", "effect");
            return false;
        }
        return true;
    }

    @Override
    public void execute(CurrentState p_currentState) {
        if (valid(p_currentState)) {
            Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
            Country l_sourceCountry = p_currentState.getD_map().getCountryByName(d_sourceCountryName);
            Integer l_armiesToUpdate = l_sourceCountry.getD_armies() - this.d_armyCount;
            l_sourceCountry.setD_armies(l_armiesToUpdate);
            Integer l_newArmies = l_targetCountry.getD_armies() + this.d_armyCount;
            l_targetCountry.setD_armies(l_newArmies);
            d_cardOwner.removeCard("airlift");
            this.setD_orderExecutionLog("Airlift card used to move " + this.d_armyCount + " armies from " + this.d_sourceCountryName + " to " + this.d_targetCountryName, "default");
            p_currentState.updateLog("Airlift card used to move " + this.d_armyCount + " armies from " + this.d_sourceCountryName + " to " + this.d_targetCountryName, "effect");
        }
        else {
            this.setD_orderExecutionLog("Invalid! Airlift card cannot be used", "error");
            p_currentState.updateLog("Invalid! Airlift card cannot be used", "effect");
        }
    }

    @Override
    public boolean valid(CurrentState p_currentState) {
        Country l_targetCountry = null;
        for (Country l_eachCountry : d_cardOwner.getD_currentCountries()) {
            if (l_eachCountry.getD_countryName().equals(d_targetCountryName)) {
                l_targetCountry = l_eachCountry;
                break;
            }
        }
        if (l_targetCountry == null) {
            this.setD_orderExecutionLog("Invalid! Target country does not belong to player", "error");
            p_currentState.updateLog("Invalid! Target country does not belong to player", "effect");
            return false;

        }
        Country l_sourceCountry = null;
        for (Country l_eachCountry : d_cardOwner.getD_currentCountries()) {
            if (l_eachCountry.getD_countryName().equals(d_sourceCountryName)) {
                l_sourceCountry = l_eachCountry;
                break;
            }
        }
        if (l_sourceCountry == null) {
            this.setD_orderExecutionLog("Invalid! Source country does not belong to player", "error");
            p_currentState.updateLog("Invalid! Source country does not belong to player", "effect");
            return false;
        }
        if (l_sourceCountry.getD_armies() < d_armyCount) {
            this.setD_orderExecutionLog("Invalid! Source country does not have enough armies", "error");
            p_currentState.updateLog("Invalid! Source country does not have enough armies", "effect");
            return false;
        }
        return true;
    }
}
