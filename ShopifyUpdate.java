import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
/**
 * Sends post request to heroku
 * 
 * @author Matt Dizzine
 * @version 9.7.2016
 */
public class ShopifyUpdate{
    private String returnValue;
    /**
     * Constructor
     */
    public ShopifyUpdate(String input){
        returnValue = "";
        String url = "INSERT POST URL HERE";
        String postParams = "data=" + input;
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(postParams.getBytes());
            os.flush();
            os.close();
            
            int responseCode = con.getResponseCode();
            
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                returnValue = response.toString();
            }else{
                returnValue = "error";
            }
        }catch(MalformedURLException mue){
            returnValue = "error";
        }catch(IOException ioe){
            returnValue = "error";
        }
    }
}
