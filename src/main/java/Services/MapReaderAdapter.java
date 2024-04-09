package Services;

import Models.CurrentState;
import Models.Map;

import java.util.List;

public class MapReaderAdapter {

    private ConquestMapFileReader d_conquestMapFileReader;
    public MapReaderAdapter(ConquestMapFileReader p_conquestMapFileReader) {
        this.d_conquestMapFileReader = p_conquestMapFileReader;
    }

    public void parseMapFile(CurrentState p_currentState, Map p_map, List<String> p_fileLines, String p_fileName) {
        d_conquestMapFileReader.readConquestFile(p_currentState, p_map, p_fileLines, p_fileName);

    }
}
