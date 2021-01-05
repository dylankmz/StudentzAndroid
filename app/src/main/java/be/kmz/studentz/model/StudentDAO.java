package be.kmz.studentz.model;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface StudentDAO {

    @Insert
    void insertPerson(Student s);

    @Update
    void updatePerson(Student s); //if primary key exists in table, update that row

    @Delete
    void deletePerson(Student s); //if primary key exists in table, delete that row;

    @Query("SELECT * FROM Student ORDER BY lastName")
    LiveData<List<Student>> getAllPersons();

    @Query("SELECT * FROM Student WHERE lastName LIKE :name")
    LiveData<List<Student>> findPersonsByName(String name);
}
