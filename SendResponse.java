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
public class SendResponse{
    private String returnValue;
    /**
     * Constructor
     */
    public SendResponse(String firstName, String lastName, String email, String turnaroundDate, String expirationDate, String salesPerson, String cartUrl){
        returnValue = "";
        String url = "https://floating-fortress-7484.herokuapp.com/rfq/rfqResponse.php";
        //needs params for email
        //name
        //email address
        //cart url
        //any notes - required minimums, any other information
        //expected turn around
        String postParams = "first_name=" + firstName + "&last_name=" + lastName + "&email_address=" + email + "&turnaround_date=" + turnaroundDate + "&expiration_date=" + expirationDate + "&sales_person=" + salesPerson + "&cart_url=" + cartUrl; 
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
                returnValue = "error - bad response code";
            }
            System.out.println(responseCode);
        }catch(MalformedURLException mue){
            returnValue = "error - malformed url";
        }catch(IOException ioe){
            returnValue = "error - io exception";
        }
        System.out.println(returnValue);
    }
}
