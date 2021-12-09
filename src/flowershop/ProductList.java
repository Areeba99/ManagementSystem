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
public class ProductList {
    private String productid;
    private String productname;
    private String productdescription;
    private int productquantity;
    private Double baseprice;
    private Double charges;
    private Double price;

    public ProductList(String productid, String productname, String productdescription, int productquantity, Double baseprice, Double charges, Double price) {
        this.productid = productid;
        this.productname = productname;
        this.productdescription = productdescription;
        this.productquantity = productquantity;
        this.baseprice = baseprice;
        this.charges = charges;
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public int getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(int productquantity) {
        this.productquantity = productquantity;
    }

    public Double getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(Double baseprice) {
        this.baseprice = baseprice;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

   
}
