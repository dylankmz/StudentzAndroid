package be.kmz.studentz.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

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
    //eerst deed ik dit met een switch, maar aangezien ik 3 talen gebruik in mijn app, werkte
    //het niet meer met string values, met een if else zorg ik ervoor dat het dynamisch eendert
    //welke taal het gefilterd kan worden. switch pakt geen res.GetString() in zijn cases
    public LiveData<List<Student>> getStudents() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(myApp);
        Resources res = getApplication().getResources();
        String choice = preferences.getString("pref_order", res.getString(R.string.str_sort_education));
        if (res.getString(R.string.str_sort_education).equals(choice)) {
            students = db.getStudentDAO().getAllStudentsByEducation();
        } else if (res.getString(R.string.str_sort_classroom).equals(choice)) {
            students = db.getStudentDAO().getAllStudentsByClassroom();
        } else if (res.getString(R.string.str_sort_lastname).equals(choice)) {
            students = db.getStudentDAO().getAllStudentsByLastName();
        } else if (res.getString(R.string.str_sort_zip).equals(choice)) {
            students = db.getStudentDAO().getAllStudentsByZip();
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
