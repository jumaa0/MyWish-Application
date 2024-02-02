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
public class SearchFriendDTO {
    String requestType ;
    String userName ;
    String myname;
    ArrayList<FriendDTO> usersList ;

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
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

    public ArrayList<FriendDTO> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<FriendDTO> usersList) {
        this.usersList = usersList;
    }

    public SearchFriendDTO(String requestType, String userName, String myname, ArrayList<FriendDTO> usersList) {
       this.myname=myname;
        this.requestType = requestType;
        this.userName = userName;
        this.usersList = usersList;
    }

    public SearchFriendDTO() {
    }
    
}
