package ru.dkotsur.calculator.data.db.entity;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "items",
        foreignKeys = {@ForeignKey(entity = Session.class, parentColumns = "id", childColumns = "session_id", onDelete = CASCADE),
                       @ForeignKey(entity = Person.class, parentColumns = "id", childColumns = "person_id", onDelete = CASCADE)})

public class Item {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private double cost;

    @ColumnInfo(name = "person_id")
    private long bayerId;

    @ColumnInfo(name = "session_id")
    private long sessionId;

    @Ignore
    public ArrayList<Long> users;

    public Item(String title, double cost, long bayerId, long sessionId) {
        this.title = title;
        this.cost = cost;
        this.bayerId = bayerId;
        this.sessionId = sessionId;
        users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getBayerId() {
        return bayerId;
    }

    public void setBayerId(long bayerId) {
        this.bayerId = bayerId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    /* CALCULATION */

    public boolean addUser(Long id) {
        if (!users.contains(id)) {
            users.add(id);
            return true;
        }
        return false;
    }

    public void addUsers(List<Long> ids) {
        for (Long id: ids) {
            addUser(id);
        }
    }

    public Double getUnitCost() {
        BigDecimal count = BigDecimal.valueOf(users.size(), 0);
        BigDecimal result = BigDecimal.valueOf(cost).divide(count, 2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }
}
