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
	
	public void combineQueue(PriorityQueue<Node> pq){
        Node removed1;
        Node removed2;
        Node combined;
        while (pq.size() > 1){
            removed1 = pq.poll();
            removed2 = pq.poll();
            combined = new Node('\0', removed1.freq + removed2.freq, null);
            combined.left = removed1;
            combined.right = removed2;
            removed1.parent = combined;
            removed2.parent = combined;
            pq.add(combined);
            System.out.println("Removed characters are " + removed1.ch + " " + removed2.ch);
        }
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
	
	public int stringConvert(String binaryString){
        	int binaryInt = Integer.parseInt(binaryString, 2);
        	return binaryInt;   
        }   
    
    	public BinaryOut writeFile(String fileName, Node huffman){
        	FileIOC file = new FileIOC();
        	char[] fileCharacters = readFile(fileName);
        	HashMap<Character, Integer> symbolTable = symbolTable(fileCharacters);
        	FileReader fileReader = file.openInputFile(fileName);
        	BinaryOut binaryFile = file.openBinaryOutputFile();
        	int magic = 0x00BC;
        	binaryFile.write(magic, 16);
        	int tableSize = symbolTable.size();
        	binaryFile.write(tableSize, 32);
        	for (char c : fileCharacters){
        	 	binaryFile.write(c, 8);
            		binaryFile.write(symbolTable.get(c), 32);
        	}
		HashMap<Character, String> bitTable = buildMap(huffman);
        	for(char c : fileCharacters){
            		binaryFile.write(stringConvert(bitTable.get(c)), 4);
        	}
        	//fileReader.close();
        	binaryFile.close();
        	return binaryFile;
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
		private Node left;
		private Node right;

		public Node(char ch, int freq, Node parent) {
			this.ch = ch;
			// freq = weight
			this.freq = freq;
			this.parent = parent;
			this.left = null;
			this.right = null;
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
			if(thiss.freq - that.freq == 0) return 1; 
			else{return (int) (thiss.freq - that.freq);}
		}
	};

	// Builds a symbol Table of all bit addresses
	public HashMap<Character, String> buildMap(Node huffman) {
		HashMap<Character, String> sTable = new HashMap<Character, String>();
		String s = "";
		traverse(huffman, sTable, s);
		return sTable;
	}

	public static void traverse(Node huffman, HashMap<Character, String> sTable, String s) {
		if(huffman.left != null) {
			traverse(huffman.left, sTable, s + "0");
		}
		if (huffman.ch != '\0') sTable.put(huffman.ch, s);;
		if(huffman.right != null) {
			traverse(huffman.right, sTable, s + "1");
		}
	}


	@Override
	public String toString() {
		Huff test = new Huff();
		char[] fileCharacters = test.readFile("test.txt");
		HashMap<Character, Integer> symbolTable = test.symbolTable(fileCharacters);
		PriorityQueue<Node> testsingleTree = test.createQueue(symbolTable);
		//PriorityQueue<Node> testPQ = test.createQueue(symbolTable);
		String answersingleTree = "";
		//String answerPQ = "";
		/*
		while(testPQ.peek() != null) {
			Node temp = testPQ.poll();
			answerPQ += "character: " + temp.ch + " frequency: " + temp.freq + "\n";
		}*/
		test.combineQueue(testsingleTree);
		Node treeTest = testsingleTree.poll();
		HashMap<Character, String> bitMap = new HashMap<Character,String>();
		bitMap = test.buildMap(treeTest);
		return bitMap + "";
	}
}
