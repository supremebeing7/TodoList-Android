package com.markjlehman.todolist.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Tasks", id = "_id")

public class Task extends Model {
    @Column(name = "Description")
    public String mDescription;

    @Column(name = "Category")
    public Category mCategory;

    public Task() {
        super();
    }

    public Task(String mDescription, Category mCategory) {
        super();
        this.mDescription = mDescription;
        this.mCategory = mCategory;
    }

    public Category getmCategory() {
        return mCategory;
    }
    public String getmDescription() {
        return mDescription;
    }

    public void setmCategory(Category mCategory) {
        this.mCategory = mCategory;
    }
    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public static List<Task> all() {
        return new Select().from(Task.class).execute();
    }

    public static void deleteAll() {
        new Delete().from(Task.class).execute();
    }
}
