package org.Signature_File_Task1;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file path: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        System.out.println("Enter offset: ");
        int offset = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter directory path: ");
        String dirPath = scanner.nextLine();
        File directory = new File(dirPath);

        String sig = BinaryReader.readSignature(file, offset);

        System.out.println("\nDiscovered files:");
        List<String> filePaths = SigSearchClass.searchForFiles(directory, sig);
        for (String path : filePaths){
            System.out.println(path);
        }
    }
}
