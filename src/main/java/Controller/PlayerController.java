package Controller;

import Constants.ProjectConstants;
import Models.CurrentState;
import Models.Player;
import Models.Continent;
import Models.Country;
import Models.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Player controller.
 */
public class PlayerController {
    /**
     * Assign countries.
     *
     * @param p_currentState the p current state
     */
    public void assignCountries(CurrentState p_currentState) {
        if(p_currentState.getD_players() == null || p_currentState.getD_players().isEmpty()){
            System.out.println(ProjectConstants.NO_PLAYER_IN_GAME);
            return;
        }
        List<Player> l_players = p_currentState.getD_players();
        List<Country> l_countryList = p_currentState.getD_map().getD_mapCountries();

        int l_noOfPlayers = l_players.size();
        int l_noOfCountries = l_countryList.size();
        int l_noOfCountiesToEachPlayer = Math.floorDiv(l_noOfCountries, l_noOfPlayers);
        assignRandomCountriesToPlayers(l_players, l_countryList, l_noOfCountiesToEachPlayer);
        displayAssignedCountries(l_players);
        assignContinentToPlayers(l_players, p_currentState.getD_map().getD_mapContinents());
    }

    /**
     * Display assigned countries.
     *
     * @param p_players the p players
     */
    private void displayAssignedCountries(List<Player> p_players) {
        for (Player l_currentPlayer : p_players) {
            System.out.print("Player " + l_currentPlayer.getD_name() + " has these countries: ");
            for (Country l_currentCountry : l_currentPlayer.getD_currentCountries()) {
                System.out.print(l_currentCountry.getD_countryName() + " ");
            }
            System.out.println();
        }
    }


    /**
     * Assign continent to players.
     *
     * @param p_players       the p players
     * @param p_mapContinents the p map continents
     */
    public void assignContinentToPlayers(List<Player> p_players, List<Continent> p_mapContinents) {
        for (Player l_eachPlayer : p_players) {
            List<Country> l_countriesOwnedByPlayer = l_eachPlayer.getD_currentCountries();

            if (l_eachPlayer.getD_currentCountries() != null && !l_eachPlayer.getD_currentCountries().isEmpty()) {
                for (Continent l_eachContinent : p_mapContinents) {
                    boolean l_isContinentOwned = l_countriesOwnedByPlayer.containsAll(l_eachContinent.getD_countries());
                    if (l_isContinentOwned) {
                        l_eachPlayer.setContinent(l_eachContinent);
                    }
                }
            }
        }
    }

    /**
     * Assign random countries to players.
     *
     * @param p_players                  the p players
     * @param p_countryList              the p country list
     * @param p_noOfCountiesToEachPlayer the p no of counties to each player
     */
    public void assignRandomCountriesToPlayers(List<Player> p_players, List<Country> p_countryList, int p_noOfCountiesToEachPlayer) {
        List<Country> l_unallocatedCountries = new ArrayList<>(p_countryList);
        if (l_unallocatedCountries.isEmpty()) {
            System.out.println(ProjectConstants.NO_COUNTRY_IN_MAP);
            return;
        }
        for (Player l_eachPlayer : p_players) {
            for (int i = 1; i <= p_noOfCountiesToEachPlayer; i++) {
                if (l_unallocatedCountries.isEmpty()) {
                    break;
                }
                Random l_randomNumber = new Random();
                int l_randomIndex = l_randomNumber.nextInt(l_unallocatedCountries.size());

                if (l_eachPlayer.getD_currentCountries() == null) {
                    l_eachPlayer.setD_currentCountries(new ArrayList<>());
                }
                l_eachPlayer.getD_currentCountries().add(l_unallocatedCountries.get(l_randomIndex));
                l_unallocatedCountries.remove(l_randomIndex);
            }
        }

        if (!l_unallocatedCountries.isEmpty()) {
            assignRandomCountriesToPlayers(p_players, l_unallocatedCountries, 1);
        }
    }

