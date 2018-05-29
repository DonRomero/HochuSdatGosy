package ru.kosmodromich.simplevkapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class HandlerActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        button = findViewById(R.id.handler_btn);
        textView = findViewById(R.id.handler_tv);
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                textView.setText("Что-то делаю целую секунду: " + msg.what);
            };
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            // долгий процесс
                            downloadFile();
                            handler.sendEmptyMessage(i);
                        }
                    }
                });
                t.start();
            }
        });
    }

    void downloadFile() {
        // пауза - 1 секунда
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
