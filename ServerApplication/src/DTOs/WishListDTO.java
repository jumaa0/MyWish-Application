/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author JUMAA
 */
public class WishListDTO {
    String requestType;
    String username;
    String itemid;
    int collected_amount;

    public WishListDTO(String requestType, String username, String itemid, int collected_amount) {
        this.requestType = requestType;
        this.username = username;
        this.itemid = itemid;
        this.collected_amount = collected_amount;
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

    public int getCollected_amount() {
        return collected_amount;
    }

    public void setCollected_amount(int collected_amount) {
        this.collected_amount = collected_amount;
    }
    
    
}
