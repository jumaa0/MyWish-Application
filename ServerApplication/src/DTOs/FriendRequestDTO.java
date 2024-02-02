/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author dell
 */
public class FriendRequestDTO {
    private String sentFrom ;
    private String sentTo ;
    private String status ;

    public FriendRequestDTO(String sentFrom, String sentTo, String status) {
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.status = status;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FriendRequestDTO() {
    }
    
}
