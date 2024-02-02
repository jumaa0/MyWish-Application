/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.ArrayList;

/**
 *
 * @author JUMAA
 */
public class ItemListDTO {
   private String requestType;
    private ArrayList<ItemDTO> items = new ArrayList<>();

    public ItemListDTO() {
    }

    public ItemListDTO(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    public void setItems(ArrayList<ItemDTO> items) {
        this.items = items;
    }

    public ArrayList<ItemDTO> getItems() {
        return items;
    }
    
 
}
