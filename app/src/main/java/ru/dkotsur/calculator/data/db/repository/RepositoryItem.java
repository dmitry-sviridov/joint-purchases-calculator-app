package ru.dkotsur.calculator.data.db.repository;

import android.os.AsyncTask;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import kotlin.Triple;
import ru.dkotsur.calculator.data.db.dao.ItemDao;
import ru.dkotsur.calculator.data.db.dao.PersonDao;
import ru.dkotsur.calculator.data.db.dao.PersonItemDao;
import ru.dkotsur.calculator.data.db.entity.Item;
import ru.dkotsur.calculator.data.db.entity.Person;
import ru.dkotsur.calculator.data.db.entity.PersonItem;

public class RepositoryItem extends Repository {

    private long sessionId;
    private long itemId;
    private List<Long> allPersonsForItemId;
    private LiveData<List<Person>> allPersonsInSession;
    private LiveData<List<Person>> allPersonsForItem;

    public RepositoryItem(long sessionId) {
        this.sessionId = sessionId;
        personDao = db.personDao();
        personItemDao = db.personItemDao();
        itemDao = db.itemDao();
        allPersonsInSession = personDao.getPersonsFromSession(sessionId);
    }

    public RepositoryItem(long sessionId, long itemId) {
        this.itemId = itemId;
        this.sessionId = sessionId;
        personDao = db.personDao();
        personItemDao = db.personItemDao();
        itemDao = db.itemDao();
        allPersonsInSession = personDao.getPersonsFromSession(sessionId);
        allPersonsForItem = personItemDao.getAllPersonsForItem(itemId);
        allPersonsForItemId = personItemDao.getAllPersonsForItemId(itemId);

    }

    public LiveData<List<Person>> getPersonsForItem() {
        return allPersonsForItem;
    }

    public Person getPersonById(long personId) throws ExecutionException, InterruptedException {
         return new PersonByIdAsync(personDao).execute(personId).get();
    }

    public List<Person> getPersonsList(Long itemId) throws ExecutionException, InterruptedException {
        return new PersonsListAsync(personItemDao).execute(itemId).get();
    }

    public List<Long> getAllPersonsForItemId() {
        return allPersonsForItemId;
    }

    public Item getItemById(long itemId) throws ExecutionException, InterruptedException {
        return new GetItemById(itemDao).execute(itemId).get();
    }

    public void clearPersonItemForItem(long itemId) {
        new ClearPersonItemForItem(personItemDao).execute(itemId);
    }

    public LiveData<List<Person>> getPersonsFromSession() {
        return allPersonsInSession;
    }

    public Person getItemsBayer() throws ExecutionException, InterruptedException {
        Person result = new GetItemsBayer(itemDao).execute(itemId).get();
        return result;
    }

    public long insertItem(Item item) throws ExecutionException, InterruptedException {
        return new InsertItemAsync(itemDao).execute(item).get();
    }

    public void updateItem(Triple<String, Double, List<Long>> triple) {
        new UpdateItemAsync(itemDao).execute(triple);
    }

    public void deleteItem(Item item) {
        new DeleteItemAsync(itemDao).execute(item);
    }

    public void insertPersonsItems(Long itemId, List<Long> userIds) {
        Pair<Long, List<Long>> data = new Pair<>(itemId, userIds);
        new AddPersonsItemsAsync(personItemDao).execute(data);
    }

    private static class InsertItemAsync extends AsyncTask<Item, Void, Long> {
        private ItemDao itemDao;
        public InsertItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Long doInBackground(Item... items) {
            return itemDao.insert(items[0]);
        }
    }

    private static class DeleteItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        DeleteItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsync extends AsyncTask<Triple<String, Double, List<Long>>, Void, Void> {
        private ItemDao itemDao;
        UpdateItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Triple<String, Double, List<Long>>... triples) {
            itemDao.update(triples[0].component3().get(0), triples[0].component1(), triples[0].component2(), triples[0].component3().get(1));
            return null;
        }
    }

    private static class GetItemById extends AsyncTask<Long, Void, Item> {
        private ItemDao itemDao;

        GetItemById(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Item doInBackground(Long... longs) {
            return itemDao.getItemById(longs[0]);
        }
    }

    private static class AddPersonsItemsAsync extends AsyncTask<Pair<Long, List<Long>>, Void, Void> {

        private PersonItemDao personItemDao;

        AddPersonsItemsAsync(PersonItemDao personItemDao) {
            this.personItemDao = personItemDao;
        }
        @Override
        protected Void doInBackground(Pair<Long, List<Long>>... pairs) {
            long itemId = pairs[0].first;
            List<Long> usersIds = pairs[0].second;
            for (Long userId: usersIds) {
                personItemDao.insert(new PersonItem(itemId, userId));
            }
            return null;
        }
    }

    private static class GetItemsBayer extends AsyncTask<Long, Void, Person> {

        private ItemDao itemDao;

        public GetItemsBayer(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Person doInBackground(Long... longs) {
            return itemDao.getItemsBayer(longs[0]);
        }
    }

    private static class ClearPersonItemForItem extends AsyncTask<Long, Void, Void> {

        private PersonItemDao personItemDao;

        public ClearPersonItemForItem(PersonItemDao personItemDao) {
            this.personItemDao = personItemDao;
        }

        @Override
        protected Void doInBackground(Long... longs) {
            personItemDao.delete(longs[0]);
            return null;
        }
    }

    private static class PersonsListAsync extends AsyncTask<Long, Void, List<Person>> {
        private PersonItemDao personItemDao;

        public PersonsListAsync(PersonItemDao personItemDao) {
            this.personItemDao = personItemDao;
        }

        @Override
        protected List<Person> doInBackground(Long... longs) {
            return personItemDao.getPersonsList(longs[0]);
        }
    }

    private static class PersonByIdAsync extends AsyncTask<Long, Void, Person> {
        private PersonDao personDao;

        public PersonByIdAsync(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Person doInBackground(Long... longs) {
            return personDao.getPersonById(longs[0]);
        }
    }

}
