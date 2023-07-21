package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomepageScreen extends JFrame {
    private String currentUser;
    private JTable todoTable;

    public HomepageScreen(String currentUser) {
        this.currentUser = currentUser;
        setTitle("Homepage - " + currentUser);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser + "! \n Here's your TODO list: \n");
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Todo Table
        todoTable = new JTable(getTodoDataFromDatabase(), new String[] { "ID", "Title", "Note" });
        JScrollPane scrollPane = new JScrollPane(todoTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private Object[][] getTodoDataFromDatabase() {
        List<Todo> todoList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String databaseURL = "jdbc:mysql://localhost:3306/uas";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(databaseURL, username, password);

            int userId = 1;

            String query = "SELECT id, title, note FROM todo WHERE userId = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String note = rs.getString("note");

                todoList.add(new Todo(id, title, note));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Object[][] data = new Object[todoList.size()][3];
        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);
            data[i][0] = todo.getId();
            data[i][1] = todo.getTitle();
            data[i][2] = todo.getNote();
        }
        return data;
    }

    private class Todo {
        private int id;
        private String title;
        private String note;

        public Todo(int id, String title, String note) {
            this.id = id;
            this.title = title;
            this.note = note;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getNote() {
            return note;
        }
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         String currentUser = "John Doe"; 
    //         HomepageScreen homepage = new HomepageScreen(currentUser);
    //         homepage.setVisible(true);
    //     });
    // }
}
