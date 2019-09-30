package ru.dkotsur.calculator.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "persons_items",
        primaryKeys = {"item_id", "person_id"},
        foreignKeys = {@ForeignKey(entity = Person.class, parentColumns = "id", childColumns = "person_id", onDelete = CASCADE),
          @ForeignKey(entity = Item.class, parentColumns = "id", childColumns = "item_id", onDelete = CASCADE)})

public class PersonItem {
    @ColumnInfo(name = "item_id")
    private long itemId;
    @ColumnInfo(name = "person_id")
    private long personId;

    public PersonItem(long itemId, long personId) {
        this.itemId = itemId;
        this.personId = personId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
