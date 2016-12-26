package cyj.android.com.waittinganimation;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private List<String> datas = new ArrayList<String>();
    private WaittingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i =0;i<20;i++){
            datas.add("item"+i);
        }

        WaittingListView listView = (WaittingListView) findViewById(R.id.listView);
        adapter = new WaittingAdapter(this,datas);
        listView.setAdapter(adapter);

        listView.setDataListener(new WaittingListView.OnDataListener() {
            @Override
            public void newData() {
                datas.add(0,"new item");
                adapter.notifyDataSetChanged();
            }
        });

    }
}
