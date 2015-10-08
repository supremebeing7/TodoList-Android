package com.markjlehman.todolist.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends ListActivity {
    public static String TAG = MainActivity.class.getSimpleName();

    private ArrayList<String> mCategories;
    private Button mCategoryButton;
    private EditText mNewCategoryText;
    private ArrayAdapter<String> mCategoryAdapter;
    private TextView mCategoriesEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategoryButton = (Button) findViewById(R.id.addTaskButton);
        mNewCategoryText = (EditText) findViewById(R.id.addTaskField);
        mCategories = new ArrayList<String>();
        for (Category category : Category.all() ) {
            mCategories.add(category.getmName());
            Log.d(TAG, category.getId().toString());
        }
        mCategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mCategories);
        setListAdapter(mCategoryAdapter);
        mCategoriesEmpty = (TextView) findViewById(R.id.emptyCategories);
        if (mCategories.size() == 0) {
            mCategoriesEmpty.setVisibility(View.VISIBLE);
        }
        mCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String thisCategoryName = mCategories.get(position);
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("categoryName", thisCategoryName);
        startActivity(intent);
    }

    private void addCategory() {
        String name = mNewCategoryText.getText().toString();
        Category newCategory = new Category(name);
        newCategory.save();
        mCategories.add(name);
        mNewCategoryText.setText("");
        mNewCategoryText.clearFocus();
        mCategoriesEmpty.setVisibility(View.INVISIBLE);
        mCategoryAdapter.notifyDataSetChanged();
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
