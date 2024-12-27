# JDBC - JAVA DATABASE CONNECTIVITY
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

  package model;

import java.sql.*;
import java.util.*;
import java.io.*;
public class jdbcclass2 {
	public static void main(String[] main) {
	try {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("src\\DB.Properties");
		prop.load(input);
		String driver_name = prop.getProperty("dbDriver_name");
		String url = prop.getProperty("dbUrl");
		String u_name = prop.getProperty("dbuser");
		String password = prop.getProperty("dbpassword");
		Class.forName(driver_name);
		Connection connect = DriverManager.getConnection(url,u_name,password);
		Statement st = connect.createStatement();
		ResultSet rs = st.executeQuery("select * from department");
		while(rs.next()) {
			System.out.println(rs.getInt(1)+" "+rs.getString(2));
		}
//		insertion();
		 
	}catch(Exception e) {
		System.out.println(e);
	}
//	private void insertion() {
//		// TODO Auto-generated method stub
//		
	}

}
DB.Properties
dbDriver_name = com.mysql.cj.jdbc.Driver
dbUrl = jdbc:mysql://localhost:3306/company
dbuser = root
dbpassword = root

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


![image](https://github.com/user-attachments/assets/bcf5e8bd-207e-4b31-b954-44295532fa3d)



Sample code
Prerequisites
MySQL server running.
A database named testdb.
A table named students:
CREATE DATABASE testdb;

USE testdb;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    age INT
);

# Model Class  

public class Student {
    private int id;
    private String name;
    private int age;

    // Constructors
    public Student() {}

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}
# DAO Class

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/school";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private Connection connection;

    // Constructor: Initialize the database connection
    public StudentDAO() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a student
    public void addStudent(Student student) {
        String query = "INSERT INTO students (name, age) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Close connection
    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

# Service Class

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
        studentDAO = new StudentDAO();
    }

    // Add a student
    public void addStudent(Student student) {
        if (student.getName() != null && student.getAge() > 0) {
            studentDAO.addStudent(student);
        } else {
            throw new IllegalArgumentException("Invalid student data");
        }
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // Close DAO connection
    public void closeService() {
        studentDAO.closeConnection();
    }
}

# Controller Class

import java.util.List;

public class StudentController {
    private StudentService studentService;

    public StudentController() {
        studentService = new StudentService();
    }

    // Add a student
    public void addStudent(Student student) {
        studentService.addStudent(student);
        System.out.println("Student added successfully.");
    }

    // Display all students
    public void displayStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Close the service
    public void closeController() {
        studentService.closeService();
    }
}

# Main class 

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentController controller = new StudentController();
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n1. Add Student");
                System.out.println("2. Display All Students");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.next();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        controller.addStudent(new Student(0, name, age));
                        break;
                    case 2:
                        controller.displayStudents();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        controller.closeController();
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } finally {
            controller.closeController();
        }
    }
}


