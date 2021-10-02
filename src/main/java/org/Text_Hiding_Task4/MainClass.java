package org.Text_Hiding_Task4;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException{
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter 1 to hide string.");
            System.out.println("Enter 2 to extract string.");
            String input = scanner.nextLine();
            if (input.equals("1")){
                hide();
            }
            else if (input.equals("2")){
                extract();
            }
        }
    }

    public static void hide() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input file path: ");
        String inputFilePath = scanner.nextLine();
        File file = new File(inputFilePath);
        System.out.println("Enter container text file path: ");
        String containerTextFilePath = scanner.nextLine();
        System.out.println("Enter output file path: ");
        String outputFilePath = scanner.nextLine();

        String binaryString = BinaryHider.readBinaryString(file);
        BinaryHider.hideString(binaryString, containerTextFilePath, outputFilePath);
        System.out.println("The string is hidden successfully.\n");
    }

    public static void extract(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter container file path: ");
        String containerTextFilePath = scanner.nextLine();
        System.out.println(BinaryHider.extractHiddenString(containerTextFilePath) + "\n");
    }
}