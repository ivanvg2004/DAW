class Employee {
    String name;
    String address;

    public Employee(String name, String address) {
        this.name = name;
        this.address = address;
    }
}

class Report {
    void print(Employee e) {
        System.out.println(e.name + " - " + e.address);
    }
}

class Main {
    public static void main(String[] args) {
        Employee e = new Employee("Ana", "Calle Mayor 5");
        new Report().print(e);
    }
}
