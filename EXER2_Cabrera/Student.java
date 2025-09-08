public class Student {
    String name;
    String id;
    String course;
    int yearLevel;
    String section;

    public Student() {
        this.name = "No Name";
        this.id = "No ID";
        this.course = "No Course";
        this.yearLevel = 0;
        this.section = "No Section";
    }

    public Student(String name, String id, String course, int yearLevel, String section) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.yearLevel = yearLevel;
        this.section = section;
    }

    public void displayInfo() {
        String info = "";
        info += "Name: " + this.name;
        info += "\nID: " + this.id;
        info += "\nCourse: " + this.course;
        info += "\nYear Level: " + this.yearLevel;
        info += "\nSection: " + this.section;
        System.out.println(info);
        System.out.println("----------------------");
    }
}
