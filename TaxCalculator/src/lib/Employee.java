package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private Gender gender;
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private Spouse spouse;
	private String spouseIdNumber;

	private List<Child> children = new LinkedList<>();

	public enum Gender {
		MALE, FEMALE
	}
	
	private static class Spouse {
		String name;
		String idNumber;
	
		Spouse(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}
	
	private static class Child {
		String name;
		String idNumber;
	
		Child(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, LocalDate joinDate, boolean isForeigner, Gender gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.joinDate = joinDate;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	 public void setMonthlySalary(int grade) {
		int baseSalary;
		switch (grade) {
			case 1: baseSalary = 3000000; break;
			case 2: baseSalary = 5000000; break;
			case 3: baseSalary = 7000000; break;
			default: baseSalary = 0; break;
		}
		this.monthlySalary = isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String name, String idNumber) {
		this.spouse = new Spouse(name, idNumber);
	}
	
	public void addChild(String name, String idNumber) {
		this.children.add(new Child(name, idNumber));
	}
	
	public int getAnnualIncomeTax() {
		int monthWorkingInYear = LocalDate.now().getYear() == joinDate.getYear()
			? LocalDate.now().getMonthValue() - joinDate.getMonthValue()
			: 12;
	
		boolean isMarried = spouse != null;
		int numberOfChildren = children.size();
	
		return TaxFunction.calculateTax(
			monthlySalary,
			otherMonthlyIncome,
			monthWorkingInYear,
			annualDeductible,
			isMarried,
			numberOfChildren
		);
	}
}
