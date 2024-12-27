import java.sql.*;
public class App {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "root";
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connect = DriverManager.getConnection(url,username,password);
        Statement st = connect.createStatement();
        System.out.println("Database Connected Successfully");
        ResultSet rs = st.executeQuery("Select * from department");
        while(rs.next()){
            System.out.println(rs.getString(0)+" "+rs.getString(1));
        }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
