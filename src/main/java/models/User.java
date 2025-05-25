package models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime createdAt;

    // Constructor đầy đủ
    public User(String username, String password, String email, String firstName, String lastName, String phone) {
        this.username = username;
        this.password = password;
        setEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        setPhone(phone);
    }


    // === Getters and Setters ===
    public int getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null && email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            this.email = email;
        } else {
            System.out.println(email);
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        if (phone != null && phone.matches("^[0-9]{10,12}$")) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid phone number (10-12 digits required)");
        }
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
