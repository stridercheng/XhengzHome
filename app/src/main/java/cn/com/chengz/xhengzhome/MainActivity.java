package cn.com.chengz.xhengzhome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cn.com.chengz.xhengzhome.customview.adapter.MainActivityAdapter;
import cn.com.chengz.xhengzhome.customview.recyclerview.SwipeEditRecyclerView;

public class MainActivity extends AppCompatActivity {
    SwipeEditRecyclerView list;
    List<String> stringList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (SwipeEditRecyclerView) findViewById(R.id.list);

        initData();
    }

    private void initData() {
        for (int i = 1; i < 50; i++) {
            stringList.add("item " + i);
        }

        MainActivityAdapter adapter = new MainActivityAdapter(this, stringList);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        list.setAdapter(adapter);
    }
}
