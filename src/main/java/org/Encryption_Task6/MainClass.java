package org.Encryption_Task6;

import java.io.File;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        VigenereEncryptor encryptor = new VigenereEncryptor("ea");
        //String dirPath = "C:\\Users\\LOKTEVS\\Downloads\\task4-20211011T160809Z-001\\task4\\dir";
        //String outputPath = "C:\\Users\\LOKTEVS\\Downloads\\task4-20211011T160809Z-001\\task4\\catalogue.enc";
        //String decryptPath = "C:\\Users\\LOKTEVS\\Downloads\\task4-20211011T160809Z-001\\task5";
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("1. Encrypt directory");
            System.out.println("2. Decrypt directory");
            String input = scanner.nextLine();
            if (input.equals("1")){
                System.out.println("Enter directory path: ");
                String dirPath = scanner.nextLine();
                System.out.println("Enter encrypted file path: ");
                String outputPath = scanner.nextLine();
                System.out.println("Enter key: ");
                String key = scanner.nextLine();

                encryptor.encrypt(dirPath, outputPath, key);
            }
            else if (input.equals("2")){
                System.out.println("Enter encrypted file path: ");
                String filePath = scanner.nextLine();
                System.out.println("Enter directory path: ");
                String dirPath = scanner.nextLine();
                System.out.println("Enter key: ");
                String key = scanner.nextLine();

                encryptor.decrypt(filePath, dirPath, key);
            }
        }
    }
}
