package com.markjlehman.todolist.ui;

import android.app.Activity;
import android.app.ListActivity;
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
import com.markjlehman.todolist.models.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends ListActivity {

    private ArrayList<String> mTasks = new ArrayList<String>();
    private Button mTaskButton;
    private EditText mNewTaskText;
    private ArrayAdapter<String> mAdapter;
    private TextView mEmpty;
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskButton = (Button) findViewById(R.id.addTaskButton);
        mNewTaskText = (EditText) findViewById(R.id.addTaskField);
        mTasks = new ArrayList<String>();
        for (Task task : Task.all() ) {
            mTasks.add(task.getmDescription());
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(mAdapter);
        mEmpty = (TextView) findViewById(R.id.empty);
        mList = (ListView) findViewById(R.id.list);
//        if (mTasks.size() == 0) {
//            mList.setVisibility(View.INVISIBLE);
//            mEmpty.setVisibility(View.VISIBLE);
//        } else {
//            mEmpty.setVisibility(View.INVISIBLE);
//            mList.setVisibility(View.VISIBLE);
//        }
        mTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

    }

    private void addTask() {
        String description = mNewTaskText.getText().toString();
        Task newTask = new Task(description);
        newTask.save();
        mTasks.add(description);
        mNewTaskText.setText("");
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
