package Models;

public class Advance implements Orders{

    String d_sourceCountry;
    String d_targetCountry;
    Integer d_noOfArmiesToPlace;
    Player d_player;

    public Advance(String p_sourceCountry, String p_targetCountry, Integer p_noOfArmiesToPlace, Player p_player) {
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_noOfArmiesToPlace = p_noOfArmiesToPlace;
        this.d_player = p_player;
    }


    @Override
    public void execute(Player p_eachPlayer) {

    }
}
