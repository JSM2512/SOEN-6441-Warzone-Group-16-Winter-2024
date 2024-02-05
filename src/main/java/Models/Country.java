package Models;

public class Country {
    Integer d_countryID;
    String d_countryName;
    Integer d_continentID;

    public Country(Integer p_countryID, String p_countryName, Integer p_continentID) {
        this.d_countryID = p_countryID;
        this.d_countryName = p_countryName;
        this.d_continentID = p_continentID;
    }

    public Integer getD_countryID() {
        return d_countryID;
    }

    public void setD_countryID(Integer p_countryID) {
        this.d_countryID = p_countryID;
    }

    public String getD_countryName() {
        return d_countryName;
    }

    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    public Integer getD_continentID() {
        return d_continentID;
    }

    public void setD_continentID(Integer p_continentID) {
        this.d_continentID = p_continentID;
    }
}
