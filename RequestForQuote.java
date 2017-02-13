import java.util.Calendar;
import java.util.ArrayList;
/**
 * Request For Quote Object
 * 
 * @author Matt Dizzine
 * @version 09.08.2016
 */
public class RequestForQuote{
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Calendar submitDate;
    private Calendar completeDate;
    private Calendar expirationDate;
    private String turnAroundTime;
    private boolean status;
    private String salesPerson;
    private ArrayList<Item> itemList;
    private String notes;
    private String fileName;
    
    /**
     * Constructor
     * @param String firstName
     * @param String lastName
     * @param String emailAddress
     * @param String phoneNumber
     * @param Calendar submitDate
     * @param Calendar completeDate
     * @param Calendar expirationDate
     * @param String turnAroundTime
     * @param boolean status - true for complete, false for incomplete
     * @param String salesPerson
     * @param ArrayList<Item> itemList
     * @param String notes
     * @param String fileName
     */
    public RequestForQuote(String firstName, String lastName, String emailAddress, String phoneNumber, Calendar submitDate, Calendar completeDate, Calendar expirationDate, String turnAroundTime, boolean status, String salesPerson, ArrayList<Item> itemList, String notes, String fileName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.submitDate = submitDate;
        this.completeDate = completeDate;
        this.expirationDate = expirationDate;
        this.turnAroundTime = turnAroundTime;
        this.status = status;
        this.salesPerson = salesPerson;
        this.itemList = itemList;
        this.notes = notes;
        this.fileName = fileName;
    }
    
    /**
     * Set first name
     * @param String firstName
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    /**
     * Set last name
     * @param String lastName
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    /**
     * Set email address
     * @param String emailAddress
     */
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
    
    /**
     * Set phone number
     * @param String phoneNumber
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Set submitDate
     * @param Calendar submitDate
     */
    public void setSubmitDate(Calendar submitDate){
        this.submitDate = submitDate;
    }
    
    /**
     * Set submitDate from String
     * @param String submitDateString
     */
    public void setSubmitDateFromString(String submitDateString){
        ParseXml parser = new ParseXml();
        submitDate = parser.buildDate(submitDateString);
    }
    
    /**
     * Set complete date
     * @param Calendar completeDate
     */
    public void setCompleteDate(Calendar completeDate){
        this.completeDate = completeDate;
    }
    
    /**
     * Set completeDate from string
     * @param String completeDateString
     */
    public void setCompleteDateFromString(String completeDateString){
        ParseXml parser = new ParseXml();
        completeDate = parser.buildDate(completeDateString);
    }
    
    /**
     * Set expiration date
     * @param Calendar expirationDate
     */
    public void setExpirationDate(Calendar expirationDate){
        this.expirationDate = expirationDate;
    }
    
    /**
     * Set expiration date from string
     * @param String expirationDateString
     */
    public void setExpirationDateFromString(String expirationDateString){
        ParseXml parser = new ParseXml();
        expirationDate = parser.buildDate(expirationDateString);
    }
    
    /**
     * Set turnaround time
     * @param String turnAroundTime
     */
    public void setTurnAroundTime(String turnAroundTime){
        this.turnAroundTime = turnAroundTime;
    }
    
    /**
     * Set status
     * - true for complete
     * - false for incomplete
     * @param boolean status
     */
    public void setStatus(boolean status){
        this.status = status;
    }
    
    /**
     * Set sales person - name of employee that handled quote
     * @param String salesPerson
     */
    public void setSalesPerson(String salesPerson){
        this.salesPerson = salesPerson;
    }
    
    /**
     * Set items list
     * @param ArrayList<Item> itemList
     */
    public void setItemsList(ArrayList<Item> itemList){
        this.itemList = itemList;
    }
    
    /**
     * Add item to itemList
     * @param Item item
     */
    public void addItemToList(Item item){
        itemList.add(item);
    }
    
    /**
     * Remove item from itemList
     * @param int index
     */
    public void removeItemFromList(int index){
        itemList.remove(index);
    }
    
    /**
     * Set notes
     * @param String notes
     */
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    /**
     * Set fileName
     * @param String fileName
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    
    /**
     * Return firstName
     * @return String firstName
     */
    public String getFirstName(){
        return firstName;
    }
    
    /**
     * Return lastName
     * @return String lastName
     */
    public String getLastName(){
        return lastName;
    }
    
    /**
     * Return emailAddress
     * @return String emailAddress
     */
    public String getEmailAddress(){
        return emailAddress;
    }
    
