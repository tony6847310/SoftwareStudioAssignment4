import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Words {
	//declare two hashmaps and two string arrays
	//hashmap matches the images path with the String it shows
	//array lets me use an integer to get the image path
	public HashMap<String, String> known = new HashMap<String, String>();
	public HashMap<String, String> unknown = new HashMap<String, String>();
	public String[] knownKeys, unknownKeys;
	
	public Words() {
		
		knownKeys = new String[100];
		unknownKeys = new String[100];
		String file = "known_words.txt";
		String line;
		try{
			//create a bufferedreader from known_words
			BufferedReader br = new BufferedReader(new FileReader(file) );
			int count = 0;
			
		    while ( (line = br.readLine() ) != null) {
		    	//separate the line into two parts, first part is the key
		    	known.put(line.substring(0, 9), line.substring(10,line.length()) );
		    	knownKeys[count] = line.substring(0, 9);
		    	count++;
		    }
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		file = "unknown_words.txt";
		try {
			//create a bufferedreader from unknown_words
			BufferedReader br = new BufferedReader(new FileReader(file) );
			int count = 0;
			
			while ( (line = br.readLine() ) != null) {
				unknown.put(line.substring(0, 9), "");
				unknownKeys[count] = line.substring(0, 9);
				count++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

