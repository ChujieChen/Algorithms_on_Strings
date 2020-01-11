package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class Trie {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();

        // write your code here
        // TODO by LCC on 01/10/2020
        // a single node root
        trie.add(new HashMap<Character, Integer>());
        for(String pattern: patterns) {
        	int currentNode = 0;
        	for(int i = 0; i < pattern.length(); ++i) {
        		char currentSymbol = pattern.charAt(i);
        		if(trie.get(currentNode).containsKey(currentSymbol)) {
        			currentNode = trie.get(currentNode).get(currentSymbol);
//        			if(currentSymbol == 'A') {
//        				System.out.println(currentNode);
//        			}
        		}
        		else {
        			trie.add(new HashMap<Character, Integer>());
        			int newNode = trie.size() - 1;
//        			if(newNode == 10) {
//        				System.out.println(currentNode);
//            			System.out.println(newNode);
//            			System.out.println(currentSymbol);
//        			}
        			trie.get(currentNode).put(currentSymbol, newNode);
        			currentNode = newNode;
        		}
        	}
        }
        

        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
