package Models;

public class Orders {
    String d_order;
    String d_sourceName;
    String d_targetName;
    Integer d_noOfArmiesToMove;

    public Orders(String p_order, String p_targetName, Integer p_noOfArmiesToMove) {
        this.d_order = p_order;
        this.d_targetName = p_targetName;
        this.d_noOfArmiesToMove = p_noOfArmiesToMove;
    }

    public String getD_order() {
        return d_order;
    }

    public void setD_order(String p_order) {
        this.d_order = p_order;
    }

    public String getD_sourceName() {
        return d_sourceName;
    }

    public void setD_sourceName(String p_sourceName) {
        this.d_sourceName = p_sourceName;
    }

    public String getD_targetName() {
        return d_targetName;
    }

    public void setD_targetName(String p_targetName) {
        this.d_targetName = p_targetName;
    }

    public Integer getD_noOfArmiesToMove() {
        return d_noOfArmiesToMove;
    }

    public void setD_noOfArmiesToMove(Integer p_noOfArmiesToMove) {
        this.d_noOfArmiesToMove = p_noOfArmiesToMove;
    }

    public void executeDeployOrder(Player p_eachPlayer) {
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
