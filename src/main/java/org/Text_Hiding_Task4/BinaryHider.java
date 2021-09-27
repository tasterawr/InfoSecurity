package org.Text_Hiding_Task4;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryHider {
    //читает строку из файла и преобразует в двоичный код
    public static String readBinaryString(File file){
        String fullBinString = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);
            List<Integer> byteList = new ArrayList<Integer>();
            int value;
            while((value = isr.read()) != -1){
                byteList.add(value);
            }

            for (int number : byteList){
                String binaryString = Integer.toBinaryString(number);
                fullBinString = String.format("%8s", binaryString).replace(' ', '0');
            }
        }
        catch (IOException e){
            System.out.printf("Exc: " + e.getMessage());
        }
        return fullBinString;
    }

    public static void hideString(String inputString, String containerTextFilePath, String outputFilePath){
        Path path = Paths.get(containerTextFilePath);
        String [] textSymbols = new String[0];
        try{
            textSymbols = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).split("");
        }
        catch (IOException e){
            System.out.println("Exception: " + e.getMessage());
        }

        char[] bits = inputString.toCharArray();
        List<String> textSymbolsList = Arrays.asList(textSymbols);

        for (int i = 0; i< bits.length; i++){
            if (bits[i] == '1'){
                String symbol = textSymbolsList.get(i);
                textSymbolsList.set(i, SymbolProvider.getEnglishSymbol(symbol));
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            for (String symb : textSymbolsList){
                writer.write(symb);
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("Exception: " + e.getMessage());
        }

    }
}
