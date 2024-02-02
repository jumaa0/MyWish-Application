/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class AddFriendDTO {
     String requestType ;
     String userName ;
     String friendName ;
     private ArrayList<FriendDTO> usersList ;

    public AddFriendDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public AddFriendDTO(String requestType, String userName, String friendName, ArrayList<FriendDTO> usersList) {
        this.requestType = requestType;
        this.userName = userName;
        this.friendName = friendName;
        this.usersList = usersList;
    }


    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public ArrayList<FriendDTO> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<FriendDTO> usersList) {
        this.usersList = usersList;
    }
     
}
