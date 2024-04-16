package com.example.datainternet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Todo> data;
    private RecyclerView rvTodo;
    String hasil = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Handler h = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String z = bundle.getString("hasil");
                hasil = z;


                        Gson gson = new Gson();

            Todo[] todos = gson.fromJson(hasil, Todo[].class);

                data = new ArrayList<Todo>();

                for (int i = 0; i < todos.length; i++) {
                    data.add(todos[i]);
                }



                rvTodo = findViewById(R.id.rvTodo);

                TodoAdapter todoAdapter = new TodoAdapter(MainActivity.this, data);

                RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
                rvTodo.setLayoutManager(lm);
                rvTodo.setAdapter(todoAdapter);



            }
        };
        Thread t = new TodoThread(h);
        t.start();




    }

}