package Models;

import java.io.IOException;
import java.util.*;
import java.util.Map;

/**
 * The type Benevolent player.
 */
public class BenevolentPlayer extends PlayerBehaviourStrategy{

    /**
     * The D deploy countries.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<>();

    /**
     * Instantiates a new Benevolent player.
     */
    public BenevolentPlayer() {
    }

    /**
     * Gets player behaviour.
     *
     * @return the player behaviour
     */
    @Override
    public String getPlayerBehaviour() {
        return "Benevolent";
    }

    /**
     * Create order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the string
     * @throws IOException the io exception
     */
    @Override
    public String createOrder(Player p_player, CurrentState p_currentState) throws IOException {
        System.out.println("Creating order for : " + p_player.getD_name());
        String l_command;
        if(!checkIfArmiesDeployed(p_player)){
            if(p_player.getD_unallocatedArmies() > 0){
                l_command = createDeployOrder(p_player, p_currentState);
            }
            else{
                l_command = createAdvanceOrder(p_player, p_currentState);
            }
        }
        else{
            if(p_player.getD_cardsOwnedByPlayer().size() > 0){
                System.out.println("Enters Card Logic");
                int l_index = (int) (Math.random() * 3) + 1;
                switch (l_index){
                    case 1:
                        System.out.println("Deploy!");
                        l_command = createDeployOrder(p_player, p_currentState);
                        break;
                    case 2:
                        System.out.println("Advance!");
                        l_command = createAdvanceOrder(p_player, p_currentState);
                        break;
                    case 3:
                        if(p_player.getD_cardsOwnedByPlayer().size() == 1){
                            System.out.println("Cards!");
                            l_command = createCardOrder(p_player, p_currentState, p_player.getD_cardsOwnedByPlayer().get(0));
                            break;
                        }
                        else{
                            Random l_random = new Random();
                            int l_cardIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size());
                            l_command = createCardOrder(p_player, p_currentState, p_player.getD_cardsOwnedByPlayer().get(l_cardIndex));
                            break;
                        }
                    default:
                        l_command = createAdvanceOrder(p_player, p_currentState);
                        break;
                }
            }
            else{
                Random l_random = new Random();
                Boolean l_randomBoolean = l_random.nextBoolean();
                if(l_randomBoolean){
                    System.out.println("Without Card Deploy Logic");
                    l_command = createDeployOrder(p_player, p_currentState);
                }
                else{
                    System.out.println("Without Card Advance Logic");
                    l_command = createAdvanceOrder(p_player, p_currentState);
                }
            }
        }
        return l_command;
    }

    /**
     * Check if armies deployed boolean.
     *
     * @param p_player the p player
     * @return the boolean
     */
    private boolean checkIfArmiesDeployed(Player p_player) {
        for(Country l_country : p_player.getD_currentCountries()){
            if(l_country.getD_armies() > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Create card order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @param p_cardName     the p card name
     * @return the string
     */
    @Override
    public String createCardOrder(Player p_player, CurrentState p_currentState, String p_cardName) {
        int l_armiesToDeploy = 0;
        Random l_random = new Random();
        Country l_randomOwnedCountry = getRandomCountry(p_player.getD_currentCountries());
        if(l_randomOwnedCountry.getD_armies() > 1){
            l_armiesToDeploy = l_random.nextInt(l_randomOwnedCountry.getD_armies() - 1) + 1;
        }
        else{
            l_armiesToDeploy = 1;
        }
        switch (p_cardName){
            case "bomb":
                System.err.println("I donot hurt anyone as I am a benevolent player.");
                return ("bomb  false");
            case "blockade":
                return String.format("blockade %s", l_randomOwnedCountry.getD_countryName());
            case "airlift":
                return String.format("airlift %s %s %d", l_randomOwnedCountry.getD_countryName(), getRandomCountry(p_player.getD_currentCountries()), l_armiesToDeploy);
            case "negotiate":
                return String.format("negotiate %s", getRandomEnemyPlayer(p_currentState, p_player).getD_name());
        }
        return null;
    }

    /**
     * Gets random enemy player.
     *
     * @param p_currentState the p current state
     * @param p_player       the p player
     * @return the random enemy player
     */
    private Player getRandomEnemyPlayer(CurrentState p_currentState, Player p_player) {
        ArrayList<Player> l_players = new ArrayList<>();
        Random l_random = new Random();
        for(Player l_eachPlayer : p_currentState.getD_players()){
            if(!l_eachPlayer.equals(p_player)){
                l_players.add(l_eachPlayer);
            }
        }
        return l_players.get(l_random.nextInt(l_players.size()));
    }

    /**
     * Create advance order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the string
     */
    @Override
    public String createAdvanceOrder(Player p_player, CurrentState p_currentState) {
        int l_armiesToAdvance;
        Random l_random = new Random();
        Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
        System.out.println("Source Country: " + l_randomSourceCountry.getD_countryName());
        Country l_weakestTargetCountry = getWeakestNeighbour(l_randomSourceCountry, p_currentState, p_player);
        if(l_weakestTargetCountry == null){
            return null;
        }
        System.out.println("Target Country: " + l_weakestTargetCountry.getD_countryName());
        if(l_randomSourceCountry.getD_armies() > 1){
            l_armiesToAdvance = l_random.nextInt(l_randomSourceCountry.getD_armies() - 1) + 1;
        }
        else{
            l_armiesToAdvance = 1;
        }
        System.out.println("advance " + l_randomSourceCountry.getD_countryName() + " " + l_weakestTargetCountry.getD_countryName() + " " + l_armiesToAdvance);
        return String.format("advance %s %s %d", l_randomSourceCountry.getD_countryName(), l_weakestTargetCountry.getD_countryName(), l_armiesToAdvance);
    }

    /**
     * Gets weakest neighbour.
     *
     * @param p_randomSourceCountry the p random source country
     * @param p_currentState        the p current state
     * @param p_player              the p player
     * @return the weakest neighbour
     */
    private Country getWeakestNeighbour(Country p_randomSourceCountry, CurrentState p_currentState, Player p_player) {
        List<Integer> l_neighbourCountryIds = p_randomSourceCountry.getD_neighbouringCountriesId();
        List<Country> l_listOfNeighbours = new ArrayList<>();
        for(Integer l_index =0; l_index < l_neighbourCountryIds.size(); l_index++){
            Country l_country = p_currentState.getD_map().getCountryById(p_randomSourceCountry.getD_neighbouringCountriesId().get(l_index));
            if(p_player.getD_currentCountries().contains(l_country)){
                l_listOfNeighbours.add(l_country);
            }
        }
        if(!l_listOfNeighbours.isEmpty()){
            return evaluateWeakestCountry(l_listOfNeighbours);
        }
        return null;
    }

    /**
     * Gets random country.
     *
     * @param p_deployCountries the p deploy countries
     * @return the random country
     */
    private Country getRandomCountry(List<Country> p_deployCountries) {
        Random l_random = new Random();
        int l_index = l_random.nextInt(p_deployCountries.size());
        return p_deployCountries.get(l_index);
    }

    /**
     * Create deploy order string.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the string
     */
    @Override
    public String createDeployOrder(Player p_player, CurrentState p_currentState) {
        if(p_player.getD_unallocatedArmies() > 0){
            Country l_weakestCountry = getWeakestCountry(p_player);
            d_deployCountries.add(l_weakestCountry);

            Random l_random = new Random();
            int l_armiesToDeploy = 1;
            if(p_player.getD_unallocatedArmies() > 1) {
                l_armiesToDeploy = l_random.nextInt(p_player.getD_unallocatedArmies() - 1) + 1;
            }
            System.out.println("deploy " + l_weakestCountry.getD_countryName() + " " + l_armiesToDeploy);
            return String.format("deploy %s %d", l_weakestCountry.getD_countryName(), l_armiesToDeploy);
        }
        else{
            return createAdvanceOrder(p_player, p_currentState);
        }
    }

    /**
     * Gets weakest country.
     *
     * @param p_player the p player
     * @return the weakest country
     */
    private Country getWeakestCountry(Player p_player) {
        List<Country> l_countries = p_player.getD_currentCountries();
        Country l_Country = evaluateWeakestCountry(l_countries);
        return l_Country;
    }

    /**
     * Evaluate weakest country country.
     *
     * @param p_countries the p countries
     * @return the country
     */
    private Country evaluateWeakestCountry(List<Country> p_countries) {
        LinkedHashMap<Country, Integer> l_countryArmyMap = new LinkedHashMap<>();
        int l_smallestArmy;
        Country l_country = null;
        for(Country l_eachCountry : p_countries){
            l_countryArmyMap.put(l_eachCountry, l_eachCountry.getD_armies());
        }
        l_smallestArmy = Collections.min(l_countryArmyMap.values());
        for(Map.Entry<Country, Integer> l_entry : l_countryArmyMap.entrySet()){
            if(l_entry.getValue() == l_smallestArmy){
                return l_entry.getKey();
            }
        }
        return l_country;
    }
}
