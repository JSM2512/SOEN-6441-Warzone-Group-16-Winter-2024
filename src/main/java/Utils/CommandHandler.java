package Utils;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;

/**
 * The type Command handler.
 */
public class CommandHandler {
    /**
     * The D command.
     */
    String d_command;

    /**
     * Instantiates a new Command handler.
     *
     * @param p_command the p command
     */
    public CommandHandler(String p_command) {
        this.d_command = p_command;
    }

    /**
     * Get main command string.
     *
     * @return the string
     */
    public String getMainCommand(){
        String[] l_command = d_command.split(" ");
        return l_command[0];
    }

    /**
     * Gets list of operations.
     *
     * @return the list of operations
     */
    public List<Map<String, String>> getListOfOperations() {
        String l_mainCommand = this.getMainCommand();
        String l_remainingString = d_command.replace(l_mainCommand,"").trim();

        if(l_remainingString == null || l_remainingString.isEmpty()){
            return new ArrayList<Map<String,String>>();
        }

        if(!l_remainingString.contains("-")){
            l_remainingString = "-filename " + l_remainingString;
        }
        List<Map<String,String>> l_listOfOperations = new ArrayList<Map<String,String>>();
        String[] l_operations = l_remainingString.split("-");

        for(String l_singleOperation : l_operations){
            if(l_singleOperation.length()>1){
                l_listOfOperations.add(getOperationsMap(l_singleOperation));
            }
        }
        return l_listOfOperations;
    }

    /**
     * Gets operations map.
     *
     * @param p_singleOperation the p single operation
     * @return the operations map
     */
    private Map<String, String> getOperationsMap(String p_singleOperation) {
        Map<String,String> l_operationMap = new HashMap<String,String>();
        String[] l_splitSingleOperation = p_singleOperation.split(" ");
        String l_mainOperation = l_splitSingleOperation[0];
        l_operationMap.put("Operation",l_mainOperation);
        int l_remainingLength = l_splitSingleOperation.length-1;
        String l_remainingArguments = "";
        if(l_remainingLength>0){
            String[] l_remainingArgumentsList = Arrays.copyOfRange(l_splitSingleOperation, 1 , l_splitSingleOperation.length);
            l_remainingArguments = String.join(" ",l_remainingArgumentsList);
        }
        l_operationMap.put("Arguments",l_remainingArguments);
        return l_operationMap;
    }

    /**
     * Check required key boolean.
     *
     * @param l_arguments       the l arguments
     * @param l_singleOperation the l single operation
     * @return the boolean
     */
    public boolean checkRequiredKey(String l_arguments, Map<String, String> l_singleOperation) {
        if(l_singleOperation.containsKey((l_arguments)) && l_singleOperation.get(l_arguments) != null && !l_singleOperation.get(l_arguments).isEmpty()){
            return true;
        }
        return false;
    }
}
