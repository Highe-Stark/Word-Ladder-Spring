package com.stark.wordladder;

import org.springframework.core.io.ClassPathResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class FetchDict implements FetchDictIntf{
    public HashSet<String> fetchDict(String dictPath) throws IOException
    {
        HashSet<String> dict = new HashSet<String>();
        File dictFile;
        try {
            dictFile = new ClassPathResource("static/" + dictPath).getFile();
        }
        catch (IOException ex) {
            throw new IOException("IOException : File " + dictPath + " not Found!");
        }
        Scanner scanner;
        try {
            scanner = new Scanner(dictFile, "UTF-8");
        }
        catch (FileNotFoundException ex) {
            // System.out.println("Error: File " + dictPath + " not Found!");
            throw new FileNotFoundException("FileNotFoundException: File " + dictPath + " not Found!");
        }
        while (scanner.hasNext()) {
            dict.add(scanner.next());
        }
        return dict;
    }
}
