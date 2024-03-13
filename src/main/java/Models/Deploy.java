package Models;

/**
 * The type Deploy.
 */
public class Deploy implements Orders{

    /**
     * The D target country name.
     */
    String d_targetCountryName;
    /**
     * The D no of armies to move.
     */
    Integer d_noOfArmiesToMove;
    /**
     * The D intitiating player.
     */
    Player d_intitiatingPlayer;

    /**
     * Instantiates a new Deploy.
     *
     * @param p_intitiatingPlayer the p intitiating player
     * @param p_targetCountryName the p target country name
     * @param p_noOfArmiesToMove  the p no of armies to move
     */
    public Deploy(Player p_intitiatingPlayer, String p_targetCountryName, Integer p_noOfArmiesToMove) {
        this.d_intitiatingPlayer = p_intitiatingPlayer;
        this.d_targetCountryName = p_targetCountryName;
        this.d_noOfArmiesToMove = p_noOfArmiesToMove;
    }
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
            for(Country l_eachCountry : p_currentState.getD_map().getD_mapCountries()){
                if(l_eachCountry.getD_countryName().equalsIgnoreCase(this.d_targetCountryName)){
                    Integer l_updatedArmies = l_eachCountry.getD_armies() + this.d_noOfArmiesToMove;
                    l_eachCountry.setD_armies(l_updatedArmies);
                    this.setD_orderExecutionLog("Armies have been deployed successfully","default");
                }
            }
        }
        else{
            this.setD_orderExecutionLog("Given Deploy Order cannot be executed since the target country does not belong to player.","error");
            p_currentState.updateLog("Given Deploy Order cannot be executed since the target country does not belong to player.","effect");
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
        for(Country l_eachCountry : d_intitiatingPlayer.getD_currentCountries()){
            if(l_eachCountry.getD_countryName().equals(d_targetCountryName)){
                return true;
            }
        }
        return false;
    }
}
