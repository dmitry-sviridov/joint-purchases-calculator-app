package ru.dkotsur.calculator.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.dkotsur.calculator.data.db.entity.Person;
import ru.dkotsur.calculator.data.db.entity.PersonItem;

@Dao
public interface PersonItemDao {

    @Insert
    void insert(PersonItem personItem);

    @Query("SELECT * FROM persons INNER JOIN persons_items ON " +
            "persons.id=persons_items.person_id WHERE persons_items.item_id = :itemId")
    LiveData<List<Person>> getAllPersonsForItem(final long itemId);

    @Query("SELECT person_id FROM persons_items WHERE item_id=:itemId")
    List<Long> getAllPersonsForItemId(final long itemId);

    @Query("DELETE FROM persons_items WHERE item_id=:itemId")
    void delete(long itemId);

}
