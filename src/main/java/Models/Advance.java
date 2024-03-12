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
            Integer l_armiesToUpdate = l_sourceCountry.getD_armies() - this.d_noOfArmiesToPlace;
            l_sourceCountry.setD_armies(l_armiesToUpdate);
            if(l_playerOfTargetCountry.getD_name().equalsIgnoreCase(this.d_intitiatingPlayer.getD_name())){
                deployArmiesToTarget(l_targetCountry);
            }
            else if(l_targetCountry.getD_armies() == 0){
                conquerTargetCountry(p_currentState, l_playerOfTargetCountry, l_targetCountry);
                this.d_intitiatingPlayer.assignCard();
            }
            else{
                battleOrderResult(p_currentState, l_playerOfTargetCountry, l_sourceCountry, l_targetCountry);
            }
        }
        else{
            // Logger info needed
        }
    }

    private void battleOrderResult(CurrentState p_currentState, Player p_playerOfTargetCountry, Country p_sourceCountry, Country p_targetCountry) {
        int l_armiesInAttack = 0;
        if(d_noOfArmiesToPlace < p_targetCountry.getD_armies()){
            l_armiesInAttack = d_noOfArmiesToPlace;
        }
        else{
            l_armiesInAttack = p_targetCountry.getD_armies();
        }
        List<Integer> l_defenderArmies = generateRandomArmyUnits(l_armiesInAttack, "defender");
        List<Integer> l_attackerArmies = generateRandomArmyUnits(l_armiesInAttack, "attacker");
        this.produceBattleResult(p_sourceCountry, p_targetCountry, l_attackerArmies, l_defenderArmies, p_playerOfTargetCountry);
        // Logger Info needed
        updateContinents(d_intitiatingPlayer, p_playerOfTargetCountry, p_currentState);
    }

    private void produceBattleResult(Country p_sourceCountry, Country p_targetCountry, List<Integer> p_attackerArmies, List<Integer> p_defenderArmies, Player p_playerOfTargetCountry) {
        Integer l_attackerArmiesLeft = 0;
        Integer l_defenderArmiesLeft = 0;
        if(d_noOfArmiesToPlace > p_targetCountry.getD_armies()){
            l_attackerArmiesLeft = d_noOfArmiesToPlace - p_targetCountry.getD_armies();
        }
        else{
            l_attackerArmiesLeft = 0;
        }
        if(d_noOfArmiesToPlace < p_targetCountry.getD_armies()){
            l_defenderArmiesLeft = p_targetCountry.getD_armies() - d_noOfArmiesToPlace;
        }
        else{
            l_defenderArmiesLeft = 0;
        }
        for(int i=0 ; i<p_attackerArmies.size(); i++){
            if(p_attackerArmies.get(i) > p_defenderArmies.get(i)){
                l_attackerArmiesLeft++;
            }
            else{
                l_defenderArmiesLeft++;
            }
        }
        handleSurvivingArmies(l_attackerArmiesLeft, l_defenderArmiesLeft, p_sourceCountry, p_targetCountry, p_playerOfTargetCountry);
    }

    private void handleSurvivingArmies(Integer p_attackerArmiesLeft, Integer p_defenderArmiesLeft, Country p_sourceCountry, Country p_targetCountry, Player p_playerOfTargetCountry) {
        if(p_defenderArmiesLeft == 0) {
            p_playerOfTargetCountry.getD_currentCountries().remove(p_targetCountry);
            p_targetCountry.setD_armies(p_attackerArmiesLeft);
            d_intitiatingPlayer.getD_currentCountries().add(p_targetCountry);
            // Logger Info needed
            d_intitiatingPlayer.assignCard();
        }
        else{
            p_targetCountry.setD_armies(p_defenderArmiesLeft);
            Integer l_sourceArmiesToUpdate = p_sourceCountry.getD_armies() + p_attackerArmiesLeft;
            p_sourceCountry.setD_armies(l_sourceArmiesToUpdate);
            // Logger Info needed
        }
    }

    private List<Integer> generateRandomArmyUnits(int p_armiesInAttack, String p_role) {
        List<Integer> l_armyList = new ArrayList<>();
        double l_probability = 0.0;
        if(p_role.equals("attacker")){
            l_probability = 0.7;
        }
        else{
            l_probability = 0.5;
        }
        for(int i = 0; i < p_armiesInAttack; i++) {
           int l_randomNumber = getRandomInteger(1,10);
           Integer l_armyUnit = (int) Math.round(l_randomNumber * l_probability);
           l_armyList.add(l_armyUnit);
        }
        return l_armyList;
    }

    private int getRandomInteger(int p_min, int p_max) {
        return ((int) (Math.random() * (p_max - p_min))) + p_min;
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
