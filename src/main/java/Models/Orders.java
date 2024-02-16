package Models;

/**
 * Model Class Orders.
 */
public class Orders {
    /**
     * The D order.
     */
    String d_order;
    /**
     * The D source name.
     */
    String d_sourceName;
    /**
     * The D target name.
     */
    String d_targetName;
    /**
     * The D no of armies to move.
     */
    Integer d_noOfArmiesToMove;

    /**
     * Instantiates a new Orders.
     *
     * @param p_order            the p order
     * @param p_targetName       the p target name
     * @param p_noOfArmiesToMove the p no of armies to move
     */
    public Orders(String p_order, String p_targetName, Integer p_noOfArmiesToMove) {
        this.d_order = p_order;
        this.d_targetName = p_targetName;
        this.d_noOfArmiesToMove = p_noOfArmiesToMove;
    }

    /**
     * Gets d order.
     *
     * @return the d order
     */
    public String getD_order() {
        return d_order;
    }

    /**
     * Sets d order.
     *
     * @param p_order the p order
     */
    public void setD_order(String p_order) {
        this.d_order = p_order;
    }

    /**
     * Gets d source name.
     *
     * @return the d source name
     */
    public String getD_sourceName() {
        return d_sourceName;
    }

    /**
     * Sets d source name.
     *
     * @param p_sourceName the p source name
     */
    public void setD_sourceName(String p_sourceName) {
        this.d_sourceName = p_sourceName;
    }

    /**
     * Gets d target name.
     *
     * @return the d target name
     */
    public String getD_targetName() {
        return d_targetName;
    }

    /**
     * Sets d target name.
     *
     * @param p_targetName the p target name
     */
    public void setD_targetName(String p_targetName) {
        this.d_targetName = p_targetName;
    }

    /**
     * Gets d no of armies to move.
     *
     * @return the d no of armies to move
     */
    public Integer getD_noOfArmiesToMove() {
        return d_noOfArmiesToMove;
    }

    /**
     * Sets d no of armies to move.
     *
     * @param p_noOfArmiesToMove the p no of armies to move
     */
    public void setD_noOfArmiesToMove(Integer p_noOfArmiesToMove) {
        this.d_noOfArmiesToMove = p_noOfArmiesToMove;
    }

    /**
     * Execute.
     *
     * @param p_eachPlayer the p each player
     */
    public void execute(Player p_eachPlayer) {
        if(d_order.equals("deploy")){
            for(Country l_eachCountry : p_eachPlayer.getD_currentCountries()){
                if(l_eachCountry.getD_countryName().equals(this.d_targetName)){
                    l_eachCountry.setD_armies(l_eachCountry.getD_armies() + this.d_noOfArmiesToMove);
                    break;
                }
            }
        }
    }
}
