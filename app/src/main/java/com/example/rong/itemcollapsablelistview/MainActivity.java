package com.example.rong.itemcollapsablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ItemCollapsableAdapter itemCollapsableAdapter;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        data.add("内容1");
        data.add("内容2");
        data.add("内容3");
        data.add("内容4");
        data.add("内容5");
        data.add("内容6");
        data.add("内容7");
        data.add("内容8");
        data.add("内容9");
        data.add("内容10");

        listView = (ListView) findViewById(R.id.listView);
        itemCollapsableAdapter = new ItemCollapsableAdapter(MainActivity.this, data);
        listView.setAdapter(itemCollapsableAdapter);
    }

}
