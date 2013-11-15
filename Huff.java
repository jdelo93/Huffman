import java.io.*;
import java.util.HashMap;

public class Huff {
	public static boolean DEBUG; 

	public static void main(String[] args) {
		Huff test = new Huff();
		char[] fileCharacters = test.readFile("test.txt");
		System.out.print(fileCharacters);
		System.out.println();
		System.out.print(test.symbolTable(fileCharacters));
	}

	// should return a symbolTable aka HashMap 
        public HashMap<Character, Integer> symbolTable(char[] fileCharacters) {
            	HashMap<Character, Integer> symbols = new HashMap<Character, Integer>();
            	int f = 1; // variable that will hold frequencies
            	for ( char i : fileCharacters){
                	if (symbols.containsKey(i)) symbols.put(i, f + 1);
                	else{symbols.put(i, f); }
            	}
            	return symbols;
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
