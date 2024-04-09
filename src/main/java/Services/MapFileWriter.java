package Services;

import Models.Continent;
import Models.Country;
import Models.CurrentState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Map file writer.
 */
public class MapFileWriter implements Serializable {

    /**
     * Parse map to file.
     *
     * @param p_currentState the p current state
     * @param p_writer       the p writer
     * @param p_mapFormat    the p map format
     * @throws IOException the io exception
     */
    public void parseMapToFile(CurrentState p_currentState, FileWriter p_writer, String p_mapFormat) throws IOException {
        if(p_currentState.getD_map().getD_mapContinents() != null && !p_currentState.getD_map().getD_mapContinents().isEmpty()){
            writeContinentMetaData(p_currentState, p_writer);
        }
        if(p_currentState.getD_map().getD_mapCountries() != null && !p_currentState.getD_map().getD_mapCountries().isEmpty()){
            writeCountryAndBorderMetaData(p_currentState, p_writer);
        }
    }

    /**
     * Write country and border meta data.
     *
     * @param p_currentState the p current state
     * @param p_writer       the p writer
     * @throws IOException the io exception
     */
    private void writeCountryAndBorderMetaData(CurrentState p_currentState, FileWriter p_writer) throws IOException {
        String l_countryMetadata = "";
        String l_borderMetadata = "";
        List<String> l_borderList = new ArrayList<>();

        p_writer.write(System.lineSeparator() + "[countries]" + System.lineSeparator());
        for (Country l_eachCountry : p_currentState.getD_map().getD_mapCountries()) {
            l_countryMetadata = "";
            l_countryMetadata = l_eachCountry.getD_countryID().toString() + " " + l_eachCountry.getD_countryName() + " " + l_eachCountry.getD_continentID().toString();
            p_writer.write(l_countryMetadata + System.lineSeparator());

            if (l_eachCountry.getD_neighbouringCountriesId() != null && !l_eachCountry.getD_neighbouringCountriesId().isEmpty()) {
                l_borderMetadata = "";
                l_borderMetadata = l_eachCountry.getD_countryID().toString();
                for (Integer l_eachBorder : l_eachCountry.getD_neighbouringCountriesId()) {
                    l_borderMetadata = l_borderMetadata + " " + l_eachBorder.toString();
                }
                l_borderList.add(l_borderMetadata);
            }
        }
        if (l_borderList != null && !l_borderList.isEmpty()) {
            p_writer.write(System.lineSeparator() + "[borders]" + System.lineSeparator());
            for (String l_eachBorder : l_borderList) {
                p_writer.write(l_eachBorder + System.lineSeparator());
            }
        }
    }

    /**
     * Write continent meta data.
     *
     * @param p_currentState the p current state
     * @param p_writer       the p writer
     * @throws IOException the io exception
     */
    private void writeContinentMetaData(CurrentState p_currentState, FileWriter p_writer) throws IOException {
        p_writer.write(System.lineSeparator() + "[continents]" + System.lineSeparator());
        for(Continent l_eachContinent : p_currentState.getD_map().getD_mapContinents()){
            p_writer.write(l_eachContinent.getD_continentName() + " " + l_eachContinent.getD_continentValue().toString() + System.lineSeparator());
        }

    }
}
