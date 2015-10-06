package com.markjlehman.todolist.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Tasks", id = "_id")

public class Task extends Model {
    @Column(name = "Description")
    public String mDescription;

    public Task() {
        super();
    }

    public Task(String mDescription) {
        super();
        this.mDescription = mDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public static List<Task> all() {
        return new Select().from(Task.class).execute();
    }
}
