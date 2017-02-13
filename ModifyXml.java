import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
/**
 * Modifies existing xml values
 * 
 * @author Matt Dizzine
 * @version 09.08.2016
 */
public class ModifyXml{
    private RequestForQuote rfq;
    
    /**
     * Constructor
     */
    public ModifyXml(RequestForQuote rfq){
        this.rfq = rfq;
    }
    
    /**
     * Set rfq
     * @param RequestForQuote rfq
     */
    public void setRequestForQuote(RequestForQuote rfq){
        this.rfq = rfq;
    }
    
    /**
     * Return rfq
     * @return RequestForQuote rfq
     */
    public RequestForQuote getRequestForQuote(){
        return rfq;
    }
    
    /**
     * Modify element
     * @param String elementName
     * @param String newValue
     * @return boolean pass
     */
    public boolean setElementData(){
        boolean pass = true;
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document dom = dBuilder.newDocument();
            Element rootElm = dom.createElement("rfq");
            Element elm = dom.createElement("firstName");
            elm.appendChild(dom.createTextNode(rfq.getFirstName()));
            rootElm.appendChild(elm);
            elm = dom.createElement("lastName");
            elm.appendChild(dom.createTextNode(rfq.getLastName()));
            rootElm.appendChild(elm);
            elm = dom.createElement("emailAddress");
            elm.appendChild(dom.createTextNode(rfq.getEmailAddress()));
            rootElm.appendChild(elm);
            elm = dom.createElement("phoneNumber");
            elm.appendChild(dom.createTextNode(rfq.getPhoneNumber()));
            rootElm.appendChild(elm);
            elm = dom.createElement("submitDate");
            elm.appendChild(dom.createTextNode(rfq.getSubmitDateString()));
            rootElm.appendChild(elm);
            elm = dom.createElement("completeDate");
            elm.appendChild(dom.createTextNode(rfq.getCompleteDateString()));
            rootElm.appendChild(elm);
            elm = dom.createElement("expirationDate");
            elm.appendChild(dom.createTextNode(rfq.getExpirationDateString()));
            rootElm.appendChild(elm);
            elm = dom.createElement("turnAroundTime");
            elm.appendChild(dom.createTextNode(rfq.getTurnAroundTime()));
            rootElm.appendChild(elm);
            elm = dom.createElement("status");
            elm.appendChild(dom.createTextNode(String.valueOf(rfq.getStatus())));
            rootElm.appendChild(elm);
            elm = dom.createElement("salesPerson");
            elm.appendChild(dom.createTextNode(rfq.getSalesPerson()));
            rootElm.appendChild(elm);
            Element itemElm = dom.createElement("items");
            Element item;
            for(Item i : rfq.getItemList()){
                item = dom.createElement("item");
                elm = dom.createElement("variantId");
                elm.appendChild(dom.createTextNode(i.getVariantId()));
                item.appendChild(elm);
                elm = dom.createElement("productId");
                elm.appendChild(dom.createTextNode(i.getProductId()));
                item.appendChild(elm);
                elm = dom.createElement("description");
                elm.appendChild(dom.createTextNode(i.getDescription()));
                item.appendChild(elm);
                elm = dom.createElement("quantity");
                elm.appendChild(dom.createTextNode(String.valueOf(i.getQuantity())));
                item.appendChild(elm);
                elm = dom.createElement("sku");
                elm.appendChild(dom.createTextNode(i.getSku()));
                item.appendChild(elm);
                elm = dom.createElement("price");
                elm.appendChild(dom.createTextNode(String.valueOf(i.getPrice())));
                item.appendChild(elm);
                itemElm.appendChild(item);
            }
            rootElm.appendChild(itemElm);
            elm = dom.createElement("notes");
            elm.appendChild(dom.createTextNode(rfq.getNotes()));
            rootElm.appendChild(elm);
            dom.appendChild(rootElm);
            try{
                Transformer trans = TransformerFactory.newInstance().newTransformer();
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                trans.setOutputProperty(OutputKeys.METHOD, "xml");
                trans.setOutputProperty("{https://xml.apache.org/xslt}indent-amount","4");
                FileOutputStream opStream = new FileOutputStream(rfq.getFileName());
                trans.transform(new DOMSource(dom),new StreamResult(opStream));
                opStream.close();
            }catch(TransformerException te){
                pass = false;
            }catch(IOException ioe){
                pass = false;
            }
        }catch(Exception e){
            pass = false;
        }
        return pass;
    }
}