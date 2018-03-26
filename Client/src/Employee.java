
public class Employee implements Comparable<Object>{
	
	private String firstName;
	private String lastName;
	private String department;
	private String phoneNumber;
	private String title;
	private String gender;
	
	public Employee(String firstName, String lastName, String department, String phoneNum, String title, String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.phoneNumber = phoneNum;
		this.title = title;
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return title+ " " +firstName + " " + lastName + " in " + department + " has phone number of " + phoneNumber + " and is " + gender;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Employee) {
			Employee other = (Employee) o;
			return lastName.compareTo(other.lastName);
		}
		return 0;
	}

}
