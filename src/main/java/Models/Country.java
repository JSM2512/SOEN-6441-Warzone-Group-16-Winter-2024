package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Model Class Country.
 */
public class Country {
    /**
     * The D country id.
     */
    Integer d_countryID;
    /**
     * The D country name.
     */
    String d_countryName;
    /**
     * The D continent id.
     */
    Integer d_continentID;
    /**
     * The D neighbouring countries id.
     */
    List<Integer> d_neighbouringCountriesId;
    /**
     * The D armies.
     */
    Integer d_armies = 0;

    /**
     * Instantiates a new Country.
     *
     * @param p_countryID   the p country id
     * @param p_countryName the p country name
     * @param p_continentID the p continent id
     */
    public Country(Integer p_countryID, String p_countryName, Integer p_continentID) {
        this.d_countryID = p_countryID;
        this.d_countryName = p_countryName;
        this.d_continentID = p_continentID;
    }

    /**
     * Instantiates a new Country.
     *
     * @param p_countryID   the p country id
     * @param p_continentID the p continent id
     */
    public Country(Integer p_countryID, Integer p_continentID) {
        this.d_countryID = p_countryID;
        this.d_continentID = p_continentID;
    }

    /**
     * Gets d country id.
     *
     * @return the d country id
     */
    public Integer getD_countryID() {
        return d_countryID;
    }

    /**
     * Sets d country id.
     *
     * @param p_countryID the p country id
     */
    public void setD_countryID(Integer p_countryID) {
        this.d_countryID = p_countryID;
    }

    /**
     * Gets d country name.
     *
     * @return the d country name
     */
    public String getD_countryName() {
        return d_countryName;
    }

    /**
     * Sets d country name.
     *
     * @param p_countryName the p country name
     */
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    /**
     * Gets d continent id.
     *
     * @return the d continent id
     */
    public Integer getD_continentID() {
        return d_continentID;
    }

    /**
     * Sets d continent id.
     *
     * @param p_continentID the p continent id
     */
    public void setD_continentID(Integer p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * Gets d armies.
     *
     * @return the d armies
     */
    public Integer getD_armies() {
        return d_armies;
    }

    /**
     * Sets d armies.
     *
     * @param p_armies the p armies
     */
    public void setD_armies(Integer p_armies) {
        this.d_armies = p_armies;
    }

    /**
     * Gets d neighbouring countries id.
     *
     * @return the d neighbouring countries id
     */
    public List<Integer> getD_neighbouringCountriesId() {
        if(d_neighbouringCountriesId == null) {
            return new ArrayList<Integer>();
        }
        else {
            return d_neighbouringCountriesId;
        }
    }

    /**
     * Sets d neighbouring countries id.
     *
     * @param d_neighbouringCountriesId the d neighbouring countries id
     */
    public void setD_neighbouringCountriesId(List<Integer> d_neighbouringCountriesId) {
        this.d_neighbouringCountriesId = d_neighbouringCountriesId;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Country{" +
                "d_countryID=" + d_countryID +
                ", d_countryName='" + d_countryName + '\'' +
                ", d_continentID=" + d_continentID +
                ", d_neighbouringCountriesId=" + d_neighbouringCountriesId +
                '}';
    }

    /**
     * Add country neighbour.
     *
     * @param p_neighbourID the p neighbour id
     */
    public void addCountryNeighbour(int p_neighbourID) {
        if(d_neighbouringCountriesId == null){
            d_neighbouringCountriesId = new ArrayList<>();
            d_neighbouringCountriesId.add(p_neighbourID);
        }
        else{
            if(!d_neighbouringCountriesId.contains(p_neighbourID)) {
                d_neighbouringCountriesId.add(p_neighbourID);
            }
            else {
                System.out.println("Neighbour already exists.");
            }
        }
    }

    /**
     * Remove country neighbour if present.
     *
     * @param p_removeCountryId the p remove country id
     */
    public void removeCountryNeighbourIfPresent(int p_removeCountryId) {
        if(d_neighbouringCountriesId == null){
            System.out.println("No neighbouring countries present.");
        }
        else{
            if(d_neighbouringCountriesId.contains(p_removeCountryId)){
                for(int i=0; i<d_neighbouringCountriesId.size(); i++){
                    if(d_neighbouringCountriesId.get(i) == p_removeCountryId){
                        d_neighbouringCountriesId.remove(i);
                    }
                }
            }
            else {
                System.out.println("Country ID : "+p_removeCountryId+" is not a neighbour in the first place.");
            }
        }
    }
}
