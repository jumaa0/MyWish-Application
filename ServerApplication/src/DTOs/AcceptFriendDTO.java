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
public class AcceptFriendDTO {
            String requestType, sent_to, sent_from;

    public AcceptFriendDTO() {
    }

    public AcceptFriendDTO(String requestType, String sent_to, String sent_from) {
        this.requestType = requestType;
        this.sent_to = sent_to;
        this.sent_from = sent_from;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getSent_to() {
        return sent_to;
    }

    public void setSent_to(String sent_to) {
        this.sent_to = sent_to;
    }

    public String getSent_from() {
        return sent_from;
    }

    public void setSent_from(String sent_from) {
        this.sent_from = sent_from;
    }
            
    
    
}
