package ru.dkotsur.calculator.data.db.repository;

import ru.dkotsur.calculator.App;
import ru.dkotsur.calculator.data.db.CalculatorDatabase;
import ru.dkotsur.calculator.data.db.dao.ItemDao;
import ru.dkotsur.calculator.data.db.dao.PersonDao;
import ru.dkotsur.calculator.data.db.dao.PersonItemDao;
import ru.dkotsur.calculator.data.db.dao.SessionDao;

public abstract class Repository {

    CalculatorDatabase db = App.instance.getDatabase();
    SessionDao sessionDao;
    PersonDao personDao;
    PersonItemDao personItemDao;
    ItemDao itemDao;
}
