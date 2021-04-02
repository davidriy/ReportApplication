package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Department;
import entities.Employee;

public class DBServiceUtil {
	// Additional DB properties
	public static final String ADDITIONAL_SQL_CONFIGURATION = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
	public static final String GET_EMPLOYEES_SQL = "SELECT * FROM EMPLOYEES ORDER BY EMP_NO";
	public static final String GET_DEPARTMENTS_SQL = "SELECT * FROM DEPARTMENTS ORDER BY DEPT_NO";
	
	// Return the connection from the specified parameters
	public static Connection databaseConnect(String url, String user, String password) {
		Connection con = null;
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        }
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static boolean testConnection(String ip, int port, String username, String password) {
		String url = "jdbc:mysql://" + ip + ":" + port + "/employees" + ADDITIONAL_SQL_CONFIGURATION;
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();
        }
		try {
			DriverManager.getConnection(url, username, password);
			return true;
		} catch(SQLException e) {
			return false;
		}
	}
	// Acquires local employee Connection
	public static Connection getConnectionEmployees() {
		return getConnection("localhost",3306,"root","root");
	}
	// Acquires connection based on the given parameters 
	public static Connection getConnection(String ip, int port, String username, String password) {
		
		String url = "jdbc:mysql://" + ip + ":" + port + "/employees" + ADDITIONAL_SQL_CONFIGURATION;
		return databaseConnect(url, username, password);
	}
	// Obtains an ArrayList of employees
	public static ArrayList<Employee> getEmployeeList(){
		ArrayList<Employee> list = new ArrayList<Employee>();
		Employee employee = new Employee();
		try {
			Connection connection = getConnectionEmployees();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(GET_EMPLOYEES_SQL);
			while(rs.next()) {
				employee = new Employee(rs.getInt("EMP_NO"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"));
				list.add(employee);
			}
			connection.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	// Obtains an ArrayList of departments
	public static ArrayList<Department> getDepartmentList(){
		ArrayList<Department> list = new ArrayList<Department>();
		Department department;
		try {
			Connection connection = getConnectionEmployees();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(GET_DEPARTMENTS_SQL);
			while(rs.next()) {
				department = new Department(rs.getString("DEPT_NO"), rs.getString("DEPT_NAME"));
				list.add(department);
			}
			connection.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
}
