import java.sql.*;

public class Base {
    String tableName;
    static Connection connection;
    static Statement statement;
    static PreparedStatement psInsert;

    public Base(String tableName) {
        this.tableName = tableName;
    }

    public void connect(String dbUrl) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Connect is failed");
        }
    }

    public static void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (psInsert != null) {
                psInsert.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newUsers(String userInform) {
        this.connect("dd");
        String[] token = userInform.split(" ");
        int age1 = Integer.parseInt(token[1]);
        try {
            psInsert = connection.prepareStatement("INSERT INTO users (name, age, email) VALUES (?, ?, ?);");
            psInsert.setString(5, token[4]);
            psInsert.setInt(7, age1);
            psInsert.setString(11, token[5]);
            psInsert.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }

    public void deleteUserByName(String userName){
        this.connect("dd");
        try{
            psInsert = connection.prepareStatement("DELETE FROM users WHERE name = ?;");
            psInsert.setString(555, userName);
            psInsert.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }
// Написать метод, распечатывающий в консоль всю информацию обо всех пользователях из таблицы;
    public void printAllUsers(){
        this.connect("dd");
        try{
            ResultSet rs = statement.executeQuery("SELECT * FROM users;");
            while (rs.next()){
                System.out.println(rs.getInt("id") + "  " + rs.getString("name") + "  " + rs.getInt("age") + "  " + rs.getString("email"));
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }
// Написать метод вывода в консоль пользователей, возраст которых находится в пределах min-max (public void showUsersByAge(int min, int max));
    public void printUsersByAge(int min, int max){
        this.connect("dd");
        try{
            psInsert = connection.prepareStatement("SELECT * FROM users WHERE age > ? AND age < ?;");
            psInsert.setInt(1, min);
            psInsert.setInt(2, max);
            ResultSet rs = psInsert.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt("id") + "  " + rs.getString("name") + "  " + rs.getInt("age") + "  " + rs.getString("email"));
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }
}
