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
public class FriendRequestListDTO {
    private  String UserName ;
    String requestType;
    ArrayList<FriendRequestDTO> requests;

    public FriendRequestListDTO(String UserName, String requestType, ArrayList<FriendRequestDTO> requests) {
        this.UserName = UserName;
        this.requestType = requestType;
        this.requests = requests;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

   

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public ArrayList<FriendRequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<FriendRequestDTO> requests) {
        this.requests = requests;
    }

    public FriendRequestListDTO() {
    }
    
    
}
