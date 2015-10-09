package com.markjlehman.todolist.ui;

import android.app.ListActivity;
import android.content.Intent;
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

public class FinishedTasksActivity extends ListActivity {
    public static String TAG = FinishedTasksActivity.class.getSimpleName();

    private Category mCategory;
    private ArrayList<String> mTasks;
    private ArrayAdapter<String> mAdapter;
    private TextView mEmpty;
    private Button mAllTasksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_tasks);

        String name = getIntent().getStringExtra("categoryName");
        mCategory = Category.find(name);

        populateTasks();

        mEmpty = (TextView) findViewById(R.id.empty);
        if (mTasks.size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }

        mAllTasksButton = (Button) findViewById(R.id.allTasksButton);
        mAllTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllTasks();
            }
        });
    }

    private void viewAllTasks() {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("categoryName", mCategory.getmName());
        startActivity(intent);
    }

    private void populateTasks() {
        mTasks = new ArrayList<String>();
        for (Task task : mCategory.finishedTasks() ) {
            mTasks.add(task.getmDescription());
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String taskDescription = mTasks.get(position);
        Task clickedTask = Task.find(taskDescription);
        unfinishTask(clickedTask, taskDescription);
    }

    private void unfinishTask(Task clickedTask, String taskDescription) {
        clickedTask.setmFinished(false);
        clickedTask.save();
        mTasks.remove(taskDescription);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.finished_tasks, menu);
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
