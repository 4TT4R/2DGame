package com.ATTAR.defaultes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader {





    public static String ReadFile(String source, String FileName){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new java.io.FileReader(new File(source + FileName)));
            String line;
            while((line = br.readLine()) != null) {
                string.append(line).append(" \r\n");

            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return string.toString();
    }
    public static StringBuilder ReadFileSB(String source, String FileName){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new java.io.FileReader(new File(source + FileName)));
            String line;
            while((line = br.readLine()) != null) {
                string.append(line).append(" \r\n");

            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return string;
    }
}
