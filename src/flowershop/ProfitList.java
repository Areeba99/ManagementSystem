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
public class ProfitList {
    String productid;
    Double purchaseprice;
    Double sellingprice;
    Double profit;

    public ProfitList(String productid, Double purchaseprice, Double sellingprice, Double profit) {
        this.productid = productid;
        this.purchaseprice = purchaseprice;
        this.sellingprice = sellingprice;
        this.profit = profit;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Double getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(Double purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public Double getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(Double sellingprice) {
        this.sellingprice = sellingprice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
    
}
