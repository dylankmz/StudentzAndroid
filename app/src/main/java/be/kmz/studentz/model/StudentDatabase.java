package be.kmz.studentz.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.kmz.studentz.model.util.DateConverters;

//nodige executors, threads, builder voor mijn RoomDatabase
//ref voorbeeld project van les
@Database(version = 1, entities = {Student.class}, exportSchema = false)
//@TypeConverters({DateConverters.class})
public abstract class StudentDatabase extends RoomDatabase {

    private static StudentDatabase instance;

    //nodig voor threads
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public static StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, StudentDatabase.class, "students.sqlite").allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract StudentDAO getStudentDAO();
}
