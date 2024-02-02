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
public class ItemDTO {
   
    String itemId, itemName, description;
    int price, collectedAmount;
    String category;
    
    
    
    /*CREATE TABLE ITEMS (ITEM_ID VARCHAR(50) NOT NULL, 
           ITEM_NAME VARCHAR(50) NOT NULL
           DESCRIPTION VARCHAR(50), PRICE INTEGER,
           CATEGOREY VARCHAR(50), */

    public ItemDTO() {
    }

    public ItemDTO(String itemId, String itemName, String description, 
            int price, int collectedAmount, String category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.collectedAmount = collectedAmount;
        this.category = category;
    }

    public ItemDTO(String itemId, String itemName, String description, int price, String category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    
    
 
}
