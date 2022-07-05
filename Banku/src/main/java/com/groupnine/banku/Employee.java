package com.groupnine.banku;

public class Employee {
    private String name;
    private String lastName;
    private String password;
    private String role;

    public boolean CheckPassword(String passwordTry) {
        boolean result = false;
        if (passwordTry.equals(this.password)) {
            result = true;
        }
        return result;
    }

    public void ChangePassword(String newPassword) {
        if (this.password.equals(newPassword) != true){
            this.password = newPassword;
        }
    }

    public Employee(String name, String lastName, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
