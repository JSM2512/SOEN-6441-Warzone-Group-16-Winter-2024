package Models;

import java.util.ArrayList;
import java.util.List;

public class Country {
    Integer d_countryID;
    String d_countryName;
    Integer d_continentID;
    List<Integer> d_neighbouringCountriesId;

    public Country(Integer p_countryID, String p_countryName, Integer p_continentID) {
        this.d_countryID = p_countryID;
        this.d_countryName = p_countryName;
        this.d_continentID = p_continentID;
    }

    public Country(Integer p_countryID, Integer p_continentID) {
        this.d_countryID = p_countryID;
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
    public List<Integer> getD_neighbouringCountriesId() {
        if(d_neighbouringCountriesId == null) {
            return new ArrayList<Integer>();
        }
        else {
            return d_neighbouringCountriesId;
        }
    }

    public void setD_neighbouringCountriesId(List<Integer> d_neighbouringCountriesId) {
        this.d_neighbouringCountriesId = d_neighbouringCountriesId;
    }

    @Override
    public String toString() {
        return "Country{" +
                "d_countryID=" + d_countryID +
                ", d_countryName='" + d_countryName + '\'' +
                ", d_continentID=" + d_continentID +
                ", d_neighbouringCountriesId=" + d_neighbouringCountriesId +
                '}';
    }

    public void addNeighbours(int p_neighbourID) {
        if(d_neighbouringCountriesId == null){
            d_neighbouringCountriesId = new ArrayList<>();
            d_neighbouringCountriesId.add(p_neighbourID);
        }
        else{
            d_neighbouringCountriesId.add(p_neighbourID);
        }
    }

    public void removeNeighbours(int p_neighbourID) {
        if(d_neighbouringCountriesId == null){
            System.out.println("No neighouring countries are present.");
        }
        else{
            d_neighbouringCountriesId.remove(p_neighbourID);
        }
    }
}
