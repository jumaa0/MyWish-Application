package DTOs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author husse
 */
public class RegisterDTO {
    
    String requesType;
    String username;
    String fullname;
    String phone;
    String email;
    String creditcard;
    int balance;
    String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String requesType, String username, String fullname, String phone, String email, String creditcard, int balance, String password) {
        this.requesType = requesType;
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.creditcard = creditcard;
        this.balance = balance;
        this.password = password;
    }

    public String getRequesType() {
        return requesType;
    }

    public void setRequesType(String requesType) {
        this.requesType = requesType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    
}
