package org.Text_Hiding_Task4;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BinaryHider {
    //читает строку из файла и преобразует в двоичный код
    public static String readBinaryString(File file) throws IOException{
        String fullBinString = "";
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);
        List<Integer> byteList = new ArrayList<Integer>();
        int value;
        while((value = isr.read()) != -1){
            byteList.add(value);
        }

        for (int number : byteList) {
            String stringWithZeroes = String.format("%8s", Integer.toBinaryString(number)).replace(' ', '0');
            fullBinString = fullBinString + stringWithZeroes;
        }

        return fullBinString;
    }

    public static void hideString(String inputString, String containerTextFilePath, String outputFilePath) throws IOException{
        String [] textSymbols = new String(Files.readAllBytes(Paths.get(containerTextFilePath)), StandardCharsets.UTF_8).split("");
        char[] bits = inputString.toCharArray();
        List<String> textSymbolsList = Arrays.asList(textSymbols);

        int bitIndex = 0;
        for (int i = 0; i< textSymbolsList.size(); i++){
            String symbol = textSymbolsList.get(i);
            if (SymbolProvider.getEnglishSymbol(symbol) != null && bits[bitIndex] == '1'){
                textSymbolsList.set(i, SymbolProvider.getEnglishSymbol(symbol));
                bitIndex++;
            }
            else if (SymbolProvider.getEnglishSymbol(symbol) != null && bits[bitIndex] == '0'){
                bitIndex++;
            }

            if (bitIndex == bits.length){
                break;
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        for (String symb : textSymbolsList){
            writer.append(symb);
        }
        writer.close();
    }

    public static String extractHiddenString(String containerFilePath){
        StringBuilder binaryString = new StringBuilder();
        String [] textSymbols = new String[0];
        try{
            Path path = Paths.get(containerFilePath);
            textSymbols = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).split("");
        }
        catch (IOException e){
            System.out.println("Exception: " + e.getMessage());
        }

        List<String> textSymbolsList = Arrays.asList(textSymbols);
        String resultBinaryString = "";
        int emptyCtr = 0;
        for (String s : textSymbolsList){
            if (emptyCtr == 8){
                resultBinaryString = binaryString.substring(0, binaryString.length()-8);
                break;
            }

            if (!"".equals(SymbolProvider.getRussianSymbol(s))){
                binaryString.append("1");
                emptyCtr = 0;
            }
            else if (SymbolProvider.getEnglishSymbol(s) != null){
                binaryString.append("0");
                emptyCtr++;
            }
        }

        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < resultBinaryString.length(); i+=8){
            if (i + 8 > binaryString.length())
                break;
            int code = Integer.parseInt(binaryString.substring(i, i+8), 2);
            String symb = "";
            if (code >= 192 && code <= 257)
                symb = SymbolProvider.getSymbolByCode(code);
            else
                symb = String.valueOf((char)code);
            resultString.append(symb);
        }
        return resultString.toString();
    }


}
