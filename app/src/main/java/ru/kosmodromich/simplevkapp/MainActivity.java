package ru.kosmodromich.simplevkapp;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import ru.kosmodromich.simplevkapp.Entities.RequestCommunityData;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.community_name);
        search = findViewById(R.id.search);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, PrefActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add:
                intent = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CommunityActivity.class);
                RequestCommunityData requeCommunityData = new RequestCommunityData();
                requeCommunityData.setName(name.getText().toString());
                intent.putExtra("data", requeCommunityData);
                startActivity(intent);
            }
        });
    }
}
