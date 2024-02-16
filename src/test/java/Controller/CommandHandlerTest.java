package Controller;

import Models.CurrentState;
import Models.Map;
import Utils.CommandHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * The type Command handler test.
 */
public class CommandHandlerTest {

    /**
     * The D current state.
     */
    CurrentState d_currentState;
    /**
     * The D map controller.
     */
    MapController d_mapController;
    /**
     * The D command handler.
     */
    CommandHandler d_commandHandler;
    /**
     * The D map name.
     */
    String d_mapName;
    /**
     * The D map.
     */
    Map d_map;

    /**
     * Setup.
     */
    @Before
    public void setup(){
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }

    /**
     * Gets main command.
     */
    @Test
    public void getMainCommand() {
        d_commandHandler = new CommandHandler("loadmap test.map");
        assertEquals("loadmap", d_commandHandler.getMainCommand());
        d_commandHandler = new CommandHandler("gameplayer -add Player1");
        assertEquals("gameplayer", d_commandHandler.getMainCommand());
        d_commandHandler = new CommandHandler("assigncountries");
        assertEquals("assigncountries",d_commandHandler.getMainCommand());
    }

    /**
     * Gets list of operations.
     */
    @Test
    public void getListOfOperations() {
        d_commandHandler = new CommandHandler("loadmap test.map");
        assertEquals("test.map", d_commandHandler.getListOfOperations().get(0).get("Arguments"));
        d_commandHandler = new CommandHandler("gameplayer -add Player1");
        assertEquals("Player1", d_commandHandler.getListOfOperations().get(0).get("Arguments"));
        assertEquals("add", d_commandHandler.getListOfOperations().get(0).get("Operation"));
    }

    /**
     * Check required key.
     */
    @Test
    public void checkRequiredKey() {
        d_commandHandler = new CommandHandler("editcountry -add India Africa");
        List<java.util.Map<String,String>> l_listOfOperation = d_commandHandler.getListOfOperations();
        assertTrue(d_commandHandler.checkRequiredKey("Arguments", l_listOfOperation.get(0)));
        assertTrue(d_commandHandler.checkRequiredKey("Operation", l_listOfOperation.get(0)));
        l_listOfOperation.get(0).remove("Operation");
        assertFalse(d_commandHandler.checkRequiredKey("Operation", l_listOfOperation.get(0)));
        l_listOfOperation.get(0).remove("Arguments");
        assertFalse(d_commandHandler.checkRequiredKey("Arguments", l_listOfOperation.get(0)));
    }
}