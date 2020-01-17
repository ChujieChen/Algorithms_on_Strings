package week1;

import java.io.*;
import java.util.*;

// TODO change class name from Node to NodeExtended
class NodeExtended
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];
	public boolean patternEnd;

	NodeExtended ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
		patternEnd = false;
	}
}

public class TrieMatchingExtended implements Runnable {
	int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			default: assert (false); return NodeExtended.NA;
		}
	}
	// TODO by LCC on 01/16/2020
	List<NodeExtended> buildTrie(List<String> patterns) {
		List<NodeExtended> trie = new ArrayList<>();
        trie.add(new NodeExtended());
        for(String pattern: patterns) {
//        	System.out.println(pattern);
        	int currentNode = 0;
        	for(int i = 0; i < pattern.length(); ++i) {
        		char currentSymbol = pattern.charAt(i);
        		int currentSymbolInt = letterToIndex(currentSymbol);
        		// changed the way to see if there's a match
        		if(trie.get(currentNode).next[currentSymbolInt] != NodeExtended.NA) {
        			currentNode = trie.get(currentNode).next[currentSymbolInt];
        		}
        		else {
        			trie.add(new NodeExtended());
        			int newNode = trie.size() - 1;
        			trie.get(currentNode).next[currentSymbolInt] = newNode;
        			currentNode = newNode;
        		}
        		// TODO set end flag
        		if(i == pattern.length() - 1) {
        			trie.get(currentNode).patternEnd = true;
        		}
        	}
        }
        return trie;
    }
	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();

		// write your code here
		// TODO by LCC on 01/16/2020
		List<NodeExtended> trie = buildTrie(patterns);
		for(int i = 0; i < text.length(); ++i) {
			boolean isMathced = prefixTrieMatching(text.substring(i), trie);
			if(isMathced) {
//				System.out.println(i);
				result.add(i);
			}
		}
		
		
		return result;
	}
	
	// TODO by LCC on 01/16/2020
	private boolean prefixTrieMatching(String text, List<NodeExtended> trie) {
		// TODO Auto-generated method stub
		
		int idxInText = 0;
		char symbol = text.charAt(idxInText);
		int symbolInt = letterToIndex(symbol);
		NodeExtended v = trie.get(0);
		while(true) {
			if(v.patternEnd) return true;
			else if(v.next[symbolInt] != NodeExtended.NA) {
				// TODO have to put this line before all updates
				// TODO this is important
				// otherwise the last matching index will not be added into result
				// for idxInText >= text.length() would be true before isLeaf(v) be true
				v = trie.get(v.next[symbolInt]);
				if(v.patternEnd) return true;
				
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
//	private boolean isLeaf(NodeExtended v) {
//		// TODO Auto-generated method stub
//		for(int val: v.next) {
//			if(val != NodeExtended.NA) return false;
//		}
//		return true;
//	}
	
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
		new Thread (new TrieMatchingExtended ()).start ();
	}
}
