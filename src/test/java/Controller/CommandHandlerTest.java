package Controller;

import Models.CurrentState;
import Models.Map;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CommandHandlerTest {

    CurrentState d_currentState;
    MapController d_mapController;
    CommandHandler d_commandHandler;
    String d_mapName;
    Map d_map;

    @Before
    public void setup(){
        d_currentState = new CurrentState();
        d_mapController = new MapController();
        d_mapName = "test.map";
        d_map = d_mapController.loadMap(d_currentState, d_mapName);
    }

    @Test
    public void getMainCommand() {
        d_commandHandler = new CommandHandler("loadmap test.map");
        assertEquals("loadmap", d_commandHandler.getMainCommand());
        d_commandHandler = new CommandHandler("gameplayer -add Player1");
        assertEquals("gameplayer", d_commandHandler.getMainCommand());
        d_commandHandler = new CommandHandler("assigncountries");
        assertEquals("assigncountries",d_commandHandler.getMainCommand());
    }

    @Test
    public void getListOfOperations() {
        d_commandHandler = new CommandHandler("loadmap test.map");
        assertEquals("test.map", d_commandHandler.getListOfOperations().get(0).get("Arguments"));
        d_commandHandler = new CommandHandler("gameplayer -add Player1");
        assertEquals("Player1", d_commandHandler.getListOfOperations().get(0).get("Arguments"));
        assertEquals("add", d_commandHandler.getListOfOperations().get(0).get("Operation"));
    }

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