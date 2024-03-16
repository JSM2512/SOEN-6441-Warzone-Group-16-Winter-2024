package Models;

/**
 * The type Card airlift.
 */
public class CardAirlift implements Card{
    /**
     * The D army count.
     */
    Integer d_armyCount;
    /**
     * The D source country name.
     */
    String d_sourceCountryName;
    /**
     * The D card owner.
     */
    Player d_cardOwner;
    /**
     * The D target country name.
     */
    String d_targetCountryName;

    /**
     * Instantiates a new Card airlift.
     *
     * @param p_cardOwner         the p card owner
     * @param p_sourceCountryName the p source country name
     * @param p_targetCountryName the p target country name
     * @param p_armyCount         the p army count
     */
    public CardAirlift(Player p_cardOwner, String p_sourceCountryName,String p_targetCountryName, Integer p_armyCount) {
        this.d_cardOwner = p_cardOwner;
        this.d_targetCountryName = p_targetCountryName;
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_armyCount = p_armyCount;
    }

    /**
     * Sets d order execution log.
     *
     * @param p_orderExecutionLog the p order execution log
     * @param p_messageType       the p message type
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_messageType) {
        if (p_messageType.equals("error")) {
            System.err.println(p_orderExecutionLog);
        } else {
            System.out.println(p_orderExecutionLog);
        }
    }

    /**
     * Valid order check boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
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

    /**
     * Execute.
     *
     * @param p_currentState the p current state
     */
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
            d_cardOwner.setD_oneCardPerTurn(false);
            this.setD_orderExecutionLog("Airlift card used to move " + this.d_armyCount + " armies from " + this.d_sourceCountryName + " to " + this.d_targetCountryName, "default");
            p_currentState.updateLog("Airlift card used to move " + this.d_armyCount + " armies from " + this.d_sourceCountryName + " to " + this.d_targetCountryName, "effect");
        }
        else {
            this.setD_orderExecutionLog("Invalid! Airlift card cannot be used", "error");
            p_currentState.updateLog("Invalid! Airlift card cannot be used", "effect");
        }
    }

    /**
     * Valid boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
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
