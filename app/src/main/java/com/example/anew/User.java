package com.example.anew;

public class User {
    private String Name;
    private String Phone;
    private String Niveau;
    private String Email;
    private String Password;

    public User() {
    }
    public User(String name, String phone, String niveau , String Email) {
        Name = name;
        Phone = phone;
        this.Niveau = Niveau;
        this.Email = Email ;
        this.Password = Password;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        this.Niveau = Niveau;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}

