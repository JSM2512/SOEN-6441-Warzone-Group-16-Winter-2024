package Models;

import Controller.PlayerController;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Advance.
 */
public class Advance implements Orders{

    /**
     * The D source country.
     */
    String d_sourceCountry;
    /**
     * The D target country.
     */
    String d_targetCountry;
    /**
     * The D no of armies to place.
     */
    Integer d_noOfArmiesToPlace;
    /**
     * The D intitiating player.
     */
    Player d_intitiatingPlayer;

    /**
     * Instantiates a new Advance.
     *
     * @param p_sourceCountry     the p source country
     * @param p_targetCountry     the p target country
     * @param p_noOfArmiesToPlace the p no of armies to place
     * @param p_intitiatingPlayer the p intitiating player
     */
    public Advance(String p_sourceCountry, String p_targetCountry, Integer p_noOfArmiesToPlace, Player p_intitiatingPlayer) {
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_noOfArmiesToPlace = p_noOfArmiesToPlace;
        this.d_intitiatingPlayer = p_intitiatingPlayer;
    }


    /**
     * Execute.
     *
     * @param p_currentState the p current state
     */
    @Override
    public void execute(CurrentState p_currentState) {
        if(valid(p_currentState)){
            Player l_playerOfTargetCountry = getplayerOfTargetCounrty(p_currentState);
            Country l_sourceCountry = p_currentState.getD_map().getCountryByName(d_sourceCountry);
            Country l_targetCountry = p_currentState.getD_map().getCountryByName(d_targetCountry);
            Integer l_armiesToUpadte = l_sourceCountry.getD_armies() - this.d_noOfArmiesToPlace;
            l_sourceCountry.setD_armies(l_armiesToUpadte);
            if(l_playerOfTargetCountry.getD_name().equalsIgnoreCase(this.d_intitiatingPlayer.getD_name())){
                deployArmiesToTarget(l_targetCountry);
            }
            else if(l_targetCountry.getD_armies() == 0){
                conquerTargetCountry(p_currentState, l_playerOfTargetCountry, l_targetCountry);
            }
        }
    }

    /**
     * Conquer target country.
     *
     * @param p_currentState          the p current state
     * @param p_playerOfTargetCountry the p player of target country
     * @param p_targetCountry         the p target country
     */
    private void conquerTargetCountry(CurrentState p_currentState, Player p_playerOfTargetCountry, Country p_targetCountry) {
        p_targetCountry.setD_armies(d_noOfArmiesToPlace);
        p_playerOfTargetCountry.getD_currentCountries().remove(p_targetCountry);
        this.d_intitiatingPlayer.d_currentCountries.add(p_targetCountry);
        System.out.println("Player : "+d_intitiatingPlayer.getD_name()+" is assigned with Country : "+p_targetCountry.getD_countryName()+" and Armies : "+p_targetCountry.getD_armies());
        this.updateContinents(this.d_intitiatingPlayer, p_playerOfTargetCountry, p_currentState);
    }

    /**
     * Update continents.
     *
     * @param p_intitiatingPlayer     the p intitiating player
     * @param p_playerOfTargetCountry the p player of target country
     * @param p_currentState          the p current state
     */
    private void updateContinents(Player p_intitiatingPlayer, Player p_playerOfTargetCountry, CurrentState p_currentState) {
        System.out.println("Updating continents of players involved in battle....");
        List<Player> l_playerList = new ArrayList<>();
        p_intitiatingPlayer.setD_currentContinents(new ArrayList<>());
        p_playerOfTargetCountry.setD_currentContinents(new ArrayList<>());

        l_playerList.add(p_intitiatingPlayer);
        l_playerList.add(p_playerOfTargetCountry);

        PlayerController l_playerController = new PlayerController();
        l_playerController.assignContinentToPlayers(l_playerList, p_currentState.getD_map().getD_mapContinents());
    }

    /**
     * Deploy armies to target.
     *
     * @param p_targetCountry the p target country
     */
    private void deployArmiesToTarget(Country p_targetCountry) {
        Integer l_updatedArmyCount = p_targetCountry.getD_armies() + this.d_noOfArmiesToPlace;
        p_targetCountry.setD_armies(l_updatedArmyCount);
    }

    /**
     * Gets of target counrty.
     *
     * @param p_currentState the p current state
     * @return the of target counrty
     */
    private Player getplayerOfTargetCounrty(CurrentState p_currentState) {
        Player l_player = null;
        for(Player l_eachPlayer : p_currentState.getD_players()){
            for(Country l_eachCountry : l_eachPlayer.getD_currentCountries()){
                if(l_eachCountry.getD_countryName().equalsIgnoreCase(d_targetCountry)){
                    l_player = l_eachPlayer;
                }
            }
        }
        return l_player;
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
        for(Country l_eachCountry : d_intitiatingPlayer.getD_currentCountries()){
            if(l_eachCountry.getD_countryName().equalsIgnoreCase(d_sourceCountry))
                l_country = l_eachCountry;
        }
        if(l_country == null){
            System.out.println("Cannot execute the order because the source country does not belong to the player.");
            return false;
        }
        if(this.d_noOfArmiesToPlace > l_country.getD_armies()){
            System.out.println("Cannot execute the order because the no of armies you inserted is more than available at the source country.");
            return false;
        }
        if(this.d_noOfArmiesToPlace == l_country.getD_armies()){
            System.out.println("Cannot execute the order because the no of armies you inserted is equal to the total no of armies present at the souce country; at least one army unit must me present to retain the country.");
            return false;
        }
        return true;
    }
}
