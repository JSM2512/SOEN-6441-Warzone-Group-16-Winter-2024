package Services;

import Models.CurrentState;
import Models.Map;

import java.util.List;

/**
 * The type Map reader adapter.
 */
public class MapReaderAdapter {

    /**
     * The D conquest map file reader.
     */
    private ConquestMapFileReader d_conquestMapFileReader;

    /**
     * Instantiates a new Map reader adapter.
     *
     * @param p_conquestMapFileReader the p conquest map file reader
     */
    public MapReaderAdapter(ConquestMapFileReader p_conquestMapFileReader) {
        this.d_conquestMapFileReader = p_conquestMapFileReader;
    }

    /**
     * Parse map file.
     *
     * @param p_currentState the p current state
     * @param p_map          the p map
     * @param p_fileLines    the p file lines
     * @param p_fileName     the p file name
     */
    public void parseMapFile(CurrentState p_currentState, Map p_map, List<String> p_fileLines, String p_fileName) {
        d_conquestMapFileReader.readConquestFile(p_currentState, p_map, p_fileLines, p_fileName);

    }
}
