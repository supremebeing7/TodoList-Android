package com.markjlehman.todolist.ui;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
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

        populateTasks();

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

    private void populateTasks() {
        mTasks = new ArrayList<String>();
        for (Task task : mCategory.tasks() ) {
            mTasks.add(taskFullDescription(task));
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(mAdapter);
    }

    private String taskFullDescription(Task task) {
        if (task.getmFinished()) {
            return task.getmDescription() + " - Finished";
        } else {
            return task.getmDescription();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String taskDescription = mTasks.get(position);
        Task clickedTask = Task.find(taskDescription);
        finishTask(clickedTask, v, taskDescription);
    }

    private void finishTask(Task clickedTask, View v, String taskDescription) {
        clickedTask.setmFinished(true);
        clickedTask.save();
        TextView taskText = (TextView) v;
        taskText.setText(taskDescription + " - Finished");
        taskText.setBackgroundColor(Color.parseColor("#00ff00"));
        taskText.setTextColor(Color.parseColor("#ffffff"));
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
