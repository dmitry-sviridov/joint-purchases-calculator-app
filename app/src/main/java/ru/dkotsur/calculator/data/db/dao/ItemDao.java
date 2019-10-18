package ru.dkotsur.calculator.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.dkotsur.calculator.data.db.entity.Item;
import ru.dkotsur.calculator.data.db.entity.Person;

@Dao
public interface ItemDao {

    @Insert
    long insert(Item item);

    @Query("UPDATE items SET title=:title, cost=:cost, person_id=:bayerId WHERE id=:id")
    void update(long id, String title, Double cost, long bayerId);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items WHERE session_id=:session_id")
    LiveData<List<Item>> getSessionsItems(long session_id);

    @Query("SELECT * FROM items WHERE session_id=:session_id")
    List<Item> getItemsList(long session_id);

    @Query("SELECT COUNT(*) FROM items WHERE session_id=:session_id")
    LiveData<Integer> getSessionsItemsCount(long session_id);

    @Query("SELECT * FROM items WHERE id=:id")
    Item getItemById(long id);

    @Query("SELECT persons.id, persons.name, persons.session_id " +
            "FROM " +
            "persons INNER JOIN items " +
            "ON persons.id=items.person_id " +
            "WHERE items.id =:itemId")
    Person getItemsBayer(long itemId);
}
