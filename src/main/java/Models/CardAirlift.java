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

    @Override
    public Boolean validOrderCheck(CurrentState p_currentState) {
        Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
        if (l_targetCountry == null) {
            // Logger Info Required
            return false;
        }
        Country l_sourceCountry = p_currentState.getD_map().getCountryByName(d_sourceCountryName);
        if (l_sourceCountry == null) {
            // Logger Info Required
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
            // Logger Info needed
            // Current state log update.
        }
        else {
            // Logger Info needed
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
            return false;
            // Logger Info needed
        }
        Country l_sourceCountry = null;
        for (Country l_eachCountry : d_cardOwner.getD_currentCountries()) {
            if (l_eachCountry.getD_countryName().equals(d_sourceCountryName)) {
                l_sourceCountry = l_eachCountry;
                break;
            }
        }
        if (l_sourceCountry == null) {
            return false;
            // Logger Info needed
        }
        if (l_sourceCountry.getD_armies() < d_armyCount) {
            return false;
            // Logger Info needed
        }
        return true;
    }
}
