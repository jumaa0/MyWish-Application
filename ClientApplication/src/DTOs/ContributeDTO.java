/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author husse
 */
public class ContributeDTO {
    
    String requestType;
    String contributor;
    String wishingUser;
    String itemId;    
    String itemName;
    int price;
    int amount;
    int collected_amount;

    public ContributeDTO(String requestType, String contributor, String wishingUser, String itemId, int price, int amount, int collected_amount,String itemName) {
        this.requestType = requestType;
        this.contributor = contributor;
        this.wishingUser = wishingUser;
        this.itemId = itemId;
        this.price = price;
        this.amount = amount;
        this.collected_amount = collected_amount;
        this.itemName= itemName; 
    }


    public ContributeDTO() {
    }

    public ContributeDTO(String requestType, String contributor, String wishingUser, String itemId, int amount,String itemName) {
        this.requestType = requestType;
        this.contributor = contributor;
        this.wishingUser = wishingUser;
        this.itemId = itemId;
        this.amount = amount;
         this.itemName= itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getCollected_amount() {
        return collected_amount;
    }

    public void setCollected_amount(int collected_amount) {
        this.collected_amount = collected_amount;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getWishingUser() {
        return wishingUser;
    }

    public void setWishingUser(String wishingUser) {
        this.wishingUser = wishingUser;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
}