    /**
     * Assign armies.
     *
     * @param p_currentState the p current state
     */
    public void assignArmies(CurrentState p_currentState) {
        if(p_currentState.getD_players() == null || p_currentState.getD_players().isEmpty()){
            System.out.println(ProjectConstants.NO_PLAYER_IN_GAME);
            return;
        }
        for (Player l_eachPlayer : p_currentState.getD_players()) {
            int l_countOfArmiesOfEachPlayer = getNoOfArmies(l_eachPlayer);
            l_eachPlayer.setD_unallocatedArmies(l_countOfArmiesOfEachPlayer);
            System.out.println("Player " + l_eachPlayer.getD_name() + " got assigned : " + l_countOfArmiesOfEachPlayer + " Armies.");
        }
    }

    /**
     * Gets no of armies.
     *
     * @param p_eachPlayer the p each player
     * @return the no of armies
     */
    public int getNoOfArmies(Player p_eachPlayer) {
        int l_currentArmySize = 0;
        if (p_eachPlayer.getD_currentCountries() != null && !p_eachPlayer.getD_currentCountries().isEmpty()) {
            l_currentArmySize = Math.max(3, Math.round((float) (p_eachPlayer.getD_currentCountries().size() / 3)));
        }
        if (p_eachPlayer.getD_currentContinents() != null && !p_eachPlayer.getD_currentContinents().isEmpty()) {
            int l_totalContinentValue = 0;
            for (Continent l_eachContinent : p_eachPlayer.getD_currentContinents()) {
                l_totalContinentValue += l_eachContinent.getD_continentValue();
            }
            l_currentArmySize += l_totalContinentValue;
        }
        return l_currentArmySize;
    }

    /**
     * Is unallocated armies exist boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean isUnallocatedArmiesExist(CurrentState p_currentState) {
        int l_totalCount = 0;
        for (Player l_eachPlayer : p_currentState.getD_players()) {
            l_totalCount += l_eachPlayer.getD_unallocatedArmies();
        }
        return l_totalCount > 0;
    }

    /**
     * Create deploy order.
     *
     * @param p_command the p command
     * @param p_player  the p player
     */
    public void createDeployOrder(String p_command, Player p_player) {
        List<Orders> l_orders;
        if (p_player.getD_orders() == null || p_player.getD_orders().isEmpty()) {
            l_orders = new ArrayList<>();
        } else {
            l_orders = p_player.getD_orders();
        }

        String l_countryName = p_command.split(" ")[1];
        int l_noOfArmiesToDeploy = Integer.parseInt(p_command.split(" ")[2]);

        if (!validateCountryBelongstoPlayer(p_player, l_countryName)) {
            System.out.println("The country " + l_countryName + " does not belong to this player.");
        }
        else if (validateNoOfArmiesToDeploy(p_player, l_noOfArmiesToDeploy)) {
            System.out.println(ProjectConstants.INVALID_NO_OF_ARMIES);
        }
        else {
            Orders l_order = new Orders(p_command.split(" ")[0], l_countryName, l_noOfArmiesToDeploy);
            l_orders.add(l_order);

            p_player.setD_orders(l_orders);

            Integer l_unallocatedArmies = p_player.getD_unallocatedArmies() - l_noOfArmiesToDeploy;
            p_player.setD_unallocatedArmies(l_unallocatedArmies);

            System.out.println(ProjectConstants.ORDER_ADDED);
        }
    }

    /**
     * Validate country belongsto player boolean.
     *
     * @param p_player      the p player
     * @param p_countryName the p country name
     * @return the boolean
     */
    private boolean validateCountryBelongstoPlayer(Player p_player, String p_countryName) {
        for (Country l_eachCountry : p_player.getD_currentCountries()) {
            if (l_eachCountry.getD_countryName().equals(p_countryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate no of armies to deploy boolean.
     *
     * @param p_player           the p player
     * @param p_noOfDeployArmies the p no of deploy armies
     * @return the boolean
     */
    private boolean validateNoOfArmiesToDeploy(Player p_player, int p_noOfDeployArmies){
        if(p_player.getD_unallocatedArmies() < p_noOfDeployArmies){
            return true;
        }
        return false;
    }

    /**
     * Is unexecuted orders exist boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    public boolean isUnexecutedOrdersExist(CurrentState p_currentState) {
        int l_totalCountOfUnexecutedOrders = 0;
        for(Player l_eachPlayer : p_currentState.getD_players()){
            l_totalCountOfUnexecutedOrders += l_eachPlayer.getD_orders().size();
        }
        return l_totalCountOfUnexecutedOrders > 0;
    }
}
