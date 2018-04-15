package com.stark.wordladder;

import java.io.IOException;
import java.util.HashSet;

public class WordLadderService implements WordLadderServiceIntf{
    String dictPath;

    public WordLadderService(String dictPath) {
        this.dictPath = dictPath;
    }
    public void setDictPath(String dictPath) {
        this.dictPath = dictPath;
    }
    public String getDictPath() {
        return dictPath;
    }
    public String service(String beg, String end) throws IOException
    {
        FetchDict fetch = new FetchDict();
        HashSet<String> dict = fetch.fetchDict(dictPath);
        WordLadder wl = new WordLadder();
        wl.setDict(dict);
        wl.setBeg(beg);
        wl.setEnd(end);
        return wl.findLadder();
    }
}
