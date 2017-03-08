package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dijiaoliang on 17/3/8.
 */
public class TestRecyclerVewActivity extends AppCompatActivity {

    private List<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        RecyclerView rv= (RecyclerView) findViewById(R.id.rv_content);
        rv.setLayoutManager(new LinearLayoutManager(this));
        data=new ArrayList<>();
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        TestAdapter adapter=new TestAdapter(this,data);
        rv.setAdapter(adapter);
        adapter.setRecyclerViewListener(new TestAdapter.OnRecyclerViewItemClickListener<Bundle>() {
            @Override
            public void onClick(int id, Bundle data) {
                switch (id){
                    case R.id.item:
                        ToastUtil.shortToast(TestRecyclerVewActivity.this,"item click");
                        break;
                    case R.id.btn_receiving:
                        ToastUtil.shortToast(TestRecyclerVewActivity.this,"item child click");
                        break;
                }
            }
        });

    }
}
