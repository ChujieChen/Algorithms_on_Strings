package week1;

import java.io.*;
import java.util.*;

class Node
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];

	Node ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
	}
}

public class TrieMatching implements Runnable {
	// TODO by LCC on 01/11/2020
	// copied from Trie.java
	// changed return type, input type
	List<Node> buildTrie(List<String> patterns) {
		List<Node> trie = new ArrayList<>();
        trie.add(new Node());
        for(String pattern: patterns) {
        	int currentNode = 0;
        	for(int i = 0; i < pattern.length(); ++i) {
        		char currentSymbol = pattern.charAt(i);
        		int currentSymbolInt = letterToIndex(currentSymbol);
        		// changed the way to see if there's a match
        		if(trie.get(currentNode).next[currentSymbolInt] != Node.NA) {
        			currentNode = trie.get(currentNode).next[currentSymbolInt];
        		}
        		else {
        			trie.add(new Node());
        			int newNode = trie.size() - 1;
        			trie.get(currentNode).next[currentSymbolInt] = newNode;
        			currentNode = newNode;
        		}
        	}
        }
        return trie;
    }
	int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			default: assert (false); return Node.NA;
		}
	}

	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();

		// write your code here
		// TODO by LCC on 01/11/2020
		List<Node> trie = buildTrie(patterns);
		for(int i = 0; i < text.length(); ++i) {
			boolean isMathced = prefixTrieMatching(text.substring(i), trie);
			if(isMathced) {
//				System.out.println(i);
				result.add(i);
			}
		}
		
		return result;
	}
	// TODO by LCC on 01/11/2020
	private boolean prefixTrieMatching(String text, List<Node> trie) {
		// TODO Auto-generated method stub
		
		int idxInText = 0;
		char symbol = text.charAt(idxInText);
		int symbolInt = letterToIndex(symbol);
		Node v = trie.get(0);
		while(true) {
			if(isLeaf(v)) return true;
			else if(v.next[symbolInt] != Node.NA) {
				// TODO have to put this line before all updates
				// TODO this is important
				// otherwise the last matching index will not be added into result
				// for idxInText >= text.length() would be true before isLeaf(v) be true
				v = trie.get(v.next[symbolInt]);
				if(isLeaf(v)) return true;
				
				++idxInText;
				if(idxInText >= text.length()) return false;
				symbolInt = letterToIndex(text.charAt(idxInText));
				
			}
			else {
//				System.out.println("no mathces found");
				return false;
			}
		}
	}
	private boolean isLeaf(Node v) {
		// TODO Auto-generated method stub
		for(int val: v.next) {
			if(val != Node.NA) return false;
		}
		return true;
	}
	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);

			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatching ()).start ();
	}
}
