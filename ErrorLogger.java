import java.io.FileWriter;
import java.io.IOException;
/**
 * Logs Errors to the log file
 * 
 * @author Matt Dizzine
 * @version 09.21.2016
 */
public class ErrorLogger{
    public static String logFile = PropertyReader.getProperty("errorlog");
    public ErrorLogger(){
    }
    
    public static void writeError(String errorMessage){
        try{
            FileWriter writer = new FileWriter(logFile,true);
            writer.write(errorMessage);
            writer.close();
        }catch(IOException e){
            System.out.println("IOException");
        }
    }
    
    public static String buildErrorString(int code){
        String errorString = "";
        return errorString;
    }
    
    public static String getErrorMessage(int code){
        String retString = "";
        switch(code){
            case 101:
                retString = "Error populating quotes due to bad address.";
                break;
            case 102:
                retString = "Error populating quotes due to bad web access.";
                break;
            case 103:
                retString = "Error populating quotes due to improper api credentials.";
                break;
            case 201:
                retString = "No quotes available in web system.";
                break;
        }
        return retString;
    }
}