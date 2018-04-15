package com.stark.wordladder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public interface FetchDictIntf {
    public HashSet<String> fetchDict(String dictPath) throws IOException;
}
