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
public class OrderList {
    
    private String productid;
    private String productname;
    private Double price;
    private int quantity;
    private Double total;

    public OrderList(String productid, String productname, Double price, int quantity, Double total) {
        this.productid = productid;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderList{" + "productid=" + productid + ", productname=" + productname + ", price=" + price + ", quantity=" + quantity + ", total=" + total + '}';
    }
    

    public OrderList(String productid) {
        this.productid = productid;
    }
    
    /**
     * @return the productid
     */
    public String getProductid() {
        return productid;
    }

    /**
     * @param productid the productid to set
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * @return the productname
     */
    public String getProductname() {
        return productname;
    }

    /**
     * @param productname the productname to set
     */
    public void setProductname(String productname) {
        this.productname = productname;
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
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param productquantity
     */
    public void setQuantity(int productquantity) {
        this.quantity = productquantity;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }
    
    
}
