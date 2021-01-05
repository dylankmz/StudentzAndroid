package be.kmz.studentz.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    public LiveData<List<Student>> students;
    private StudentDatabase db;
    private final Application myApp;

    public StudentViewModel(Application app) {
        super(app);
        myApp = app;
        db = StudentDatabase.getInstance(app);

        students = db.getStudentDAO().getAllStudents();
    }

    public void insertStudent(Student s){
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
