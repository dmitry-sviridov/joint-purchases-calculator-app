package ru.dkotsur.calculator.data.db.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.dkotsur.calculator.data.db.dao.ItemDao;
import ru.dkotsur.calculator.data.db.dao.PersonDao;
import ru.dkotsur.calculator.data.db.entity.Item;
import ru.dkotsur.calculator.data.db.entity.Person;

public class RepositorySelectedSession extends Repository{

    private long sessionId;

    private LiveData<List<Person>> allPersonsInSession;
    private LiveData<List<Item>> allItemsInSession;

    public RepositorySelectedSession(long sessionId) {
        this.sessionId = sessionId;
        sessionDao = db.sessionDao();
        personDao = db.personDao();
        personItemDao = db.personItemDao();
        itemDao = db.itemDao();

        allItemsInSession = itemDao.getSessionsItems(sessionId);
        allPersonsInSession = personDao.getPersonsFromSession(sessionId);
    }

    public LiveData<List<Person>> getPersonsFromSession(long sessionId) {
        return allPersonsInSession;
    }

    public LiveData<List<Item>> getSessionItems(long sessionId) {
        return allItemsInSession;
    }

    public void insertPerson(Person person) {
        new InsertPersonAsync(personDao).execute(person);
    }

    public void deletePerson(Person person) {
        new DeletePersonAsync(personDao).execute(person);
    }

    public List<Person> getPersonsList() throws ExecutionException, InterruptedException {
        return new PersonsListAsync(personDao).execute(sessionId).get();
    }

    public List<Item> getItemsList() throws ExecutionException, InterruptedException {
        return new ItemsListAsync(itemDao).execute(sessionId).get();
    }

    private static class PersonsListAsync extends AsyncTask<Long, Void, List<Person>> {
        private PersonDao personDao;

        public PersonsListAsync(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected List<Person> doInBackground(Long... longs) {
            return personDao.getPersonList(longs[0]);
        }
    }

    private static class ItemsListAsync extends AsyncTask<Long, Void, List<Item>> {
        private ItemDao itemDao;

        public ItemsListAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected List<Item> doInBackground(Long... longs) {
            return itemDao.getItemsList(longs[0]);
        }
    }

    private static class InsertPersonAsync extends AsyncTask<Person, Void, Void> {
        private PersonDao personDao;
        public InsertPersonAsync(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.insert(people[0]);
            return null;
        }
    }

    private static class DeletePersonAsync extends AsyncTask<Person, Void, Void>{
        private PersonDao personDao;
        public DeletePersonAsync(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... people) {
            personDao.delete(people[0]);
            return null;
        }
    }
}
