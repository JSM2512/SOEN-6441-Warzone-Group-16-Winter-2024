package Models;

public class Deploy implements Orders{

    String d_targetCountry;
    String d_sourceCountry;
    Integer d_noOfArmiesToMove;

    public Deploy(String d_targetCountry, String d_sourceCountry, Integer d_noOfArmiesToMove) {
        this.d_targetCountry = d_targetCountry;
        this.d_sourceCountry = d_sourceCountry;
        this.d_noOfArmiesToMove = d_noOfArmiesToMove;
    }

    @Override
    public void execute(Player p_eachPlayer) {
        for(Country l_eachCountry : p_eachPlayer.getD_currentCountries()){
            if(l_eachCountry.getD_countryName().equals(this.d_targetCountry)){
                l_eachCountry.setD_armies(l_eachCountry.getD_armies() + this.d_noOfArmiesToMove);
                break;
            }
        }
    }
}
