public class StudentTester {
    public static void main(String[] args) {
        Student s1 = new Student("Von Cabrera", "2024-1507", "BS Information Technology", 2, "2E");
        Student s2 = new Student("Jestony Pabio", "2024-2646", "BS Argibusiness Management", 2, "2H");
        Student s3 = new Student("Jockie Andaya", "2024-2197", "BS Biology", 2, "2B");
        Student s4 = new Student("Paula Dapitanon", "2024-0010 ", "ED English", 2, "2A");
        Student s5 = new Student("Jasmine Grace", "2025-0348", "ED Mathematics", 1, "1B");
        Student s6 = new Student("Simon Paul", "2023-3051", "BS Mathematics", 2, "2B");

        s1.displayInfo();
        s2.displayInfo(); 
        s3.displayInfo();
        s4.displayInfo();
        s5.displayInfo();
        s6.displayInfo();
    }
}
