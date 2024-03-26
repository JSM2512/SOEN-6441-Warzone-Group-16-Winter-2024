package Models;

import java.io.IOException;
import java.util.*;
import java.util.Map;

public class AggressivePlayer extends PlayerBehaviourStrategy{

    ArrayList<Country> d_deployCountries = new ArrayList<>();

    @Override
    public String getPlayerBehaviour() {
        return "Aggressive";
    }

    @Override
    public String createOrder(Player p_player, CurrentState p_currentState) throws IOException {


        return null;
    }

    @Override
    public String createCardOwner(Player p_player, CurrentState p_currentState, String p_cardName) {
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
                return "negotiate " + l_randomTargetCountry.getD_countryName();
        }
        return null;
    }


    private Country getStrongestCountry(Player p_player, CurrentState p_currentState) {
        List<Country> l_countriesOwnedByPlayer = p_player.getD_currentCountries();
        Country l_strongestCountry = claculateStrongestCountry(l_countriesOwnedByPlayer);
        return l_strongestCountry;

    }

    private Country claculateStrongestCountry(List<Country> p_countriesOwnedByPlayer) {
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

    @Override
    public String createAdvanceOrder(Player p_player, CurrentState p_currentState) {
        Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
        moveArmiesFromItsNeighbours(p_player, l_randomSourceCountry, p_currentState);
        Random l_random = new Random();
        Country l_randomTargetCountry = p_currentState.getD_map().getCountryById(l_randomSourceCountry.getD_neighbouringCountriesId().get(l_random.nextInt(l_randomSourceCountry.getD_neighbouringCountriesId().size())));
        int l_noOfArmiesToMove = l_randomSourceCountry.getD_armies() > 1 ? l_randomSourceCountry.getD_armies() : 1;

        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName() + " " + l_noOfArmiesToMove;
    }

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

    private Country getRandomCountry(ArrayList<Country> p_deployCountries) {
        Random l_random = new Random();
        return p_deployCountries.get(l_random.nextInt(p_deployCountries.size()));
    }

    @Override
    public String createDeployOrder(Player p_player, CurrentState p_currentState) {
        return null;
    }
}
