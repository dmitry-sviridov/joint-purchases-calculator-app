package ru.dkotsur.calculator;

import android.app.Application;
import androidx.room.Room;
import ru.dkotsur.calculator.data.db.CalculatorDatabase;

public class App extends Application {

    public static App instance;

    private CalculatorDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, CalculatorDatabase.class, "calculator_db")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public CalculatorDatabase getDatabase() {
        return database;
    }
}
