package com.nishith.reserveShow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
    @Column(unique = true,nullable = false)
    @NotBlank(message = "Username must not be Blank")
    @Size(min = 3,max = 12,message = "Username must stay in range of 3 to 12")
    private String username;
    @Column(nullable = false)
    @NotBlank(message = "Password must not be Blank")
    @Size(min=6,message = "Password must contains range of 6")
        private String password;
    @Column(nullable = false)
        private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username +
                ", role: " +role +
                '}';
    }
}
