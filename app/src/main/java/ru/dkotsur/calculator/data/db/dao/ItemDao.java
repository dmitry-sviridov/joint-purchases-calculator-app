package ru.dkotsur.calculator.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.dkotsur.calculator.data.db.entity.Item;

@Dao
public interface ItemDao {

    @Insert
    long insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items WHERE session_id=:session_id")
    LiveData<List<Item>> getSessionsItems(long session_id);

    @Query("SELECT COUNT(*) FROM items WHERE session_id=:session_id")
    LiveData<Integer> getSessionsItemsCount(long session_id);

    @Query("SELECT * FROM items WHERE id=:id")
    Item getItemById(long id);
}
