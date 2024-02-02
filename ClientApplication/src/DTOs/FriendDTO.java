/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.ArrayList;

/**
 *
 * @author husse
 */
public class FriendDTO {
    private String name;
    private ArrayList<ItemDTO> wishlist;
    private boolean sentRequest;

    public FriendDTO() {
    }

    public FriendDTO(String name, ArrayList<ItemDTO> wishlist) {
        this.name = name;
        this.wishlist = wishlist;
    }

    public ArrayList<ItemDTO> getWishlist() {
        return wishlist;
    }

    public void setWishlist(ArrayList<ItemDTO> wishlist) {
        this.wishlist = wishlist;
    }

    public boolean isSentRequest() {
        return sentRequest;
    }

    public void setSentRequest(boolean sentRequest) {
        this.sentRequest = sentRequest;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
