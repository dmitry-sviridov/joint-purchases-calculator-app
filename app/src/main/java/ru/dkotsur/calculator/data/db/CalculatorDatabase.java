package ru.dkotsur.calculator.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.dkotsur.calculator.data.db.dao.ItemDao;
import ru.dkotsur.calculator.data.db.dao.PersonDao;
import ru.dkotsur.calculator.data.db.dao.PersonItemDao;
import ru.dkotsur.calculator.data.db.dao.SessionDao;
import ru.dkotsur.calculator.data.db.entity.Item;
import ru.dkotsur.calculator.data.db.entity.Person;
import ru.dkotsur.calculator.data.db.entity.PersonItem;
import ru.dkotsur.calculator.data.db.entity.Session;

@Database(entities = {Item.class, Person.class, PersonItem.class, Session.class}, version = 1)
public abstract class CalculatorDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();
    public abstract PersonDao personDao();
    public abstract PersonItemDao personItemDao();
    public abstract SessionDao sessionDao();
}
