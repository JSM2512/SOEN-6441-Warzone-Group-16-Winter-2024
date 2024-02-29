package Views;

import Models.ModelLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameLogger implements Observer {

    ModelLogger d_modelLogger;

    @Override
    public void update(Observable p_observable, Object p_arg) {
        d_modelLogger = (ModelLogger) p_observable;
        File l_loggerFile = new File("GameLogs.txt");
        String l_logMessage = d_modelLogger.getD_message();

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(l_loggerFile, true));
            writer.write(l_logMessage);
            writer.newLine();
            writer.close();
        }
        catch (IOException p_e) {
            p_e.printStackTrace();
        }
    }
}
