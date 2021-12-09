/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop;

/**
 *
 * @author areeb
 */
public class ItemList {
    private String itemid;
    private String itemname;
    private String itemdescription;
    private int quantity;
    private Double price;
    private String category;

    public ItemList(String itemid, String itemname, String itemdescription, int quantity, Double price, String category) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemdescription = itemdescription;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    /**
     * @return the itemid
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * @param itemid the itemid to set
     */
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    /**
     * @return the itemname
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname the itemname to set
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    /**
     * @return the itemdescription
     */
    public String getItemdescription() {
        return itemdescription;
    }

    /**
     * @param itemdescription the itemdescription to set
     */
    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    
}