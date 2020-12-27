package com.example.cartravels;


public class PersonalInfo {
    private String FullName,Email,Gender,Password,UserType,CityName;
    private Long Contact;


    public String getFullName() {
        return FullName;
    }
    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }
    public void setGender(String  gender) {
        Gender = gender;
    }

    public Long getContact() {
        return Contact;
    }
    public void setContact(Long contact) {
        Contact = contact;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return UserType;
    }
    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getCityName() {
        return CityName;
    }
    public void setCityName(String cityName) {
        CityName = cityName;
    }
}
