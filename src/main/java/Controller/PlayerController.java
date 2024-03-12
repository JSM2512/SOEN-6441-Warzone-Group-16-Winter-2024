package Controller;

import Constants.ProjectConstants;
import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Player controller.
 */
public class PlayerController {

    /**
     * The D current state.
     */
    CurrentState d_currentState = new CurrentState();

    /**
     * Assign countries.
     *
     * @param p_currentState the p current state
     */
    public void assignCountries(CurrentState p_currentState) {
        if(p_currentState.getD_players() == null || p_currentState.getD_players().isEmpty()){
            System.out.println(ProjectConstants.NO_PLAYER_IN_GAME);
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.NO_PLAYER_IN_GAME,"Type 1");
            return;
        }
        List<Player> l_players = p_currentState.getD_players();
        List<Country> l_countryList = p_currentState.getD_map().getD_mapCountries();

        int l_noOfPlayers = l_players.size();
        int l_noOfCountries = l_countryList.size();

        Player l_neutralPlayer = null;
        for(Player l_eachPlayer : p_currentState.getD_players()){
            if(l_eachPlayer.getD_name().equalsIgnoreCase("Neutral")){
                l_neutralPlayer = l_eachPlayer;
                break;
            }
        }
        if(l_neutralPlayer != null){
            l_noOfPlayers--;
        }

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
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.NO_COUNTRY_IN_MAP,"Type 1");
            return;
        }

        for (Player l_eachPlayer : p_players) {
            if(!l_eachPlayer.getD_name().equalsIgnoreCase("Neutral")) {
                if (l_unallocatedCountries.isEmpty()) {
                    break;
                }
                for (int i = 1; i <= p_noOfCountiesToEachPlayer; i++) {
                    Random l_randomNumber = new Random();
                    int l_randomIndex = l_randomNumber.nextInt(l_unallocatedCountries.size());

                    if (l_eachPlayer.getD_currentCountries() == null) {
                        l_eachPlayer.setD_currentCountries(new ArrayList<>());
                    }
                    l_eachPlayer.getD_currentCountries().add(l_unallocatedCountries.get(l_randomIndex));
                    l_unallocatedCountries.remove(l_randomIndex);
                }
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
            d_currentState.getD_modelLogger().setD_message(ProjectConstants.NO_PLAYER_IN_GAME,"Type 1");
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
     * Is unexecuted orders exist boolean.
     *
     * @param p_playerList the p player list
     * @return the boolean
     */
    public boolean isUnexecutedOrdersExist(List<Player> p_playerList) {
        int l_totalCountOfUnexecutedOrders = 0;
        for(Player l_eachPlayer : p_playerList){
            l_totalCountOfUnexecutedOrders += l_eachPlayer.getD_orders().size();
        }
        return l_totalCountOfUnexecutedOrders != 0;
    }

    /**
     * Check for more orders boolean.
     *
     * @param p_players the p players
     * @return the boolean
     */
    public boolean checkForMoreOrders(List<Player> p_players) {
        for(Player l_eachPlayer : p_players){
            if(l_eachPlayer.hasMoreOrders()){
                return true;
            }
        }
        return false;
    }

    public void resetPlayerFlag(List<Player> p_playerList){
        for(Player l_eachPlayer : p_playerList){
            if(!l_eachPlayer.getD_name().equalsIgnoreCase("Neutral")){
                l_eachPlayer.setMoreOrders(true);
            }
            l_eachPlayer.setD_oneCardPerTurn(false);
            l_eachPlayer.resetNegotiation();
        }
    }
}
