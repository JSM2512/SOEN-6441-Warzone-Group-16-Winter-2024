package Services;

import Models.CurrentState;

import java.io.FileWriter;
import java.io.IOException;

public class MapWriterAdapter {

    private ConquestMapFileWriter d_conquestMapFileWriter;

    public MapWriterAdapter(ConquestMapFileWriter p_conquestMapFileWriter){
        d_conquestMapFileWriter = p_conquestMapFileWriter;
    }

    public void parseMapToFile(CurrentState p_currentState, FileWriter p_writer, String p_mapFormat) throws IOException {
        d_conquestMapFileWriter.parseMapToFile(p_currentState, p_writer,p_mapFormat);
    }
}
