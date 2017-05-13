package other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class IO {
	
	public IO() {
		
	}
	
	public static String[] getHighScores() {
		String[] strArr = new String[10];
		 // The name of the file to open.
        String fileName = "highscores.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            for(int i = 0; (i < 10) &&  (line = bufferedReader.readLine()) != null; i++) {
            	
            }
            
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
            	
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}
	
	public static void writeHighscore(String name, int score) {
		try {
			File out = new File("highscores.txt");
			if(out.exists() && out.canWrite()) {
				PrintWriter pw = new PrintWriter(out);
				//TODO
			}
		}
		catch(Exception e) {
			System.out.println("Error Writing Highscores");
			e.printStackTrace();
		}
	}
	
	private static void writeOneHighscore() {
		
	}
}
