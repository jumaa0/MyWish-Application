/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author husse
 */
public class LoginDTO {
    
    private String requestType;
    private String username;
    private String password;
    private String status;
    private int balance;
    private int notiCount;
    
    public LoginDTO(){};

    public LoginDTO(String requestType, String username, String password) {
        this.requestType = requestType;
        this.username = username;
        this.password = password;
    }

    public int getNotiCount() {
        return notiCount;
    }

    public void setNotiCount(int notiCount) {
        this.notiCount = notiCount;
    }
    
    

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

   
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
