package org.Text_Hiding_Task4;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SymbolProvider {
    private static Map<String, String> symbolMap = new HashMap<String, String>();

    public static String getRussianSymbol(String engSymb){
        for (String key : symbolMap.keySet()){
            if (symbolMap.get(key).equals(engSymb)){
                return key;
            }
        }
        return "";
    }

    public static String getEnglishSymbol(String rusSymb){
        return symbolMap.get(rusSymb);
    }

    public static void loadSymbolMap(){
        File symbolMapFile = new File("src/main/resources/task4_resources/symbol_map.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(symbolMapFile));
            String currentString;

            while ((currentString = reader.readLine()) != null){
                String[] symbols = currentString.split(" ");
                symbolMap.put(symbols[0], symbols[1]);
            }
        }
        catch (IOException e){
            System.out.printf("Exc: " + e.getMessage());
        }
    }
}
