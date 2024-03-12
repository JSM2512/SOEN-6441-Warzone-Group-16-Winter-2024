package Models;

public class CardBlockade implements Card {
    Player d_cardOwner;
    String d_targetCountryName;

    public CardBlockade(Player p_cardOwner, String p_targetCountryName) {
        this.d_cardOwner = p_cardOwner;
        this.d_targetCountryName = p_targetCountryName;
    }

    @Override
    public Boolean validOrderCheck(CurrentState p_currentState) {
        Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
        if (l_targetCountry == null) {
            // Logger Info Required
            return false;
        }
        return true;
    }

    @Override
    public void execute(CurrentState p_currentState) {
        if (valid(p_currentState)) {
            Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountryName);
            Integer l_newArmies = l_targetCountry.getD_armies();
            if(l_newArmies == 0) {
                l_newArmies = 1;
            }
            l_targetCountry.setD_armies(l_newArmies * 3);
            d_cardOwner.getD_currentCountries().remove(l_targetCountry);
            Player l_neutralPlayer = null;
            for (Player l_eachPlayer : p_currentState.getD_players()) {
                if (l_eachPlayer.getD_name().equals("Neutral")) {
                    l_neutralPlayer = l_eachPlayer;
                    break;
                }
            }
            if(l_neutralPlayer!=null){
                l_neutralPlayer.getD_currentCountries().add(l_targetCountry);
            }
            d_cardOwner.removeCard("blockade");
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
        if (l_targetCountry != null) {
            return true;
        } else {
            // Logger Info needed
            return false;
        }
    }
}


