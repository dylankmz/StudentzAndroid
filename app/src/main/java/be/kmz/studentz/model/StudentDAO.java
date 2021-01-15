package be.kmz.studentz.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    void insertStudent(Student s);

    @Update
    void updateStudent(Student s);

    @Delete
    void deleteStudent(Student s);

    @Query("SELECT * FROM Student ORDER BY classroom")
    LiveData<List<Student>> getAllStudentsByClassroom();

    @Query("SELECT * FROM Student ORDER BY education")
    LiveData<List<Student>> getAllStudentsByEducation();

    @Query("SELECT * FROM Student ORDER BY lastName")
    LiveData<List<Student>> findStudentsByLastName();
}
