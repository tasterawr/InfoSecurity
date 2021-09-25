package org.Signature_File_Task1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryReader {
    public static String readSignature(File f, int offset){
        List<Byte> byteList = new ArrayList<>();
        try{
            FileInputStream stream = new FileInputStream(f);

            if (offset != 0){
                for (int i = 0; i < offset; i++)
                    stream.read();
            }

            for (int i = 0; i < 16; i++){
                byteList.add((byte)stream.read());
            }

            String result = "";
            for (Byte b: byteList){
                result = result + b + " ";
            }

            return result;
        }
        catch (FileNotFoundException e) {
            System.out.println("FileInputStream exception");
        } catch (IOException e) {
            System.out.println("Read exception");
        }
        return null;
    }

    public static String readBytes(File f){
        List<Byte> byteList = new ArrayList<>();
        try{
            FileInputStream stream = new FileInputStream(f);

            while(stream.available() != 0){
                byteList.add((byte)stream.read());
            }

            String result = "";
            for (Byte b: byteList){
                result = result + b + " ";
            }

            return result;
        }
        catch (FileNotFoundException e) {
            System.out.println("FileInputStream exception");
        } catch (IOException e) {
            System.out.println("Read exception");
        }
        return null;
    }
}
