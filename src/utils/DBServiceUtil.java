package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Configuration;
import entities.Department;
import entities.Employee;

public class DBServiceUtil {
	// Additional DB properties
	public static final String ADDITIONAL_SQL_CONFIGURATION = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
	public static final String GET_EMPLOYEES_SQL = "SELECT * FROM employees ORDER BY emp_no";
	public static final String GET_DEPARTMENTS_SQL = "SELECT * FROM departments ORDER BY dept_no";
	
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
	public static ArrayList<Employee> getEmployeeList(Configuration configuration){
		ArrayList<Employee> list = new ArrayList<Employee>();
		Employee employee = new Employee();
		try {
			Connection connection = getConnection(configuration.getIp(), Integer.valueOf(configuration.getPort()), configuration.getUsername(), configuration.getPassword());
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(GET_EMPLOYEES_SQL);
			while(rs.next()) {
				employee = new Employee(rs.getInt("emp_no"), rs.getString("first_name"), rs.getString("last_name"));
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
	public static ArrayList<Department> getDepartmentList(Configuration configuration){
		ArrayList<Department> list = new ArrayList<Department>();
		Department department;
		try {
			Connection connection = getConnection(configuration.getIp(), Integer.valueOf(configuration.getPort()), configuration.getUsername(), configuration.getPassword());
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(GET_DEPARTMENTS_SQL);
			while(rs.next()) {
				department = new Department(rs.getString("dept_no"), rs.getString("dept_name"));
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
