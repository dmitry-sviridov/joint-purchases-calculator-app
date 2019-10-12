package ru.dkotsur.calculator.data.db.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.dkotsur.calculator.data.db.dao.ItemDao;
import ru.dkotsur.calculator.data.db.entity.Item;
import ru.dkotsur.calculator.data.db.entity.Person;

public class RepositoryAddItem extends Repository {

    private long sessionId;
    private LiveData<List<Person>> allPersonsInSession;
    private LiveData<List<Person>> allPersonsForItem;

    public RepositoryAddItem(long sessionId) {
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
    public void insertItem(Item item) {
        new InsertItemAsync(itemDao).execute(item);
    }
    public void updateItem(Item item) {
        new UpdateItemAsync(itemDao).execute(item);
    }
    public void deleteItem(Item item) {
        new DeleteItemAsync(itemDao).execute(item);
    }

    private static class InsertItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        public InsertItemAsync(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsync extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        public DeleteItemAsync(ItemDao itemDao) {
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
        public UpdateItemAsync(ItemDao itemDao) {
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

        public GetItemById(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Item doInBackground(Long... longs) {
            return itemDao.getItemById(longs[0]);
        }
    }

}
