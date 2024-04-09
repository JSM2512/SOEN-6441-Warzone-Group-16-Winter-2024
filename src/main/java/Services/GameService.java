package Services;

import Models.Phase;

import java.io.*;

/**
 * The type Game service.
 */
public class GameService{

    /**
     * Instantiates a new Game service.
     */
    public GameService() {
    }

    /**
     * Save game.
     *
     * @param p_currentPhase the p current phase
     * @param p_fileName     the p file name
     */
    public static void saveGame(Phase p_currentPhase, String p_fileName){
        try{
            FileOutputStream l_gameSaveFile = new FileOutputStream("src/main/maps/" + p_fileName);
            ObjectOutputStream l_gameSaveFileObjectStream = new ObjectOutputStream(l_gameSaveFile);
            l_gameSaveFileObjectStream.writeObject(p_currentPhase);
            l_gameSaveFileObjectStream.flush();
            l_gameSaveFileObjectStream.close();

        } catch (Exception l_e) {
            l_e.printStackTrace();
        }
    }

    /**
     * Load game phase.
     *
     * @param p_fileName the p file name
     * @return the phase
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static Phase loadGame(String p_fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream l_gameLoadFileObjectStream = new ObjectInputStream(new FileInputStream("src/main/maps/" + p_fileName));
        Phase l_phase = (Phase) l_gameLoadFileObjectStream.readObject();

        l_gameLoadFileObjectStream.close();
        return l_phase;
    }
}
