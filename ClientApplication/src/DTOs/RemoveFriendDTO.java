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
public class RemoveFriendDTO {
    
    String requestType;
    String username;
    String friendname;

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

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public RemoveFriendDTO(String requestType, String username, String friendname) {
        this.requestType = requestType;
        this.username = username;
        this.friendname = friendname;
    }
    
    
    
}
