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
public class FriendListDTO {
    
    private String requestType;
    private String userName;
    private ArrayList<FriendDTO> friends = new ArrayList<>();

    public FriendListDTO() {
    }

    public FriendListDTO(String requestType, String userName) {
        this.requestType = requestType;
        this.userName = userName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    

    public ArrayList<FriendDTO> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<FriendDTO> friendNames) {
        this.friends = friendNames;
    }
    
    
    
    
    
}
