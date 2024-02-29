package Models;

import Views.GameLogger;

import java.util.Observable;

public class ModelLogger extends Observable {
    String d_message;

    public ModelLogger(){
        GameLogger l_logger = new GameLogger();
        this.addObserver(l_logger);
    }

    public String getD_message() {
        return d_message;
    }

    public void setD_message(String p_message) {
        this.d_message = p_message;
    }
}
