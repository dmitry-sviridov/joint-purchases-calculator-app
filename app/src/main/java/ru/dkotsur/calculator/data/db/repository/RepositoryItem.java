package ru.dkotsur.calculator.data.db.repository;

import android.os.AsyncTask;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.dkotsur.calculator.data.db.dao.ItemDao;
import ru.dkotsur.calculator.data.db.dao.PersonItemDao;
import ru.dkotsur.calculator.data.db.entity.Item;
import ru.dkotsur.calculator.data.db.entity.Person;
import ru.dkotsur.calculator.data.db.entity.PersonItem;

public class RepositoryItem extends Repository {

    private long sessionId;
    private LiveData<List<Person>> allPersonsInSession;
    private LiveData<List<Person>> allPersonsForItem;

    public RepositoryItem(long sessionId) {
        this.sessionId = sessionId;
        personDao = db.personDao();
        personItemDao = db.personItemDao();
        itemDao = db.itemDao();
        allPersonsInSession = personDao.getPersonsFromSession(sessionId);
    }

    public Item getItemById(long sessionId) throws ExecutionException, InterruptedException {
        return new GetItemById(itemDao).execute(Long.valueOf(sessionId)).get();
    }
    public LiveData<List<Person>> getPersonsFromSession(long sessionId) {
        return allPersonsInSession;
    }

    public long insertItem(Item item) throws ExecutionException, InterruptedException {
        return new InsertItemAsync(itemDao).execute(item).get();
    }
    public void updateItem(Item item) {
        new UpdateItemAsync(itemDao).execute(item);
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

    private static class UpdateItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        UpdateItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
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

}
