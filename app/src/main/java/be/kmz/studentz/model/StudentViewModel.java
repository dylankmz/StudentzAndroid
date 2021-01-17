package be.kmz.studentz.model;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import be.kmz.studentz.R;

//viewmodel klasse
public class StudentViewModel extends AndroidViewModel {

    public LiveData<List<Student>> students;
    private Student student;
    private StudentDatabase db;
    //constante variabele, value kan niet gewijzigd worden
    private final Application myApp;
    //mutablelivedata wordt gebruikt als je data wilt wijzigen, met livedata is dit niet mogelijk
    private MutableLiveData<Student> sharedStudent;


    public StudentViewModel(Application app) {
        super(app);
        myApp = app;
        db = StudentDatabase.getInstance(app);
        students = db.getStudentDAO().getAllStudentsByClassroom();
    }


    //sharedStudent om default waarden te kunnen hebben
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

    //getStudents met preferences, ik roep de juiste querys hiervoor, hulp van prof gekregen
    public LiveData<List<Student>> getStudents() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(myApp);
        String choice = preferences.getString("pref_order", "Sort on education");
        switch (choice) {
            case "Sort on education": students = db.getStudentDAO().getAllStudentsByEducation();
            break;
            case "Sort on classroom": students = db.getStudentDAO().getAllStudentsByClassroom();
            break;
            case "Sort on last name(Alphabetical)": students = db.getStudentDAO().findAllStudentsByLastName();
            break;
            case "Sort on zip": students = db.getStudentDAO().findAllStudentsByZip();
            break;
        }
        return students;
    }

    //insert
    public void insertStudent(Student s) {
        StudentDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getStudentDAO().insertStudent(s);
            }
        });
    }

    //update
    public void updateStudent(Student s){
        StudentDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getStudentDAO().updateStudent(s);
            }
        });
    }

    //delete
    public void deleteStudent(Student s){
        StudentDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getStudentDAO().deleteStudent(s);
            }
        });
    }
}
