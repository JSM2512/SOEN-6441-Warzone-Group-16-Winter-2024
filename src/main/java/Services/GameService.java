package Services;

import Models.Phase;

import java.io.*;

public class GameService{

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

    public static Phase loadGame(String p_fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream l_gameLoadFileObjectStream = new ObjectInputStream(new FileInputStream("src/main/maps/" + p_fileName));
        Phase l_phase = (Phase) l_gameLoadFileObjectStream.readObject();

        l_gameLoadFileObjectStream.close();
        return l_phase;
    }
}
