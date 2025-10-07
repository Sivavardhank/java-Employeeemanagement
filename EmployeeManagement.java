import java.util.*;

// ---------- 1. Abstraction + Encapsulation ----------
abstract class Person {
    private String name;
    private int age;

    public Person(String name, int age) {  // Constructor
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    // Abstract method
    abstract void displayInfo();
}

// ---------- 2. Inheritance + Polymorphism ----------
class Employee extends Person {
    private int empId;
    private double salary;
    private String department;
    static int empCount = 0;  // static variable shared by all
    final double BONUS_PERCENT = 10.0; // final variable

    public Employee(String name, int age, int empId, double salary, String department) {
        super(name, age); // call parent constructor
        this.empId = empId;
        this.salary = salary;
        this.department = department;
        empCount++;
    }

    public int getEmpId() { return empId; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }

    // Method overriding (Polymorphism)
    @Override
    void displayInfo() {
        System.out.println(empId + " | " + getName() + " | " + getAge() +
                " | " + department + " | ₹" + salary);
    }

    // Method to calculate bonus (uses final variable)
    public double calculateBonus() {
        return salary * BONUS_PERCENT / 100;
    }

    // finalize() to demonstrate Garbage Collection
    @Override
    protected void finalize() {
        System.out.println("Employee object with ID " + empId + " destroyed by GC");
    }
}

// ---------- 3. Manager Class using Collections and Exception Handling ----------
class EmployeeManager {
    private ArrayList<Employee> list = new ArrayList<>();

    // Add Employee
    void addEmployee(Employee e) {
        list.add(e);
        System.out.println("✅ Employee added successfully!");
    }

    // Display All Employees
    void displayAll() {
        if (list.isEmpty()) {
            System.out.println("⚠️ No employees found.");
            return;
        }
        System.out.println("\n--- Employee List ---");
        for (Employee e : list)
            e.displayInfo();
    }

    // Search Employee
    void searchById(int id) {
        for (Employee e : list)
            if (e.getEmpId() == id) {
                System.out.println("\nEmployee found:");
                e.displayInfo();
                return;
            }
        System.out.println("❌ Employee not found!");
    }

    // Sort Employees by Salary
    void sortBySalary() {
        list.sort((a, b) -> Double.compare(b.getSalary(), a.getSalary()));
        System.out.println("✅ Sorted employees by salary (High → Low)");
    }

    // Delete Employee
    void deleteById(int id) {
        boolean removed = list.removeIf(e -> e.getEmpId() == id);
        if (removed)
            System.out.println("🗑️ Employee deleted successfully!");
        else
            System.out.println("❌ Employee not found!");
    }
}

// ---------- 4. Main Class ----------
public class EmployeeManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();

        while (true) {
            System.out.println("\n========== Employee Management System ==========");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Sort Employees by Salary");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Name: ");
                        String name = sc.next();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int id = sc.nextInt();
                        System.out.print("Enter Salary: ");
                        double sal = sc.nextDouble();
                        System.out.print("Enter Department: ");
                        String dept = sc.next();

                        Employee e = new Employee(name, age, id, sal, dept);
                        manager.addEmployee(e);
                    }

                    case 2 -> manager.displayAll();
                    case 3 -> {
                        System.out.print("Enter Employee ID to search: ");
                        manager.searchById(sc.nextInt());
                    }
                    case 4 -> manager.sortBySalary();
                    case 5 -> {
                        System.out.print("Enter Employee ID to delete: ");
                        manager.deleteById(sc.nextInt());
                    }
                    case 6 -> {
                        System.out.println("👋 Exiting system...");
                        System.gc(); // call Garbage Collector
                        System.exit(0);
                    }
                    default -> System.out.println("❌ Invalid choice! Try again.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: Invalid input. Try again.");
                sc.nextLine(); // clear buffer
            }
        }
    }
}
