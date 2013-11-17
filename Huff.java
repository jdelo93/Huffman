import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huff {
	public static boolean DEBUG; 

	public static void main(String[] args) {
		Huff test = new Huff();
		char[] fileCharacters = test.readFile("test.txt");
		System.out.println(fileCharacters);
		System.out.print(test.symbolTable(fileCharacters));
		System.out.print(test.createQueue(test.symbolTable(fileCharacters)));
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
			this.freq = freq;
			this.parent = parent;
		}

		public int compareTo(Node x) {
			return this.freq - x.freq;
		}
	}

	// Create a priority queue with all the elements
	public PriorityQueue<Node> createQueue(HashMap<Character, Integer> symbols) {
		// Grabs all the symbols in the hashMap
		for(char c: symbols.keySet()) {
			System.out.println(symbols.get(c));
		}
		return null;
	}
}
