package Models;

import java.io.IOException;
import java.util.*;
import java.util.Map;

/**
 * The type Aggressive player.
 */
public class AggressivePlayer extends PlayerBehaviourStrategy{

    /**
     * The D deploy countries.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<>();

    /**
     * Gets player behaviour.
     *
     * @return the player behaviour
     */
    @Override
    public String getPlayerBehaviour() {
        return "Aggressive";
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
        System.out.println("Order creation for " + p_player.getD_name());
        String l_command = "";
        if (!checkIfArmiesDeployed(p_player)) {
            if (p_player.getD_unallocatedArmies() > 0) {
                l_command = createDeployOrder(p_player, p_currentState);
            } else {
                l_command = createAdvanceOrder(p_player, p_currentState);
            }
        } else {
            if (p_player.getD_cardsOwnedByPlayer().size() > 0) {
                int l_index = (int) (Math.random() * 3) + 1;
                switch (l_index) {
                    case 1:
                        System.out.println("Deploy order");
                        l_command = createDeployOrder(p_player, p_currentState);
                        break;
                    case 2:
                        System.out.println("Advance order");
                        l_command = createAdvanceOrder(p_player, p_currentState);
                        break;
                    case 3:
                        if (p_player.getD_cardsOwnedByPlayer().size() == 1) {
                            System.out.println("Cards");
                            l_command = createCardOrder(p_player, p_currentState, p_player.getD_cardsOwnedByPlayer().get(0));
                            break;
                        } else {
                            Random l_random = new Random();
                            int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size());
                            l_command = createCardOrder(p_player, p_currentState, p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                            break;
                        }
                    default:
                        l_command = createAdvanceOrder(p_player, p_currentState);
                        break;
                }
            } else {
                Random l_random = new Random();
                boolean l_randomBoolean = l_random.nextBoolean();
                if (l_randomBoolean) {
                    System.out.println("Without card deploy");
                    l_command = createDeployOrder(p_player, p_currentState);
                } else {
                    System.out.println("Without card advance");
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
        if(p_player.getD_currentCountries().stream().anyMatch(l_country -> l_country.getD_armies() > 0)){
            return true;
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
        Random l_random = new Random();
        Country l_strongestSourceCountry = getStrongestCountry(p_player, p_currentState);
        Country l_randomTargetCountry = p_currentState.getD_map().getCountryById(l_strongestSourceCountry.getD_neighbouringCountriesId().get(l_random.nextInt(l_strongestSourceCountry.getD_neighbouringCountriesId().size())));
        int l_noOfArmiesToMove = l_strongestSourceCountry.getD_armies() > 1 ? l_strongestSourceCountry.getD_armies() : 1;
        switch (p_cardName) {
            case "bomb":
                return "bomb " + l_randomTargetCountry.getD_countryName();
            case "blockade":
                return "blockade " + l_randomTargetCountry.getD_countryName();
            case "airlift":
                return "airlift " + l_strongestSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName() + " " + l_noOfArmiesToMove;
            case "negotiate":
                return "negotiate " + getRandomEnemyPlayer(p_player, p_currentState).getD_name();
        }
        return null;
    }

    /**
     * Gets random enemy player.
     *
     * @param p_player    the p player
     * @param p_gameState the p game state
     * @return the random enemy player
     */
    private Player getRandomEnemyPlayer(Player p_player, CurrentState p_gameState) {
        ArrayList<Player> l_playerList = new ArrayList<>();
        Random l_random = new Random();
        for (Player l_eachPlayer : p_gameState.getD_players()) {
            if (!l_eachPlayer.equals(p_player)) {
                l_playerList.add(l_eachPlayer);
            }
        }
        return l_playerList.get(l_random.nextInt(l_playerList.size()));
    }

    /**
     * Gets strongest country.
     *
     * @param p_player       the p player
     * @param p_currentState the p current state
     * @return the strongest country
     */
    private Country getStrongestCountry(Player p_player, CurrentState p_currentState) {
        List<Country> l_countriesOwnedByPlayer = p_player.getD_currentCountries();
        Country l_strongestCountry = calculateStrongestCountry(l_countriesOwnedByPlayer);
        return l_strongestCountry;

    }

    /**
     * Calculate strongest country country.
     *
     * @param p_countriesOwnedByPlayer the p countries owned by player
     * @return the country
     */
    private Country calculateStrongestCountry(List<Country> p_countriesOwnedByPlayer) {
        LinkedHashMap<Country,Integer> l_countryWithArmies = new LinkedHashMap<>();
        int l_largestNoOfArmies = 0;
        Country l_strongestCountry = null;
        for(Country l_eachCountry : p_countriesOwnedByPlayer){
            l_countryWithArmies.put(l_eachCountry,l_eachCountry.getD_armies());
        }
        l_largestNoOfArmies = Collections.max(l_countryWithArmies.values());
        for(Map.Entry<Country,Integer> l_entry : l_countryWithArmies.entrySet()){
            if(l_entry.getValue().equals(l_largestNoOfArmies)){
                return l_entry.getKey();
            }
        }
        return l_strongestCountry;
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
        Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
        moveArmiesFromItsNeighbours(p_player, l_randomSourceCountry, p_currentState);
        Random l_random = new Random();
        Country l_randomTargetCountry = p_currentState.getD_map().getCountryById(l_randomSourceCountry.getD_neighbouringCountriesId().get(l_random.nextInt(l_randomSourceCountry.getD_neighbouringCountriesId().size())));
        int l_noOfArmiesToMove = l_randomSourceCountry.getD_armies() > 1 ? l_randomSourceCountry.getD_armies() : 1;

        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName() + " " + l_noOfArmiesToMove;
    }

    /**
     * Move armies from its neighbours.
     *
     * @param p_player              the p player
     * @param p_randomSourceCountry the p random source country
     * @param p_currentState        the p current state
     */
    private void moveArmiesFromItsNeighbours(Player p_player, Country p_randomSourceCountry, CurrentState p_currentState) {
        List<Integer> l_adjacentCountryIds = p_randomSourceCountry.getD_neighbouringCountriesId();
        List<Country> l_listOfNeighbours = new ArrayList<>();
        for(int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++){
            Country l_country = p_currentState.getD_map().getCountryById(p_randomSourceCountry.getD_neighbouringCountriesId().get(l_index));
            if(p_player.getD_currentCountries().contains(l_country)){
                l_listOfNeighbours.add(l_country);
            }
        }

        int l_armiesToMove = 0;
        for(Country l_eachCountry : l_listOfNeighbours){
            if(p_randomSourceCountry.getD_armies() > 0){
                l_armiesToMove = p_randomSourceCountry.getD_armies() + l_eachCountry.getD_armies();
            }
            else{
                l_armiesToMove = l_eachCountry.getD_armies();
            }
        }
        p_randomSourceCountry.setD_armies(l_armiesToMove);
    }

    /**
     * Gets random country.
     *
     * @param p_deployCountries the p deploy countries
     * @return the random country
     */
    private Country getRandomCountry(ArrayList<Country> p_deployCountries) {
        Random l_random = new Random();
        return p_deployCountries.get(l_random.nextInt(p_deployCountries.size()));
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
        Random l_random = new Random();
        Country l_strongestCountry = getStrongestCountry(p_player, p_currentState);
        d_deployCountries.add(l_strongestCountry);
        int l_noOfArmiesToDeploy = 1;
        if(p_player.getD_unallocatedArmies()>1){
            l_noOfArmiesToDeploy = l_random.nextInt(p_player.getD_unallocatedArmies()-1)+1;
        }
        return String.format("deploy %s %d", l_strongestCountry.getD_countryName(), l_noOfArmiesToDeploy);
    }
}
