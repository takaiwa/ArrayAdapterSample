package net.takaiwa.arrayadaptersample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    private List<Otsumami> list = null;
    private ListAdapter list_adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter_test);

        // xmlからおつまみ名の配列を取得
        String[] item_name = this.getResources().getStringArray(R.array.tsumami);
        this.list = new ArrayList<Otsumami>(item_name.length);
        for(int i=0;i<item_name.length;i++) {
            // おつまみクラスに格納
            Otsumami otsumami = new Otsumami();
            otsumami.setItem_name(item_name[i]);

            this.list.add(otsumami);
        }
        this.list_adapter = new ListAdapter(this, this.list);
        // 表示
        ((ListView)findViewById(R.id.listView1)).setAdapter(this.list_adapter);
    }

    // おつまみと個数を格納する用クラス
    private class Otsumami {
        private String item_name = null;
        private int count = 0;
        public String getItem_name() {
            return item_name;
        }
        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }
        public int getCount() {
            return count;
        }
        public void addCount() {
            this.count = this.count + 1;
        }
    }

    private class ListAdapter extends ArrayAdapter<Otsumami> {

        private LayoutInflater mInflater;

        public ListAdapter(Context context, List<Otsumami> objects) {
            super(context, 0, objects);
            this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // positionはfinalにしないと、onClick内で機能しない
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Otsumami otsumami = this.getItem(position);
            if(null == convertView) {
                // 行のレイアウトを取得（TextViewをImageButtonのレイアウト）
                convertView = this.mInflater.inflate(R.layout.list_item, null);
            }

            if(null != otsumami) {
                // おつまみ名と個数を表示
                ((TextView)convertView.findViewById(R.id.textView1)).setText(otsumami.getItem_name() + " (" + otsumami.getCount() + ")");

                ((ImageButton)convertView.findViewById(R.id.imageButton1)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        // リストの中身を変更する
                        Otsumami add_otumami = list.get(position);
                        add_otumami.addCount();
                        list.set(position, add_otumami);

                        // 表示の更新のやり方１：リストビューに通知をかける
                        list_adapter.notifyDataSetChanged();

                        // 表示の更新やり方2：ビューを直接いじる
//                        View parent = (View) v.getParent();
//                        ((TextView)(parent.findViewById(R.id.textView1))).setText(add_otumami.getItem_name() + " (" + add_otumami.getCount() + ")");
                    }
                });
            }
            return convertView;
        }
    }
}
