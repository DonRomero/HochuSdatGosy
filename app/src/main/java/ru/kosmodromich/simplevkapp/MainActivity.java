package ru.kosmodromich.simplevkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        setListeners();
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
