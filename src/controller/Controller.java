package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;

public class Controller {
    private static Controller single_instance = null;

    public static synchronized Controller getInstance()
    {
        if (single_instance == null)
            single_instance = new Controller();
  
        return single_instance;
    }

    public User currentUser = null;

    DatabaseHandler conn = new DatabaseHandler();

    public ArrayList<User> getAllUsers() {
    ArrayList<User> users = new ArrayList<>();
    conn.connect();
    String query = "SELECT * FROM user";
    try {
        Statement stmt = conn.con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int userID = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int gender = rs.getInt("gender");
            Date birthday = rs.getDate("birthday");
            String photo = rs.getString("photo");
            // Assuming you have appropriate getters and setters in the User class
            User user = new User(userID, name, email, password, gender, birthday, photo);

            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}

}
