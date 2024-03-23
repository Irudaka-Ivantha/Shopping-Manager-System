public class User {
    private String User_Name;
    private String Password;
    public User(String user_Name, String password) {
        User_Name = user_Name;
        Password = password;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



}
