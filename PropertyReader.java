import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
/**
 * Reads information from the properties file
 * 
 * @author Matt Dizzine
 * @version 09.21.2016
 */
public class PropertyReader{
    private static String propertiesFile = "C:\\Users\\Awaldman\\Desktop\\javaRfq\\modFiles\\rfqresponder.properties";   
    public static String getProperty(String propertyName){        
        String resultString = "";
        HashMap<String,String> propertiesMap = new HashMap<String,String>();
        try{
            FileReader reader = new FileReader(propertiesFile);
            Scanner fileIn = new Scanner(reader);
            fileIn.useDelimiter("\r\n");
            while(fileIn.hasNext()){
                String temp = fileIn.next();
                if(!temp.substring(0,1).equals("#")){
                    String[] splitter = temp.split("=");
                    propertiesMap.put(splitter[0],splitter[1]);
                }
            }
            reader.close();
            resultString = propertiesMap.get(propertyName);
        }catch(IOException e){
            //write to error log
            System.out.println("IOException");
        }catch(ArrayIndexOutOfBoundsException e){
            //write to error log
            System.out.println("ArrayIndexOutOfBounds");
        }
        return resultString;
    }
    
    public static String getPropertiesFileLocation(){
        return propertiesFile;
    }
}