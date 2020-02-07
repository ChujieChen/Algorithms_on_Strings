package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
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

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();
        
        // write your code here
        // TODO by LCC on 02/04/2020
        int[] preCnt = new int[256];
        for(int i = 0; i < bwt.length(); ++i) {
        	preCnt[bwt.charAt(i)]++;
        }
        for(int i = 1; i < preCnt.length; ++i) {
        	preCnt[i] += preCnt[i - 1];
        }
        // L's i-th char to F's j-th
        // Store i-j pair using arr Pair[i] = j
        int[] Pair = new int[bwt.length()];
        for(int i = 0; i < bwt.length(); ++i) {
        	Pair[i] = preCnt[bwt.charAt(i) - 1];
        	preCnt[bwt.charAt(i) - 1]++;
        }
        // reconstruct text
        StringBuilder text = new StringBuilder();
        for(int i = 0, pos = 0; i < bwt.length() - 1; ++i) {
        	text.append(bwt.charAt(pos));
        	pos = Pair[pos];
        }
        text = text.reverse();
        result = text.append("$");
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
