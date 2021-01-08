package be.kmz.studentz.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    public LiveData<List<Student>> students;
    private Student student;
    private StudentDatabase db;
    private final Application myApp;
    private MutableLiveData<Student> sharedStudent;

    public StudentViewModel(Application app) {
        super(app);
        myApp = app;
        db = StudentDatabase.getInstance(app);
        students = db.getStudentDAO().getAllStudents();
    }

    public MutableLiveData<Student> getSharedStudent() {
        if (sharedStudent == null) {
            student = new Student();
            student.setGender("Male");
            student.setEducation("IT");
            student.setClassroom("A");
            sharedStudent = new MutableLiveData<>();
            sharedStudent.setValue(student);
        }
        return sharedStudent;
    }

    public void pickGender(String gender) {
        student.setGender(gender);
    }

    public void pickEducation(String education) {
        student.setEducation(education);
    }

    public void pickClassroom(String classroom) {
        student.setClassroom(classroom);
    }

    public LiveData<List<Student>> getStudents() {
        students = db.getStudentDAO().getAllStudents();
        return students;
    }

    public void insertStudent(Student s) {
        StudentDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getStudentDAO().insertStudent(s);
            }
        });
    }

    public void updateStudent(Student s){
        StudentDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getStudentDAO().updateStudent(s);
            }
        });
    }

    public void deleteStudent(Student s){
        StudentDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getStudentDAO().deleteStudent(s);
            }
        });
    }
}
