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

    @Column(name = "Finished")
    public boolean mFinished;

    public Task() {
        super();
    }

    public Task(String mDescription, Category mCategory) {
        super();
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mFinished = false;
    }

    public Category getmCategory() {
        return mCategory;
    }
    public String getmDescription() {
        return mDescription;
    }
    public boolean getmFinished() {
        return mFinished;
    }

    public void setmCategory(Category mCategory) {
        this.mCategory = mCategory;
    }
    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public void setmFinished(boolean mFinished) {
        this.mFinished = mFinished;
    }

    public static List<Task> all() {
        return new Select().from(Task.class).execute();
    }

    public static void deleteAll() {
        new Delete().from(Task.class).execute();
    }

    public static Task find(String taskDescription) {
        return new Select().from(Task.class).where("Description = ?", taskDescription).executeSingle();
    }
}
