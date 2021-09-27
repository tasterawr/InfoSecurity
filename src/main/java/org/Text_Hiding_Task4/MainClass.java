package org.Text_Hiding_Task4;

import java.io.File;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        SymbolProvider.loadSymbolMap();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input file path: ");
        String inputFilePath = scanner.nextLine();
        File file = new File(inputFilePath);
        System.out.println("Enter container text file path: ");
        String containerTextFilePath = scanner.nextLine();
        System.out.println("Enter output file path: ");
        String outputFilePath = scanner.nextLine();

        String binaryString = BinaryHider.readBinaryString(file);
        System.out.println("Binary string: " + binaryString);
        //System.out.println(Integer.toBinaryString(192));
        BinaryHider.hideString(binaryString, containerTextFilePath, outputFilePath);
    }
}