package Models;

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

    }

    /**
     * Valid boolean.
     *
     * @param p_currentState the p current state
     * @return the boolean
     */
    @Override
    public boolean valid(CurrentState p_currentState) {
        return false;
    }
}
