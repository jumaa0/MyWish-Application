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
public class DeclineFriendDTO {
    String sent_to ;
    String sent_from ;
    String requestType ;

    public DeclineFriendDTO(String sent_to, String sent_from, String requestType) {
        this.sent_to = sent_to;
        this.sent_from = sent_from;
        this.requestType = requestType;
    }

    public DeclineFriendDTO() {
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

    public String getRequetType() {
        return requestType;
    }

    public void setRequetType(String requestType) {
        this.requestType = requestType;
    }
    
    
}
