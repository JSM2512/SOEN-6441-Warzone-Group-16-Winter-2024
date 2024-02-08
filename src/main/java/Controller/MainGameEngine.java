package Controller;

import Models.CurrentState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MainGameEngine {

    MapController d_mapController=new MapController();
    CurrentState d_currentState=new CurrentState();

    public static void main(String... args){
        MainGameEngine l_mainGameEngine = new MainGameEngine();
        l_mainGameEngine.start();
    }

    private void start() {
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean l_flag = true;

        while(l_flag){
            System.out.println("================================== MAIN MENU ===================================");
            System.out.println("1. Initiate the map : (Use: 'loadmap filename'.)");
            System.out.println("2. Exit the game : (Use: 'exit'.)");
            System.out.println("3. Edit the Map : (Use: 'editmap filename')");
            System.out.println("");
            System.out.print("Enter the command : ");
            try{
                String l_inputCommand = l_bufferedReader.readLine();
                commandHandler(l_inputCommand);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }

    private void commandHandler(String p_inputCommand) throws Exception {
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        String l_mainCommand = l_commandHandler.getMainCommand();
        boolean l_mapAvilable = false;

        if(d_currentState.getD_map() != null){
            l_mapAvilable = true;
        }
        if(l_mainCommand.equals("loadmap")){
            loadMap(l_commandHandler);
        }
        else if(l_mainCommand.equals(("editmap"))){
            editMap(l_commandHandler);
        }
        else if(l_mainCommand.equals("editcountry")) {
            if (!l_mapAvilable) {
                System.out.println("Cannot edit Country as map is not available. Please run editmap command");
            } else {
                editCountry(l_commandHandler);
            }
        }
        else if(l_mainCommand.equals("editcontinent")){
            if(!l_mapAvilable){
                System.out.println("Map not available. Please use editmap command first.");
            }
            else{
                editContinent(l_commandHandler);
            }
        }
        else if (l_mainCommand.equals("validatemap")) {
            if(!l_mapAvilable){
                System.out.println("Map not available. Please use loadmap/editmap command first.");
            }
            else{
                validateMap(l_commandHandler);
            }
        }
        if("exit".equals(p_inputCommand)){
            System.out.println("Closing Game....");
            System.exit(0);
        }
    }

    private void validateMap(CommandHandler p_commandHandler) {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            Models.Map l_map = d_currentState.getD_map();
            if(l_map == null){
                System.out.println("Map not Found!");
            }
            else {
                if(l_map.validateMap()){
                    System.out.println("Map is Valid");
                }
                else{
                    System.out.println("Map is not Valid");
                }
            }
        }
        else {
            System.out.println("Validate map command is not correct. Use 'validatemap' command.");
        }
    }

    private void editContinent(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()){
            throw new Exception("Invalid command entered for editmap.");
        }
        else{
            for(Map<String,String> l_singleOperation : l_listOfOperations){
                if(l_singleOperation.containsKey("Operation") && l_singleOperation.get("Operation")!=null && l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null){
                    d_mapController.editContinent(d_currentState, l_singleOperation.get("Operation"), l_singleOperation.get("Arguments"));
                }
            }
        }
    }

    private void loadMap(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        System.out.println(l_listOfOperations);
        if(l_listOfOperations == null || l_listOfOperations.isEmpty()) {
            throw new Exception("Invalid Command for load map");
        }
        for(Map<String,String> l_singleOperation : l_listOfOperations) {
            if(l_singleOperation.containsKey("Arguments") && l_singleOperation.get("Arguments")!=null) {
                Models.Map l_map = d_mapController.loadMap(d_currentState,l_singleOperation.get("Arguments"));
                System.out.println(l_map);
                if(l_map.validateMap()){
                    System.out.println("Map is valid.");
                }
                else{
                    System.out.println("Map is not valid.");
                }
            }
        }
    }

    private void editMap(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        System.out.println(l_listOfOperations);
        if(l_listOfOperations == null || l_listOfOperations.isEmpty())
        {
            throw new Exception("Invalid Command for edit map");
        }
        else{
            for(Map<String, String> l_singleOperation : l_listOfOperations){
                if(p_commandHandler.checkRequiredKey("Arguments", l_singleOperation)){
                    d_mapController.editMap(d_currentState, l_singleOperation.get("Arguments"));
                }
                else{
                    throw new Exception("Invalid Command for edit map operation");
                }
            }
        }
    }

    private void editCountry(CommandHandler p_commandHandler) throws Exception {
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        if(l_listOfOperations.isEmpty())
        {
            throw new Exception("Invalid Command for edit Country");
        }
        else{
            for(Map<String, String> l_singleOperation: l_listOfOperations){
                if(p_commandHandler.checkRequiredKey("Arguments", l_singleOperation) && p_commandHandler.checkRequiredKey("Operation", l_singleOperation)){
                    d_mapController.editCountry(d_currentState, l_singleOperation.get("Operation"), l_singleOperation.get("Arguments"));
                }
            }
        }
    }
}