    /**
     * Return phoneNumber
     * @return String phoneNumber
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    /**
     * Return submitDate
     * @return Calendar submitDate
     */
    public Calendar getSubmitDate(){
        return submitDate;
    }
    
    /**
     * Return submitDate as string
     * @return String submitDateString
     */
    public String getSubmitDateString(){
        String submitDateString = "";        
        if(submitDate!=null){
            int month = submitDate.get(Calendar.MONTH) + 1;
            int day = submitDate.get(Calendar.DAY_OF_MONTH);
            int year = submitDate.get(Calendar.YEAR);
            if(month<10){
                submitDateString += "0";
            }
            submitDateString += month + "/";
            if(day<10){
                submitDateString += "0";
            }
            submitDateString += day + "/" + year;            
        }
        return submitDateString;
    }
    
    /**
     * Return completeDate
     * @return Calendar completeDate
     */
    public Calendar getCompleteDate(){
        return completeDate;
    }
    
    /**
     * Return completeDate as String
     * @return String completeDateString 
     */
    public String getCompleteDateString(){
        String completeDateString = "";
        if(completeDate!=null){
            int month = completeDate.get(Calendar.MONTH) + 1;
            int day = completeDate.get(Calendar.DAY_OF_MONTH);
            int year = completeDate.get(Calendar.YEAR);
            if(month<10){
                completeDateString += "0";
            }
            completeDateString += month + "/";
            if(day<10){
                completeDateString += "0";
            }
            completeDateString += day + "/" + year;
        }
        return completeDateString;
    }
    
    /**
     * Return expirationDate
     * @return Calendar expirationDate
     */
    public Calendar getExpirationDate(){
        return expirationDate;
    }
    
    /**
     * Return expirationDate as String
     * @return String expirationDateString
     */
    public String getExpirationDateString(){
        String expirationDateString = "";
        if(expirationDate!=null){
            int month = expirationDate.get(Calendar.MONTH) + 1;
            int day = expirationDate.get(Calendar.DAY_OF_MONTH);
            int year = expirationDate.get(Calendar.YEAR);
            if(month<10){
                expirationDateString += "0";
            }
            expirationDateString += month + "/";
            if(day<10){
                expirationDateString += "0";
            }
            expirationDateString += day + "/" + year;
        }
        return expirationDateString;
    }
    
    /**
     * Return turnAroundDate
     * @return String turnAroundTime
     */
    public String getTurnAroundTime(){
        return turnAroundTime;
    }
        
    /**
     * Return status
     * @return boolean status
     */
    public boolean getStatus(){
        return status;
    }
    
    /**
     * Return salesPerson
     * @return String salesPerson
     */
    public String getSalesPerson(){
        return salesPerson;
    }
    
    /**
     * Return itemList
     * @return ArrayList<Item> itemList
     */
    public ArrayList<Item> getItemList(){
        return itemList;
    }
    
    /**
     * Return specific item from itemList
     * @param int itemIndex
     * @return Item itemList.get(itemIndex)
     */
    public Item getSpecificItem(int itemIndex){
        return itemList.get(itemIndex);
    }
    
    /**
     * Return notes
     * @return String notes
     */
    public String getNotes(){
        return notes;
    }
    
    /**
     * Return fileName
     * @return String fileName
     */
    public String getFileName(){
        return fileName;
    }
    
    /**
     * Returns string representation of the object
     * @override
     * @return String objString
     */
    public String toString(){
        String objString = "First Name: " + getFirstName() + "\r\nLast Name: " + getLastName() + "\r\nEmail: " + getEmailAddress() + "\r\nPhoneNumber: " + getPhoneNumber() + "\r\n";
        objString += "\r\nSubmit Date: " + getSubmitDateString() + "\r\nComplete Date: " + getCompleteDateString() + "\r\nExpiration Date: " + getExpirationDateString() + "\r\nTurnaround Date: " + getTurnAroundTime() + "\r\n";
        objString += "\r\nStatus: ";
        if(getStatus()){
            objString += "Complete";
        }else{
            objString += "Incomplete";
        }
        objString += "\r\nSales Person: " + getSalesPerson() + "\r\n\r\nItems: ";
        for(Item i : itemList){
            objString += "\r\nVariant ID: " + i.getVariantId() + "\r\nProduct ID: " + i.getProductId() + "\r\nDescription " + i.getDescription() + "\r\nQuantity: " + i.getQuantity() + "\r\nSKU: " + i.getSku() + "\r\nPrice: " + i.getPrice() + "\r\n";
        }
        objString += "\r\nNotes: " + getNotes() + "\r\n\r\nFile Name: " + getFileName();
        return objString;
    }
}