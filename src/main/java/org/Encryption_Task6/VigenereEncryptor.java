package org.Encryption_Task6;

import org.Signature_File_Task1.BinaryReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VigenereEncryptor {
    private List<String> alphabet;
    private String [][] alphabetTable;

    public VigenereEncryptor(String alphabetPath){
        loadAlphabetTable(alphabetPath);
    }

    public void encrypt(String directoryPath, String outputFilePath, String key){
        StringBuilder directoryString = new StringBuilder();
        readCatalogue(new File(directoryPath), "", directoryString); //чистый текст каталога
        String fullKey = makeFullKey(directoryString.length(), key); //растянутый ключ
        String encryptedText = "";

        for (int i = 0; i < directoryString.length(); i++){
            char textSymbol = directoryString.charAt(i);
            String keySymbol = String.valueOf(fullKey.charAt(i));
            if (Character.isLetter(textSymbol)){
                encryptedText = encryptedText + alphabetTable[alphabet.indexOf(String.valueOf(textSymbol))][alphabet.indexOf(keySymbol)];
            }
            else{
                encryptedText = encryptedText + textSymbol;
            }
        }

        writeToFile(outputFilePath, encryptedText);
        deleteDirectory(directoryPath);
    }

    public void decrypt(String inputFilePath, String dirOutputPath, String key){
        String decryptedText = decryptFileText(inputFilePath, key);
        System.out.println(decryptedText);

        String [] directories = decryptedText.split("DIV");
        for (String dir : directories){
            createDirectory(dirOutputPath, dir);
        }
    }

    private void createDirectory(String dirOutputPath, String directory){
        String resultPath = dirOutputPath;
        String [] dirParts = directory.split("&");
        for (int i = 0; i < dirParts.length; i++){
            if (fileType(dirParts[i]).equals("folder")){
                new File(resultPath + "\\" + getFileName(dirParts[i])).mkdirs();
                resultPath = resultPath + "\\" + getFileName(dirParts[i]);
            }
            else if (fileType(dirParts[i]).equals("file")){
                new File(resultPath + "\\" + getFileName(dirParts[i]));
                writeToFile(resultPath + "\\" + getFileName(dirParts[i]), dirParts[i+1]);
            }
        }
    }

    private void readCatalogue(File directory, String currentCat, StringBuilder directoryString){
        currentCat = currentCat + directory.getName() + "/";
        if (!directoryString.isEmpty()){
            directoryString.append("DIV");
        }

        for (String dirName : currentCat.split("/")){
            directoryString.append("FOL" + dirName + "&");
        }

        List<String> filePaths = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null){
            for (File file : files){
                if (file.isFile()){
                    directoryString.append("FIL" + file.getName() + "&" + readFile(file.getAbsolutePath()) + "&");
                }
            }
            for (File file : files){
                if (file.isDirectory()){
                    readCatalogue(file, currentCat, directoryString);
                }
            }
        }
    }

    private String readFile(String stringPath){
        Path path = Paths.get(stringPath);
        try {
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "";
    }

    private void writeToFile(String filePath, String input){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            for (String symbol : Arrays.asList(input)){
                writer.append(symbol);
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private String makeFullKey(int length, String key){
        StringBuilder result = new StringBuilder(key);
        while (result.length() < length){
            result.append(key);
        }

        return result.substring(0, length).toString();
    }

    private String decryptFileText(String inputFilePath, String key){
        StringBuilder result = new StringBuilder();
        try {
            String encryptedText = new String(Files.readAllBytes(Paths.get(inputFilePath)), StandardCharsets.UTF_8);
            String fullKey = makeFullKey(encryptedText.length(), key);

            for (int i = 0; i < fullKey.length(); i++){
                int index = alphabet.indexOf(String.valueOf(fullKey.charAt(i)));
                if (!Character.isLetter(encryptedText.charAt(i))){
                    result.append(encryptedText.charAt(i));
                    continue;
                }
                for (int j = 0; j < alphabet.size(); j++){
                    if (alphabetTable[index][j].equals(String.valueOf(encryptedText.charAt(i)))){
                        result.append(alphabet.get(j));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return result.toString();
    }

    private String fileType(String input){
        if (input.substring(0, 3).equals("FOL"))
            return "folder";
        else if (input.substring(0, 3).equals("FIL"))
            return "file";
        else
            return "";
    }

    private String getFileName(String input){
        return input.substring(3, input.length());
    }

    private void deleteDirectory(String dirPath){
        File directory = new File(dirPath);
        File [] files = directory.listFiles();

        for (File f : files){
            if (f.isFile())
                f.delete();
        }
        for (File f : files){
            if (f.isDirectory())
                deleteDirectory(f.getPath());
        }

        directory.delete();
    }

    private void loadAlphabetTable(String alphabetFilePath){
        alphabetFilePath = "src/main/resources/task6_resources/alphabet.txt";
        String [] textSymbols = new String[0];
        try{
            Path path = Paths.get(alphabetFilePath);
            textSymbols = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).split("");
        }
        catch (IOException e){
            System.out.println("Exception: " + e.getMessage());
        }

        alphabet = Arrays.asList(textSymbols);
        alphabetTable = new String[alphabet.size()][alphabet.size()];
        
        int n = alphabet.size();
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<String> temp = new ArrayList<>();
            temp.addAll(alphabet.subList(offset, alphabet.size()));
            temp.addAll(alphabet.subList(0, offset));
            temp.toArray(alphabetTable[i]);
            offset++;
        }
    }
}
