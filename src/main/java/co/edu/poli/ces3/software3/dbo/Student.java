package co.edu.poli.ces3.software3.dbo;
import java.time.LocalDate;

public class Student {

    private String name;
    private String lastname;
    private LocalDate birthDate;
    private String email;



    Student() {

    }


    public Student(String name, String lastname, LocalDate birthDate, String email) {
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void main(String[] args) {

    }

}