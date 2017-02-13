import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Calendar;
import java.util.ArrayList;
/**
 * Reads xml file and builds RequestForQuote Object
 * 
 * @author Matt Dizzine
 * @version 09.08.2016
 */
public class ParseXml{
    
    /**
     * Constructor
     */
    public ParseXml(){        
    }
    
    public RequestForQuote parseFile(String filePath){
        RequestForQuote rfq = null;
        try{
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);            
            String fName = doc.getElementsByTagName("firstName").item(0).getTextContent();
            String lName = doc.getElementsByTagName("lastName").item(0).getTextContent();
            String email = doc.getElementsByTagName("emailAddress").item(0).getTextContent();
            String phone = doc.getElementsByTagName("phoneNumber").item(0).getTextContent();
            String submitDateString = doc.getElementsByTagName("submitDate").item(0).getTextContent();
            Calendar submitDate = buildDate(submitDateString);
            String completeDateString = doc.getElementsByTagName("completeDate").item(0).getTextContent();
            Calendar completeDate = buildDate(completeDateString);
            String expirationDateString = doc.getElementsByTagName("expirationDate").item(0).getTextContent();
            Calendar expirationDate = buildDate(expirationDateString);
            String turnAroundTime = doc.getElementsByTagName("turnAroundTime").item(0).getTextContent();
            boolean status = Boolean.parseBoolean(doc.getElementsByTagName("status").item(0).getTextContent());
            String salesPerson = doc.getElementsByTagName("salesPerson").item(0).getTextContent();
            ArrayList<Item> itemList = new ArrayList<Item>();
            NodeList items = doc.getElementsByTagName("item");
            for(int i = 0; i < items.getLength(); i++){
                Node node = items.item(i);
                Element elm = (Element)node;
                String variantId = elm.getElementsByTagName("variantId").item(0).getTextContent();
                String productId = elm.getElementsByTagName("productId").item(0).getTextContent();
                String description = elm.getElementsByTagName("description").item(0).getTextContent();
                int quantity = Integer.parseInt(elm.getElementsByTagName("quantity").item(0).getTextContent());
                String sku = elm.getElementsByTagName("sku").item(0).getTextContent();
                double price = Double.parseDouble(elm.getElementsByTagName("price").item(0).getTextContent());
                itemList.add(new Item(variantId,productId,description,quantity,sku,price));
            }
            String notes = doc.getElementsByTagName("notes").item(0).getTextContent();
            rfq = new RequestForQuote(fName,lName,email,phone,submitDate,completeDate,expirationDate,turnAroundTime,status,salesPerson,itemList,notes,filePath);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return rfq;
        }
    }
    
    public Calendar buildDate(String inputDate){
        Calendar returnDate = null;
        if(inputDate!=""){
            String[] splitter = inputDate.split("/");
            int month = Integer.parseInt(splitter[0]) - 1;
            int day = Integer.parseInt(splitter[1]);
            int year = Integer.parseInt(splitter[2]);
            returnDate = Calendar.getInstance();
            returnDate.set(year,month,day);
        }
        return returnDate;
    }
}