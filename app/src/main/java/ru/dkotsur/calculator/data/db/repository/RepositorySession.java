package ru.dkotsur.calculator.data.db.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.dkotsur.calculator.data.db.dao.SessionDao;
import ru.dkotsur.calculator.data.db.entity.Session;

public class RepositorySession extends Repository{

    private final String TAG = RepositorySession.class.getSimpleName();

    private LiveData<List<Session>> allSessions;

    public RepositorySession() {
        sessionDao = super.db.sessionDao();
        allSessions = sessionDao.getAllSessions();
    }

    public void insert(Session session) {
        new InsertSessionAsyncTask(sessionDao).execute(session);
        Log.e(TAG, "Inserting..");
    }

    public void delete(Session session) {
        new DeleteSessionAsyncTask(sessionDao).execute(session);
    }

    public void deleteAll() {
        new DeleteAllAsync(sessionDao).execute();
    }

    public Session getById(long id) throws ExecutionException, InterruptedException {
        return new GetSessionById(sessionDao).execute(Long.valueOf(id)).get();
    }

    public LiveData<List<Session>> getAllSessions() {
        Log.e(TAG, "Taking all sessions from db");
        return allSessions;
    }

    private static class InsertSessionAsyncTask extends AsyncTask<Session, Void, Void> {
        private SessionDao sessionDao;

        public InsertSessionAsyncTask(SessionDao sessionDao) {
            this.sessionDao = sessionDao;
        }

        @Override
        protected Void doInBackground(Session... sessions) {
            sessionDao.insert(sessions[0]);
            return null;
        }
    }

    private static class DeleteSessionAsyncTask extends AsyncTask<Session, Void, Void> {
        private SessionDao sessionDao;

        public DeleteSessionAsyncTask(SessionDao sessionDao) {
            this.sessionDao = sessionDao;
        }

        @Override
        protected Void doInBackground(Session... sessions) {
            sessionDao.delete(sessions[0]);
            return null;
        }
    }

    private static class GetSessionById extends AsyncTask<Long, Void, Session> {
        private SessionDao sessionDao;

        public GetSessionById(SessionDao sessionDao) {
            this.sessionDao = sessionDao;
        }

        @Override
        protected Session doInBackground(Long... longs) {
            return sessionDao.getSessionById(longs[0]);
        }
    }

    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void> {
        private SessionDao sessionDao;

        public DeleteAllAsync(SessionDao sessionDao) {
            this.sessionDao = sessionDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sessionDao.deleteAll();
            return null;
        }
    }

}
