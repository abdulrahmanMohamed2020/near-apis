package model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String message;
    private Map<String, Object> userData = new HashMap<>();

    public User() {
    }

    // used for post request
    public User(String fullName, String walletName, String email, String phone) {
        userData.put("fullName", fullName);
        userData.put("walletName", walletName);
        userData.put("email", email);
        userData.put("phone", phone);
    }

    // used for put request
    public User(String full_name) {
        userData.put("full_name", full_name);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return userData;
    }

    public void setData(Map<String, Object> data) {
        this.userData = data;
    }
}
