package be.kmz.studentz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.threeten.bp.LocalDate;

import java.io.Serializable;
import java.util.Arrays;

@Entity
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long Id;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private String[] education;
    private String[] classroom;
    private String email;
    private String address;
    private String location;
    private String zip;

    public Student() {
    }

    public Student(long id, String firstName, String lastName, String gender, LocalDate birthDate, String[] education, String[] classroom, String email, String address, String location, String zip) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.education = education;
        this.classroom = classroom;
        this.email = email;
        this.address = address;
        this.location = location;
        this.zip = zip;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String[] getEducation() {
        return education;
    }

    public void setEducation(String[] education) {
        this.education = education;
    }

    public String[] getClassroom() {
        return classroom;
    }

    public void setClassroom(String[] classroom) {
        this.classroom = classroom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
