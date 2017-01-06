package com.next.artest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.next.artest.adapters.AdapterSelectActivity;
import com.next.artest.dao.ActivityTypeDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SelectTypeActivity extends AppCompatActivity {

    public static final String ACTIVITY_LIST_TYPE = "activity_list_type";

    @BindView(R.id.select_activity_type_list)
    ListView activityTypeList;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private AdapterSelectActivity adapterSelectActivity;
    private List<ActivityTypeDAO> typeDAOs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        adapterSelectActivity = new AdapterSelectActivity(this);
        activityTypeList.setAdapter(adapterSelectActivity);
        switch (getIntent().getIntExtra(ACTIVITY_LIST_TYPE, 0)) {
            case 0:
                typeDAOs.add(new ActivityTypeDAO(R.mipmap.ic_launcher, "Fun with Words", "Test description"));
                typeDAOs.add(new ActivityTypeDAO(R.mipmap.ic_sing_along, "Sing Along", "Test description"));
                typeDAOs.add(new ActivityTypeDAO(R.mipmap.ic_flash_card, "Flash Cards", "Test description"));
                typeDAOs.add(new ActivityTypeDAO(R.mipmap.ic_train, "Word Train", "Test description"));
                break;
            case 1:
                typeDAOs.add(new ActivityTypeDAO(R.mipmap.ic_launcher, "Fun with Numbers", "Test description"));
                typeDAOs.add(new ActivityTypeDAO(R.mipmap.ic_flash_card, "Number Games", "Test description"));
                break;
        }
        adapterSelectActivity.setData(typeDAOs);

        activityTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(SelectTypeActivity.this, UnityPlayerActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(SelectTypeActivity.this, ReadActivity.class));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
