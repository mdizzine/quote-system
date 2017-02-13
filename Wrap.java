/**
 * Wrap allows to test interaction between classes
 * 
 * @author Matt Dizzine
 * @version 09.08.2016
 */
public class Wrap{
    private RequestForQuote rfq;
    public Wrap(){
        ParseXml parser = new ParseXml();
        rfq = parser.parseFile("C:\\shopifyRFQresponse\\11.xml");
    }
    
    public void editValue(String type, String value){
        switch(type){
            case "firstName":
                rfq.setFirstName(value);
                break;
            case "lastName":
                rfq.setLastName(value);
                break;
            case "emailAddress":
                rfq.setEmailAddress(value);
                break;
            case "phoneNumber":
                rfq.setPhoneNumber(value);
                break;
            case "submitDate":
                rfq.setSubmitDateFromString(value);
                break;
            case "completeDate":
                rfq.setCompleteDateFromString(value);
                break;
            case "expirationDate":
                rfq.setExpirationDateFromString(value);
                break;
            case "turnAroundTime":
                rfq.setTurnAroundTime(value);
                break;
            case "status":
                rfq.setStatus(Boolean.parseBoolean(value));
                break;
            case "salesPerson":
                rfq.setSalesPerson(value);
                break;
            case "notes":
                rfq.setNotes(value);
            default:
               System.out.println("invalid type");
                break;
        }
    }
    
    public void writeFile(){
        ModifyXml writer = new ModifyXml(rfq);
        writer.setElementData();
    }
    
    public String getCompleteDateString(){
        return rfq.getCompleteDateString();
    }
    
    public String getSubmitDateString(){
        return rfq.getSubmitDateString();
    }
    
    public String getExpirationDateString(){
        return rfq.getExpirationDateString();
    }
}