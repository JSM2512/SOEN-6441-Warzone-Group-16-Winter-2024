package Controller;

import Models.CurrentState;
import Models.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MapController {

    public void loadMap(CurrentState p_currentState, String p_arguments) {
        Map l_map=new Map();
        List<String> l_fileLines = loadFile(p_arguments);
        System.out.println(l_fileLines);
        if(!l_fileLines.isEmpty()) {
            List<String> l_continentData = getContinentData(l_fileLines);
            List<String> l_countryData = getCountryData(l_fileLines);
            List<String> l_borderData =getBorderData(l_fileLines);
            System.out.println("Continents Data = " + l_continentData);
            System.out.println("Country Data = " + l_countryData);
            System.out.println("Border Data = " + l_borderData);
        }

    }

    private List<String> getContinentData(List<String> p_fileLines) {
        List<String> l_continentData = p_fileLines.subList(p_fileLines.indexOf("[continents]") + 1, p_fileLines.indexOf("[countries]") - 1);
        return l_continentData;
    }

    private List<String> getCountryData(List<String> p_fileLines) {
        List<String> l_countryData = p_fileLines.subList(p_fileLines.indexOf("[countries]") + 1, p_fileLines.indexOf("[borders]") - 1);
        return l_countryData;
    }

    private List<String> getBorderData(List<String> p_fileLines) {
        List<String> l_borderData = p_fileLines.subList(p_fileLines.indexOf("[borders]") + 1, p_fileLines.size());
        return  l_borderData;
    }

    private List<String> loadFile(String p_fileName) {
        String l_fileLocation = getFilePath(p_fileName);
        List<String> l_fileLines = new ArrayList<>();
        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(l_fileLocation));
            l_fileLines = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (FileNotFoundException l_e) {
            System.out.println("File not Found!");
        } catch (IOException l_e) {
            System.out.println("File not Found or corrupted file!");
        }
        return l_fileLines;
    }

    private String getFilePath(String p_fileName) {
        String l_absolutePath=new File("").getAbsolutePath();
        l_absolutePath= l_absolutePath + File.separator + "src" + File.separator + "main" + File.separator + "maps" + File.separator + p_fileName;
        return l_absolutePath;
    }




}
