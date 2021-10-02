package org.Signature_File_Task1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SigSearchClass {
    public static List<String> searchForFiles(File directory, String signature){
        List<String> filePaths = new ArrayList<String>();
        File[] files = directory.listFiles();

        for (File file : files){
            if (file.isDirectory()){
                List<String> subDirFilePaths = searchForFiles(file, signature);

                for (String filePath : subDirFilePaths){
                    filePaths.add(filePath);
                }
            }
            else{
                String byteString = BinaryReader.readBytes(file);
                if (byteString.contains(signature)){
                    filePaths.add(directory.getPath() + "\\" + file.getName());
                }
            }
        }

        return filePaths;
    }
}
