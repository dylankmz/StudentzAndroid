package be.kmz.studentz.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import be.kmz.studentz.R;

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
            //ref: https://11zon.com/android/android_resource_values.php
            student.setGender(getApplication().getResources().getString(R.string.str_male));
            student.setEducation(getApplication().getResources().getString(R.string.str_it));
            student.setClassroom("A");
            sharedStudent = new MutableLiveData<>();
            sharedStudent.setValue(student);
        }
        return sharedStudent;
    }

    public void pickGender(String gender) {
        student.setGender(gender);
        sharedStudent.setValue(student);
    }

    public void pickEducation(String education) {
        student.setEducation(education);
        sharedStudent.setValue(student);
    }

    public void pickClassroom(String classroom) {
        student.setClassroom(classroom);
        sharedStudent.setValue(student);
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
