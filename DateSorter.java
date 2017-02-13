import java.util.Comparator;
/**
 * Class to sort rfqs by date
 * 
 * @author Matt Dizzine
 * @version 09.09.2016
 */
public class DateSorter implements Comparator<RequestForQuote>{
    public int compare(RequestForQuote o1, RequestForQuote o2){
        int returnVal = 0;
        if(o1.getSubmitDate().getTimeInMillis()<o2.getSubmitDate().getTimeInMillis())returnVal = 1;
        else if(o1.getSubmitDate().getTimeInMillis()==o2.getSubmitDate().getTimeInMillis())returnVal = 0;
        else if(o1.getSubmitDate().getTimeInMillis()>o2.getSubmitDate().getTimeInMillis())returnVal = -1;
        return returnVal;
    }
}