JDBC - JAVA DATABASE CONNECTIVITY
![image](https://github.com/user-attachments/assets/61ea53de-c51c-4be6-ba48-aa0934641850)

jdbc acts as an interface to connect java to databse , which helps java to fetch data from the database


jdbc contains 
Driver 
- which connects the java to database
- it contains the classpath (com.mysql.cj.jdbc.Driver)
DriverManager
- contains url,username,password (jdbc:mysql://localhost:3306/db_name,root,root);
Connection
- which will helps drivermanger to connect to the db
Statement
- helps to create a sql statement
- eg (Select * from table_name)
ResultSet
- helps to fetch data from db using the method like(getString(),getInt(),etc)

Code
package model;

import java.sql.*;

public class jdbcclass {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("select * from department");
            while(rs.next()) {
            	System.out.println(rs.getString(2));
            }
            connect.close();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }
}

There are 3 ways to store the url,username and password
1)  String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "root";
2) Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/college, "root", "root");
3) add all these datas in one file and fetch it from there
   ![Screenshot 2024-12-27 112600](https://github.com/user-attachments/assets/832277df-e46a-44fe-b07e-8e773ab60dd8)

                              3 layers
                         Model , DAO , Service
   
1)model


- A model class is a plain Java object (POJO) used to represent data. 
- A class which represents data object which can be used for transferring data in java application. 
-It encapsulates direct access to data in object and ensures all data in object is accessed via getter methods

eg code

public class Student {
    private int id;
    private String name;
    private int age;

    // Default constructor
    public Student() {}

    // Parameterized constructor
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for 'age'
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}



2)DAO  - Data Access Object


-The UserDAO class provides methods to interact with the database, such as createUser, getUserById, etc.
-It uses a Connection object to establish a connection to the database.
-SQL queries are used to perform CRUD (Create, Read, Update, Delete) operations on the users table.
-The User class represents the data model for a user.
eg code
import java.sql.*;

public class UserDAO {
    private Connection connection;

    // Constructor to initialize the database connection
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new user in the database
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        }
    }

    // Method to retrieve a user by their ID
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                    );
                }
            }
        }
        return null;
    }

    // Other methods for updating, deleting, and listing users
}



3)Service
- a service class is a class that encapsulates a specific business logic or functionality.
-  It acts as a layer between the controller and the data access layer, providing a clear separation of concerns and making your code more maintainable and testable.
-> Statelessness:
      - Ideally, service classes should be stateless, meaning they don't store any data specific to a particular request.
      - This ensures thread safety and simplifies testing.


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Validation logic
        // ...

        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Other business logic methods
}


Flow of the Project When Fetching Data

User (Console Input)
   ↓
Main Class (Invokes Controller)
   ↓
Controller (StudentController)  
   ↓
Service Layer (StudentService)
   ↓
DAO Layer (StudentDAO)  
   ↓
Database (Executes SQL Query)
   ↑
DAO (Converts ResultSet to List<Student>)
   ↑
Service (Processes Data if Necessary)
   ↑
Controller (Formats Data for Display)
   ↑
User (Console Output) 


Sample code






