import java.io.*;
import java.util.HashMap;

public class Huff {
	public static boolean DEBUG; 

	public static void main(String[] args) {
		Huff test = new Huff();
		char[] fileCharacters = test.readFile("test.txt");
		System.out.print(fileCharacters);
	}

	// should return a symbolTable aka HashMap 
	public HashMap<Integer, Integer> symbolTable(char[] fileCharacters) {
		HashMap<Integer, Integer> symbols = new HashMap<Integer, Integer>();
		// Do something to associate the symbols with an address and frequency 
		return null;
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
		}
		catch(Exception e) {// Change exception? 
			System.out.print("readFile has failed!");
		}
		return fileCharacters;
	}


}