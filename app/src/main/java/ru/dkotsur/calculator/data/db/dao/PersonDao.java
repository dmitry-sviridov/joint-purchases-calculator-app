package ru.dkotsur.calculator.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.dkotsur.calculator.data.db.entity.Person;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("SELECT * FROM persons WHERE session_id=:sessionId")
    LiveData<List<Person>> getPersonsFromSession(long sessionId);

    @Query("SELECT COUNT(*) FROM persons WHERE session_id=:sessionId")
    LiveData<Integer> getPersonsCountFromSession(long sessionId);
}
