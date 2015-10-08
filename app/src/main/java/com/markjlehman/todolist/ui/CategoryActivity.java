package com.markjlehman.todolist.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.markjlehman.todolist.R;
import com.markjlehman.todolist.models.Category;
import com.markjlehman.todolist.models.Task;

import java.util.ArrayList;

public class CategoryActivity extends ListActivity {
    public static String TAG = CategoryActivity.class.getSimpleName();

    private Category mCategory;
    private ArrayList<String> mTasks;
    private Button mTaskButton;
    private EditText mNewTaskText;
    private ArrayAdapter<String> mAdapter;
    private TextView mEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String name = getIntent().getStringExtra("categoryName");
        mCategory = Category.find(name);

        mTaskButton = (Button) findViewById(R.id.addTaskButton);
        mNewTaskText = (EditText) findViewById(R.id.addTaskField);

        mTasks = new ArrayList<String>();
        for (Task task : mCategory.tasks() ) {
            mTasks.add(task.getmDescription());
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(mAdapter);
        mEmpty = (TextView) findViewById(R.id.empty);
        if (mTasks.size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
        mTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String taskDescription = mTasks.get(position);
        Task clickedTask = Task.find(taskDescription);
        clickedTask.delete();
        TextView taskText = (TextView) v;
        taskText.setText(taskDescription + " - Deleted");
        taskText.setBackgroundColor(Color.parseColor("#ff0000"));
        taskText.setTextColor(Color.parseColor("#ffffff"));
        if (mTasks.size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void addTask() {
        String description = mNewTaskText.getText().toString();
        Task newTask = new Task(description, mCategory);
        newTask.save();
        mTasks.add(description);
        mNewTaskText.setText("");
        mEmpty.setVisibility(View.INVISIBLE);
        mNewTaskText.clearFocus();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
