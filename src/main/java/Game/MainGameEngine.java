package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MainGameEngine {

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
            System.out.println("");
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

    private void commandHandler(String p_inputCommand){
        CommandHandler l_commandHandler = new CommandHandler(p_inputCommand);
        String l_mainCommand = l_commandHandler.getMainCommand();

        if(l_mainCommand.equals("loadmap")){
            loadMap(l_commandHandler);
        }
        if("exit".equals(p_inputCommand)){
            System.out.println("Closing Game....");
            System.exit(0);
        }
    }

    private void loadMap(CommandHandler p_commandHandler){
        List<Map<String,String>> l_listOfOperations = p_commandHandler.getListOfOperations();
        System.out.println(l_listOfOperations);

    }
}
