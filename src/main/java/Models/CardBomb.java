package Models;

/**
 * The type Card bomb.
 */
public class CardBomb implements Card{

    /**
     * The D card owner.
     */
    Player d_cardOwner;
    /**
     * The D target country name.
     */
    String d_targetCountryName;

    /**
     * Instantiates a new Card bomb.
     *
     * @param p_cardOwner         the p card owner
     * @param p_targetCountryName the p target country name
     */
    public CardBomb(Player p_cardOwner, String p_targetCountryName) {
        this.d_cardOwner = p_cardOwner;
        this.d_targetCountryName = p_targetCountryName;
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
     * Execute.
     *
     * @param p_currentState the p current state
     */
    @Override
    public void execute(CurrentState p_currentState) {
        if(valid(p_currentState)){
            Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
            Integer l_armyCountOnTargetCountry = 0;
            if(l_targetCountry.getD_armies() == 0){
                l_armyCountOnTargetCountry = 1;
            }
            else{
                l_armyCountOnTargetCountry = l_targetCountry.getD_armies();
            }
            Integer l_newArmies = (int) Math.floor((double) l_armyCountOnTargetCountry / 2);
            l_targetCountry.setD_armies(l_newArmies);
            d_cardOwner.removeCard("bomb");
            d_cardOwner.setD_oneCardPerTurn(false);
            this.setD_orderExecutionLog("Bomb card used to reduce the armies of " + this.d_targetCountryName + " to " + l_newArmies, "default");
            p_currentState.updateLog("Bomb card used to reduce the armies of " + this.d_targetCountryName + " to " + l_newArmies, "effect");
        }
        else{
            this.setD_orderExecutionLog("Invalid! Bomb card cannot be used", "error");
            p_currentState.updateLog("Invalid! Bomb card cannot be used", "effect");
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
        if(l_targetCountry == null){
            this.setD_orderExecutionLog("Invalid! Target country does not exist", "error");
            p_currentState.updateLog("Invalid! Target country does not exist", "effect");
            return false;
        }
        return true;
    }


    /**
     * Valid boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    @Override
    public boolean valid(CurrentState p_currentState) {
        Country l_country = null;
        for(Country l_eachCountry : d_cardOwner.getD_currentCountries()){
            if(l_eachCountry.getD_countryName().equals(d_targetCountryName)){
                l_country = l_eachCountry;
            }
        }
        boolean l_isTargetCountryNeighbour = false;
        Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
        for (Country l_eachCountry : d_cardOwner.getD_currentCountries()) {
            for (Integer l_eachNeighbour : l_eachCountry.getD_neighbouringCountriesId()) {
                if (l_eachNeighbour.equals(l_targetCountry.getD_countryID())) {
                    l_isTargetCountryNeighbour = true;
                    break;
                }
            }
        }
        if(!d_cardOwner.negotiationValidation(this.d_targetCountryName)){
            this.setD_orderExecutionLog("Invalid! Negotiation is in place with the target country", "error");
            p_currentState.updateLog("Invalid! Negotiation is in place with the target country", "effect");
            return false;
        }
        if(l_country != null || !l_isTargetCountryNeighbour){
            this.setD_orderExecutionLog("Invalid! Bomb card cannot be used on own country or non-neighbouring Country", "error");
            p_currentState.updateLog("Invalid! Bomb card cannot be used on own country or non-neighbouring Country", "effect");
            return false;
        }
        return true;
    }
}
