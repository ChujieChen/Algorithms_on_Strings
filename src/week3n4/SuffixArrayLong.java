package week3n4;

import java.util.*;
import java.io.*;


public class SuffixArrayLong {
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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text) {
        int[] result = new int[text.length()];

        // write your code here
        // TODO: by LCC on 03/04/2020
        // ref: https://d18ky98rnyall9.cloudfront.net/_dda59cdc75799bf5624931924ff69f86_14_algorithmic_challenges_2_suffix_array.pdf?Expires=1583539200&Signature=d~GKcj69N9BzsSntuDq0z7rQ6UU~uSI0rAh4xT-NAkt9PeynP27dUOWEVbj-mU-R-eIh-S4KcPCSVAmfqmqWu6MvgGb5RRQddwwp9pRVCxU8LuRtvJbNZde7j2PG~B8eJKPzYSgrDbufKZKbMa~iJDSfTNzGR03gqo1azIHLTyA_&Key-Pair-Id=APKAJLTNE6QMUY6HBC5A
        int[] order = SortCharacters(text);
//        print(order);
        int[] classes = ComputeCharClasses(text, order);
//        print(classes);
        int L = 1;
        while (L < text.length()) {
			order = SortDoubled(text, L, order, classes);
//			print(order);
			classes = UpdateClasses(order, classes, L);
//			print(classes);
			L = 2 * L;
		}
        return order;
    }
    // TODO
    private int letterToIndex (char letter)
	{
		switch (letter)
		{
			case '$': return 0;
			case 'A': return 1;
			case 'C': return 2;
			case 'G': return 3;
			case 'T': return 4;
			default: assert (false);
		}
		return -99;
	}
    // TODO: by LCC on 03/04/2020
    // Counting sort
    private int[] SortCharacters(String S) {
    	int[] order = new int[S.length()];
    	int[] count = new int[5];
    	for(int i = 0; i < S.length(); ++i) {
    		count[letterToIndex(S.charAt(i))] = count[letterToIndex(S.charAt(i))] + 1;
//    		System.out.println(count[letterToIndex(S.charAt(i))]);
    	}
    	for(int j = 1; j < 5; ++j) {
    		count[j] = count[j] + count[j - 1];  
    	}
    	for(int i = S.length() - 1; i >= 0; --i) {
    		int c = letterToIndex(S.charAt(i));
    		count[c] = count[c] - 1;
    		order[count[c]] = i;
    	}
    	return order;
    }
    // TODO: by LCC on 03/04/2020
    private int[] ComputeCharClasses(String S, int[] order) {
    	int[] classes = new int[S.length()];
    	classes[order[0]] = 0;
    	for(int i = 1; i < S.length(); ++i) {
    		if(S.charAt(order[i]) != S.charAt(order[i - 1])) {
    			classes[order[i]] = classes[order[i - 1]] + 1;
    		}
    		else {
    			classes[order[i]] = classes[order[i - 1]];
    		}
//    		System.out.println(classes[order[i]]);
    	}
    	return classes;
    }
    // TODO: by LCC on 03/04/2020
    private int[] SortDoubled(String S, int L, int[] order, int[] classes) {
    	int[] count = new int[S.length()];
    	int[] newOrder = new int[S.length()];
    	for(int i = 0; i < S.length(); ++i) {
    		count[classes[i]] = count[classes[i]] + 1;
    	}
    	for(int j = 1; j < S.length(); ++j) {
    		count[j] = count[j] + count[j - 1];  
    	}
    	for(int i = S.length() - 1; i >= 0; --i) {
    		int start = (order[i] - L + S.length()) % S.length();
    		int cl = classes[start];
    		count[cl] = count[cl] - 1;
    		newOrder[count[cl]] = start;
    	}
//    	print(newOrder);
    	return newOrder;
    }
    private int[] UpdateClasses(int[] newOrder, int[] classes, int L) {
    	int n = newOrder.length;
    	int[] newClass = new int[n];
    	newClass[newOrder[0]] = 0;
    	for(int i = 1; i < n; ++i) {
    		int cur = newOrder[i], prev = newOrder[i - 1];
    		// changed from mid = (cur + L) to mid = (cur + L) % n
    		int mid = (cur + L) % n, midPrev = (prev + L) % n;
    		if(classes[cur] != classes[prev] || classes[mid] != classes[midPrev]) {
    			newClass[cur] = newClass[prev] + 1;
    		}
    		else {
				newClass[cur] = newClass[prev];
			}
    	}
//    	print(newClass);
    	return newClass;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}
