package be.kmz.studentz.model;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class StudentDatabase extends RoomDatabase {

    private static StudentDatabase instance;

    //nodig voor threads
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public static StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, StudentDatabase.class, "students.sqlite").build();
        }
        return instance;
    }

    public abstract StudentDAO getStudentDAO();
}
