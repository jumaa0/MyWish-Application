/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author Ahmed Samy
 */
public class MyWishItems {
 String requestType;
 String username;
 String itemid;
  String itemname;
 String description;
 String category;
 int price;
 int collectedAmount;

    public MyWishItems(String requestType, String username) {
        this.requestType = requestType;
        this.username = username;
    }

   public MyWishItems(String itemid, String itemname, String description,int price, String category,  int collectedAmount) {
        this.itemid = itemid;
        this.itemname=itemname;
        this.description = description;
        this.category = category;
        this.price = price;
        this.collectedAmount = collectedAmount;
    }

    public MyWishItems(String requestType, String username, String itemid) {
        this.requestType = requestType;
        this.username = username;
        this.itemid = itemid;
    }

    

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(int collectedAmount) {
        this.collectedAmount = collectedAmount;
    }
 
 
    
}
