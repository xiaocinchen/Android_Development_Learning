package cn.edu.bjtu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    private List<MailList> mailList = new ArrayList<>();
    private EditText inputText;
    private RecyclerView mailRecyclerView;
    private ListAdapter adapter;
    private ListAdapter adapter1;
    private List<MailList> showList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initMail();
        TextView name = (TextView) findViewById(R.id.mail_name);
        TextView number = (TextView) findViewById(R.id.mail_number);
        final EditText editText = (EditText) findViewById(R.id.search_bar);
        mailRecyclerView = (RecyclerView) findViewById(R.id.list_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mailRecyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(mailList);
        mailRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //Log.e("data",position+"");
                showChoise(position,0);
            }
        });

        editText.addTextChangedListener(new MyTextWatcher());

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    String result = editText.getText().toString();
                    if (result.length() >= 1) {
                        List<MailList> showList = new ArrayList<>();
                        for (int i = 0; i < mailList.size(); i++) {
                            if (mailList.get(i).getName().contains(result)) {
                                showList.add(mailList.get(i));
                            }
                        }
                        adapter1 = new ListAdapter(showList);
                        mailRecyclerView.setAdapter(adapter1);
                        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                //Log.e("data",position+"");
                                showChoise(position,1);
                            }
                        });
                    } else {
                        mailRecyclerView.setAdapter(adapter);
                    }
                }
                return false;
            }
        });
    }

    private void initMail() {
        MailList mail1 = new MailList("YZL", "123456789");
        mailList.add(mail1);
        MailList mail2 = new MailList("JJL", "987654321");
        mailList.add(mail2);
        for (int i = 0; i < 15; i++) {
            String ranName = (char) ((int) (Math.random() * 25) + 65) + "" + ((char) ((int) (Math.random() * 25) + 65) + "") + ((char) ((int) (Math.random() * 25) + 65) + "");
            String ranNumber = "";
            for (int j = 0; j < 9; j++)
                ranNumber = ranNumber + (int) (Math.random() * 10) + "";
            MailList mail = new MailList(ranName, ranNumber);
            mailList.add(mail);
        }

        Collections.sort(mailList, new Comparator<MailList>(){
            @Override
            public int compare(MailList o1, MailList o2) {
                return o1.getName().compareTo(o2.getName());
            }});
    }

    public void showChoise(final int position,final int flag) {
        final String[] way = new String[]{"短信", "电话"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle("您想要？");
        builder.setItems(way, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent("android.intent.action.ACTION_MESSAGE");
                        if (flag == 0)
                            intent.putExtra("target",mailList.get(position).getName());
                        else
                            intent.putExtra("target",showList.get(position).getName());
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Intent.ACTION_DIAL);
                        intent2.setData(Uri.parse("tel:" + mailList.get(position).getNumber()));
                        startActivity(intent2);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            Toast.makeText(this, "you click add", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.remove_item){
            Toast.makeText(this, "you click remove", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            final EditText editText = (EditText) findViewById(R.id.search_bar);
            String result = editText.getText().toString();
            if (result.length() >= 1) {
                showList.clear();
                for (int i = 0; i < mailList.size(); i++) {
                    if (mailList.get(i).getName().contains(result)) {
                        showList.add(mailList.get(i));
                    }
                }
                adapter1 = new ListAdapter(showList);
                mailRecyclerView.setAdapter(adapter1);
            } else {
                mailRecyclerView.setAdapter(adapter);
            }
            adapter1.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    //Log.e("data",position+"");
                    showChoise(position,1);
                }
            });
        }
    }
}
