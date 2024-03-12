package Models;

public class CardBomb implements Card{

    Player d_cardOwner;
    String d_targetCountryName;

    public CardBomb(Player p_cardOwner, String p_targetCountryName) {
        this.d_cardOwner = p_cardOwner;
        this.d_targetCountryName = p_targetCountryName;
    }

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
            // Logger Info needed
            // Current state log update.
        }
        else{
            // Logger Info needed
        }
    }
    @Override
    public Boolean validOrderCheck(CurrentState p_currentState) {
        Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
        if(l_targetCountry == null){
            // Logger Info Required
            return false;
        }
        return true;
    }



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
        if(l_country != null || !l_isTargetCountryNeighbour){
            // Logger Info needed
            return false;
        }
        return true;
    }
}
