package com.example.sweetapp.model;

public class UserOwnerModel {

    private String Uid, Email, Name, Password, PhoneNumber;

    public UserOwnerModel() {
    }

    public UserOwnerModel(String Uid, String Email, String Name, String Password, String PhoneNumber) {
        this.Uid = Uid;
        this.Email = Email;
        this.Name = Name;
        this.Password = Password;
        this.PhoneNumber = PhoneNumber;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        Name = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
}
