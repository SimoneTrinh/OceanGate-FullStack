package model;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;

    public User(String firstName, String lastName, String username, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    // Getters và setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        if (phone != null && phone.matches("^[0-9]{9,15}$")) { // kiểm tra chỉ chứa số, độ dài từ 9–15
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
