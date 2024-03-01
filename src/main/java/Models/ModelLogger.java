package Models;

import Views.GameLogger;

import java.util.Observable;

/**
 * The type Model logger.
 */
public class ModelLogger extends Observable {
    /**
     * The D message.
     */
    String d_message;

    /**
     * Instantiates a new Model logger.
     */
    public ModelLogger(){
        GameLogger l_logger = new GameLogger();
        this.addObserver(l_logger);
    }

    /**
     * Gets d message.
     *
     * @return the d message
     */
    public String getD_message() {
        return d_message;
    }

    /**
     * Sets d message.
     *
     * @param p_message     the p message
     * @param p_messageType the p message type
     */
    public void setD_message(String p_message, String p_messageType) {
        this.d_message = p_message;
        setChanged();
        notifyObservers();

    }
}
