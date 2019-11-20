package en.pocatko.michal.stochastia.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * A class used across the project to load any text used in the game
 */
public class Textdata {
    private static HashMap<String, String> m_textData;
    public static void Init(){
        m_textData = new HashMap<>();
        InputStream textDataResource = Textdata.class.getClassLoader().getResourceAsStream("Textdata.txt");
        Reader textDataResourceReader = null;
        try {
            textDataResourceReader = new InputStreamReader(textDataResource, "UTF-8");
        }
        catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(textDataResourceReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                int delimiterPos = line.indexOf(':');
                String name = line.substring(0, delimiterPos);
                String text = line.substring(delimiterPos + 1, line.length());
                m_textData.put(name, text);
            }
        }
        catch(FileNotFoundException fnfe) {System.err.println(fnfe.toString());}
        catch(IOException ioe) {System.err.println(ioe.toString());}
    }
    public static String Get(String name) {
        if(m_textData.containsKey(name)) {
            return m_textData.get(name);
        }
        else {
            return "unknown";
        }
    }
}