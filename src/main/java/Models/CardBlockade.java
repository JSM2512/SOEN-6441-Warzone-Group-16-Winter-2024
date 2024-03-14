package Models;

public class CardBlockade implements Card {
    Player d_cardOwner;
    String d_targetCountryName;

    public CardBlockade(Player p_cardOwner, String p_targetCountryName) {
        this.d_cardOwner = p_cardOwner;
        this.d_targetCountryName = p_targetCountryName;
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
                System.out.println("Neutral Country: "+l_targetCountry.getD_countryName()+" has been assigned to Neutral Player");
            }
            d_cardOwner.removeCard("blockade");
            d_cardOwner.setD_oneCardPerTurn(false);
            this.setD_orderExecutionLog("Player " + d_cardOwner.getD_name() + " used blockade card to triple the armies of " + this.d_targetCountryName, "default");
            p_currentState.updateLog("Player " + d_cardOwner.getD_name() + " used blockade card to triple the armies of " + this.d_targetCountryName, "effect");
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
            this.setD_orderExecutionLog("Invalid! Blockade card cannot be used because target country does not belong to player", "error");
            p_currentState.updateLog("Invalid! Blockade card cannot be used because target country does not belong to player", "effect");
            return false;
        }
    }
}


