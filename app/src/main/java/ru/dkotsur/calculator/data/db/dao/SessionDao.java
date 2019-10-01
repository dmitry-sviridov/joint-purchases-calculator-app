package ru.dkotsur.calculator.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.dkotsur.calculator.data.db.entity.Session;

@Dao
public interface SessionDao {

    @Insert
    void insert(Session session);

    @Update
    void update(Session session);

    @Delete
    void delete(Session session);

    @Query("DELETE FROM sessions")
    void deleteAll();

    @Query("SELECT * FROM sessions")
    LiveData<List<Session>> getAllSessions();

    @Query("SELECT * FROM sessions WHERE id= :id")
    Session getSessionById(long id);
}
