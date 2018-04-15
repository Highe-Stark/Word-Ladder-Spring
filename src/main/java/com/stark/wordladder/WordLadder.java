package com.stark.wordladder;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Stack;

public class WordLadder implements wordladderIntf {
    private HashSet<String> dict;
    private String beg;
    private String end;

    public String findLadder() {
        String notFound = "There is no word ladder between " + beg + " and " + end;
        if (beg.length() != end.length()) return notFound;
        if (beg.equals(end)) return beg;
        // stk for a stack of words
        Stack<String> stk = new Stack<String>();
        stk.push(beg);
        // ladderTree contains a queue of stack representing ladder
        LinkedList<Stack<String>> ladderTree = new LinkedList<Stack<String>>();
        ladderTree.add(stk);
        // visited contains words in the stacks
        HashSet<String> visited = new HashSet<String>();
        visited.add(beg);
        while (!ladderTree.isEmpty())
        {
            stk = ladderTree.poll();
            String last = stk.peek();
            int len = last.length();
            for ( int i = 0; i < len; i++)
            {
                StringBuilder now = new StringBuilder();
                now.append(last);
                for (char rp = 'a'; rp <= 'z'; rp++)
                {
                    now.setCharAt(i, rp);
                    String nows = now.toString();
                    if (!dict.contains(nows)) continue;
                    if (visited.contains(nows)) continue;
                    visited.add(nows);
                    if (nows.equals(end)) {
                        String res;
                        Stack<String> tmp = new Stack<String>();
                        while (!stk.empty()) {
                            tmp.push(stk.pop());
                        }
                        res = "";
                        while (!tmp.empty()) {
                            res += tmp.pop() + " -> ";
                        }
                        res += end;
                        return res;
                    }
                    Stack<String> stkcpy = new Stack<String>();
                    stkcpy	= (Stack<String>) stk.clone();
                    stkcpy.push(nows);
                    ladderTree.add(stkcpy);
                }
            }
        }
        return notFound;
    }

    public void setDict(HashSet<String> dict) {
        this.dict = dict;
    }

    public void setBeg(String beg) {
        this.beg = beg;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBeg() {
        return this.beg;
    }

    public String getEnd() {
        return this.end;
    }
}
