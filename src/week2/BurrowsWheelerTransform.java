package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {
        StringBuilder result = new StringBuilder();
        
        // write your code here
        // TODO by LCC on 02/04/2020
        String[] M = new String[text.length()];
        for(int i = 0; i < M.length; ++i) {
        	M[i] = text.substring(i) + text.substring(0, i);  
        }
        Arrays.sort(M);
        for(int i = 0; i < M.length; ++i) {
        	result.append(M[i].charAt(M[i].length() - 1));
        }

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}
