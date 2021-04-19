package entities;
/*
 * Employee class
 */
public class Employee {
	private Integer empNo;
	private String firstName;
	private String lastName;
	
	public Employee() {
		super();
	}
	public Employee(Integer empNo, String firstName, String lastName) {
		super();
		this.empNo = empNo;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public Integer getEmpNo() {
		return empNo;
	}
	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return empNo + " - " + firstName + " " + lastName;
	}
}
