package org.Text_Hiding_Task4;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SymbolProvider {
    private static Map<String, String> symbolMap = new HashMap<String, String>();
    private static Map<Integer, String> codeMap = new HashMap<>();

    static {
        try {
            loadCodeMap();
            loadSymbolMap();
        }
        catch (UnsupportedEncodingException e){

        }
    }
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

    public static String getSymbolByCode(int code){
        return codeMap.get(code);
    }

    public static void loadCodeMap() throws UnsupportedEncodingException{
        int code = 192;
        for (int i = 1040; i<1072;i++){
            codeMap.put(code, String.valueOf((char)i));
            codeMap.put(code + 32, String.valueOf((char)(i + 32)));
            code++;
        }
    }
}
