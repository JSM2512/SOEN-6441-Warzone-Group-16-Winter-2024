package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Random player.
 */
public class RandomPlayer extends PlayerBehaviourStrategy {

    /**
     * Instantiates a new Random player.
     */
    public RandomPlayer() {
    }

    /**
     * The D deploy countries.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    /**
     * Create order string.
     *
     * @param p_player    the p player
     * @param p_gameState the p game state
     * @return the string
     */
    @Override
    public String createOrder(Player p_player, CurrentState p_gameState) {
        System.out.println("Creating order for : " + p_player.getD_name());
        String l_command;
        if (!checkIfArmiesDepoyed(p_player)) {
            if(p_player.getD_unallocatedArmies() > 0) {
                l_command = createDeployOrder(p_player, p_gameState);
            }else{
                l_command = createAdvanceOrder(p_player, p_gameState);
            }
        } else {
            if(p_player.getD_cardsOwnedByPlayer().size() > 0){
                int l_index = (int) (Math.random() * 3) +1;
                switch (l_index) {
                    case 1:
                        l_command = createDeployOrder(p_player, p_gameState);
                        break;
                    case 2:
                        l_command = createAdvanceOrder(p_player, p_gameState);
                        break;
                    case 3:
                        if (p_player.getD_cardsOwnedByPlayer().size() == 1) {
                            l_command = createCardOrder(p_player, p_gameState, p_player.getD_cardsOwnedByPlayer().get(0));
                            break;
                        } else {
                            Random l_random = new Random();
                            int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size());
                            l_command = createCardOrder(p_player, p_gameState, p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                            break;
                        }
                    default:
                        l_command = createAdvanceOrder(p_player, p_gameState);
                        break;
                }
            }else{
                Random l_random = new Random();
                Boolean l_randomBoolean = l_random.nextBoolean();
                if(l_randomBoolean) {
                    l_command = createDeployOrder(p_player, p_gameState);
                }else {
                    l_command = createAdvanceOrder(p_player, p_gameState);
                }
            }
        }
        return l_command;
    }

    /**
     * Check if armies depoyed boolean.
     *
     * @param p_player the p player
     * @return the boolean
     */
    private boolean checkIfArmiesDepoyed(Player p_player) {
        for(Country l_eachCountry : p_player.getD_currentCountries()){
            if(l_eachCountry.getD_armies() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets player behaviour.
     *
     * @return the player behaviour
     */
    @Override
    public String getPlayerBehaviour() {
        return "Random";
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
        int l_armiesToSend;
        Country l_randomOwnedCountry = getRandomCountry(p_player.getD_currentCountries());
        Country l_randomNeighbour = p_currentState.getD_map().getCountryById(l_randomOwnedCountry.getD_neighbouringCountriesId().get(l_random.nextInt(l_randomOwnedCountry.getD_neighbouringCountriesId().size())));
        Player l_randomPlayer = getRandomPlayer(p_currentState, p_player);
        if(l_randomOwnedCountry.getD_armies() > 1){
            l_armiesToSend = l_random.nextInt(l_randomOwnedCountry.getD_armies() - 1) + 1;
        }
        else {
            l_armiesToSend = 1;
        }
        switch (p_cardName) {
            case "bomb":
                return "bomb " + l_randomNeighbour.getD_countryName();
            case "blockade":
                return "blockade " + l_randomOwnedCountry.getD_countryName();
            case "airlift":
                return "airlift " + l_randomOwnedCountry.getD_countryName() + " " + getRandomCountry(p_player.getD_currentCountries()) + " " + l_armiesToSend;
            case "negotiate":
                return "negotiate " + l_randomPlayer.getD_name();
            default:
                return null;
        }
    }

    /**
     * Gets random player.
     *
     * @param p_currentState the p current state
     * @param p_player       the p player
     * @return the random player
     */
    private Player getRandomPlayer(CurrentState p_currentState, Player p_player) {
        ArrayList<Player> l_PlayerList = new ArrayList<Player>();
        Random l_random = new Random();
        for(Player l_eachPlayer : p_currentState.getD_players()){
            if(l_eachPlayer.equals(p_player)){
                l_PlayerList.add(l_eachPlayer);
            }
        }
        return l_PlayerList.get(l_random.nextInt(l_PlayerList.size()));
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
        int l_armiesToAdvance = 1;
        Random l_random = new Random();
        Country l_randomOwnedCountry = getRandomCountry(d_deployCountries);
        int l_randomIndex = l_random.nextInt(l_randomOwnedCountry.getD_neighbouringCountriesId().size());
        Country l_randomNeighbour;
        if(l_randomOwnedCountry.getD_neighbouringCountriesId().size() > 1){
            l_randomNeighbour = p_currentState.getD_map().getCountryById(l_randomOwnedCountry.getD_neighbouringCountriesId().get(l_randomIndex));
        }
        else {
            l_randomNeighbour = p_currentState.getD_map().getCountryById(l_randomOwnedCountry.getD_neighbouringCountriesId().get(0));
        }
      if(l_randomOwnedCountry.getD_armies() > 1){
          l_armiesToAdvance = l_random.nextInt(l_randomOwnedCountry.getD_armies() - 1) + 1;
      }
      else {
            l_armiesToAdvance = 1;
      }
      return "advance " + l_randomOwnedCountry.getD_countryName() + " " + l_randomNeighbour.getD_countryName() + " " + l_armiesToAdvance;
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
            Random l_random = new Random();
            Country l_randomCountry = getRandomCountry(p_player.getD_currentCountries());
            d_deployCountries.add(l_randomCountry);
            int l_armiesToDeploy = 1;
            if(p_player.getD_unallocatedArmies() > 1){
                l_armiesToDeploy = l_random.nextInt(p_player.getD_unallocatedArmies() - 1) + 1;
            }
            return String.format("deploy %s %d", l_randomCountry.getD_countryName(), l_armiesToDeploy);
        }
        else{
            return createAdvanceOrder(p_player, p_currentState);
        }
    }

    /**
     * Gets random country.
     *
     * @param p_currentCountries the p current countries
     * @return the random country
     */
    private Country getRandomCountry(List<Country> p_currentCountries) {
        Random l_random = new Random();
        int l_randomIndex = l_random.nextInt(p_currentCountries.size());
        return p_currentCountries.get(l_randomIndex);
    }
}
