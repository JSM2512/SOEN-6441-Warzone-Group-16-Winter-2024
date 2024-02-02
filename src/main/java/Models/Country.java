package Models;

public class Country {
    Integer d_countryID;
    String d_countryName;
    Integer d_continentID;

    public Country(Integer d_countryID, String d_countryName, Integer d_continentID) {
        this.d_countryID = d_countryID;
        this.d_countryName = d_countryName;
        this.d_continentID = d_continentID;
    }

    public Integer getD_countryID() {
        return d_countryID;
    }

    public void setD_countryID(Integer d_countryID) {
        this.d_countryID = d_countryID;
    }

    public String getD_countryName() {
        return d_countryName;
    }

    public void setD_countryName(String d_countryName) {
        this.d_countryName = d_countryName;
    }

    public Integer getD_continentID() {
        return d_continentID;
    }

    public void setD_continentID(Integer d_continentID) {
        this.d_continentID = d_continentID;
    }
}
