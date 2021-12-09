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
public class PurchaseList {
    private String itemid;
    private int supplierid;
    private int employeeid;
    private int quantity;
    private Double unitprice;
    private String supplydate;
    private Double total;

    public PurchaseList(String itemid, int supplierid, int employeeid, int quantity, Double unitprice, String supplydate, Double total) {
        this.itemid = itemid;
        this.supplierid = supplierid;
        this.employeeid = employeeid;
        this.quantity = quantity;
        this.unitprice = unitprice;
        this.supplydate = supplydate;
        this.total = total;
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
     * @return the supplierid
     */
    public int getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid the supplierid to set
     */
    public void setSupplierid(int supplierid) {
        this.supplierid = supplierid;
    }

    /**
     * @return the employeeid
     */
    public int getEmployeeid() {
        return employeeid;
    }

    /**
     * @param employeeid the employeeid to set
     */
    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
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
     * @return the unitprice
     */
    public Double getUnitprice() {
        return unitprice;
    }

    /**
     * @param unitprice the unitprice to set
     */
    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    /**
     * @return the supplydate
     */
    public String getSupplydate() {
        return supplydate;
    }

    /**
     * @param supplydate the supplydate to set
     */
    public void setSupplydate(String supplydate) {
        this.supplydate = supplydate;
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
