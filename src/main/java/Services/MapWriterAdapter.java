package Services;

import Models.CurrentState;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Map writer adapter.
 */
public class MapWriterAdapter {

    /**
     * The D conquest map file writer.
     */
    private ConquestMapFileWriter d_conquestMapFileWriter;

    /**
     * Instantiates a new Map writer adapter.
     *
     * @param p_conquestMapFileWriter the p conquest map file writer
     */
    public MapWriterAdapter(ConquestMapFileWriter p_conquestMapFileWriter){
        d_conquestMapFileWriter = p_conquestMapFileWriter;
    }

    /**
     * Parse map to file.
     *
     * @param p_currentState the p current state
     * @param p_writer       the p writer
     * @param p_mapFormat    the p map format
     * @throws IOException the io exception
     */
    public void parseMapToFile(CurrentState p_currentState, FileWriter p_writer, String p_mapFormat) throws IOException {
        d_conquestMapFileWriter.parseMapToFile(p_currentState, p_writer,p_mapFormat);
    }
}
