package Controller;

import Models.CurrentState;
import Models.Map;

import java.io.File;


public class MapController {

    public void loadMap(CurrentState p_currentState, String p_arguments) {
        Map l_map=new Map();
        loadFile(p_arguments);

    }

    private void loadFile(String p_fileName) {
        String l_fileLocation = getFilePath(p_fileName);
        System.out.println(l_fileLocation);
    }

    private String getFilePath(String p_fileName) {
        String l_absolutePath=new File("").getAbsolutePath();
        l_absolutePath= l_absolutePath + File.separator + "src" + File.separator + "main" + File.separator + "maps" + File.separator + p_fileName;
        return l_absolutePath;
    }




}
