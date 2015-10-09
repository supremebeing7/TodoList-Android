package com.markjlehman.todolist.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Categories", id = "_id")
public class Category extends Model {

    @Column(name = "Name")
    public String mName;

    public Category() { super(); }

    public Category(String mName) {
        super();
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public static List<Category> all() {
        return new Select().from(Category.class).execute();
    }

    public List<Task> tasks() {
        return getMany(Task.class, "Category");
    }

    public static Category find(String name) {
        return new Select().from(Category.class).where("Name = ?", name).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(Category.class).execute();
    }

    public List<Task> finishedTasks() {
        return new Select().from(Task.class).where("Category = ? AND Finished = ?", this.getId(), true).execute();
    }
}
