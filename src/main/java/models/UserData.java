package models;

public class UserData extends User {
    private String idUser;

    public UserData(String idUser, String username, String password, String email, String firstName, String lastName, String phone) {
        super(username, password, email, firstName, lastName, phone);
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String id) {
        this.idUser = id;
    }

}
