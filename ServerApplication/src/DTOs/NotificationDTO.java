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
public class NotificationDTO {
    String requestType;
    String username;
    String message;
    String status;
    
    public NotificationDTO() {
    }

    public NotificationDTO(String requestType,String username) {
        this.username = username;
         this.requestType = requestType;
       
    }

    public NotificationDTO(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

}
