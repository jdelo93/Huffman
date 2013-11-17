import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Huff {
	public static boolean DEBUG; 

	public static void main(String[] args) {
		Huff test = new Huff();
		System.out.print(test);
	}

	// Returns all the characters in the file
	public char[] readFile(String fileName) {
		// Opens the file and loads it into fileReader
		FileIOC file = new FileIOC();
		FileReader fileReader = file.openInputFile(fileName);
		
		// Creates an array of characters in order to be read later
		// The length of the array is determined by how many characters are 
		// actually in the file which is found with fileCount.length() 
		File fileCount = new File(fileName);
		char[] fileCharacters = new char[(int) fileCount.length()];
		
		// Actually does the character reading 
		try {
			fileReader.read(fileCharacters);
			// Do we actually have to close the file?
			fileReader.close();
		}
		catch(Exception e) {// Change exception? 
			System.out.print("readFile has failed!");
		}
		return fileCharacters;
	}


	// Should return a symbolTable aka HashMap 
    public HashMap<Character, Integer> symbolTable(char[] fileCharacters) {
        	HashMap<Character, Integer> symbols = new HashMap<Character, Integer>();
        	int f = 1; // variable that will hold frequencies
        	for ( char i : fileCharacters){
            	if (symbols.containsKey(i)) symbols.put(i, symbols.get(i) + 1);
            	else{symbols.put(i, f); }
        	}
        	return symbols;
    }


	// Leaf Node for each class
	private static class Node implements Comparable<Node> {
		private char ch;
		private int freq;
		private Node parent;

		public Node(char ch, int freq, Node parent) {
			this.ch = ch;
			// freq = weight
			this.freq = freq;
			this.parent = parent;
		}

		public int compareTo(Node x) {
			return this.freq - x.freq;
		}
	}

	// Create a priority queue with all the elements
	public PriorityQueue<Node> createQueue(HashMap<Character, Integer> symbols) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(symbols.size(), comparing);
		// Grabs all the symbols in the hashMap
		for(char c: symbols.keySet()) {
			Node current = new Node(c, symbols.get(c), null);
			pq.add(current);
		}
		return pq;
	}

	// Comparable declaration for the priority Queue, compares by frequency 
	public static Comparator<Node> comparing = new Comparator<Node>() {
		@Override
		public int compare(Node thiss, Node that) {
			return (int) (thiss.freq - that.freq);
		}
	};

	@Override
	public String toString() {
		Huff test = new Huff();
		char[] fileCharacters = test.readFile("test.txt");
		HashMap<Character, Integer> symbolTable = test.symbolTable(fileCharacters);
		PriorityQueue<Node> testq = test.createQueue(symbolTable);
		String answer = "";
		while(testq.peek() != null) {
			Node temp = testq.poll();
			answer += "character: " + temp.ch + " frequency: " + temp.freq + "\n";
		}
		return answer;
	}
}
