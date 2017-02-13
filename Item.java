/**
 * Item object
 * 
 * @author Matt Dizzine
 * @version 09.08.2016
 */
public class Item{
    private String variantId;
    private String productId;
    private String description;
    private int quantity;
    private String sku;
    private double price;
    
    /**
     * Constructor
     * @param String variantId
     * @param String productId
     * @param String description
     * @param int quantity
     * @param String sku
     * @param double price
     */
    public Item(String variantId, String productId, String description, int quantity, String sku, double price){
        this.variantId = variantId;
        this.productId = productId;
        this.description = description;
        this.quantity = quantity;
        this.sku = sku;
        this.price = price;
    }
    
    /**
     * Set variantId
     * @param String variantId
     */
    public void setVariantId(String variantId){
        this.variantId = variantId;
    }
    
    /**
     * Set productId
     * @param String productId
     */
    public void setProductId(String productId){
        this.productId = productId;
    }
    
    /**
     * Set description
     * @param String description
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /**
     * Set quantity
     * @param int quantity
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    /**
     * Set SKU
     * @param String sku
     */
    public void setSku(String sku){
        this.sku = sku;
    }
    
    /**
     * Set price
     * @param double price
     */
    public void setPrice(double Price){
        this.price = price;
    }
    
    /**
     * Return variantId
     * @return String variantId
     */
    public String getVariantId(){
        return variantId;
    }
    
    /**
     * Return productId
     * @return String productId
     */
    public String getProductId(){
        return productId;
    }
    
    /**
     * Return description
     * @return String description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Return quantity
     * @return int quantity
     */
    public int getQuantity(){
        return quantity;
    }
    
    /**
     * Return sku
     * @return String sku
     */
    public String getSku(){
        return sku;
    }
    
    /**
     * Return price
     * @return double price
     */
    public double getPrice(){
        return price;
    }
}