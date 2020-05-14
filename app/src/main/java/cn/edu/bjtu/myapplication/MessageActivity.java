package cn.edu.bjtu.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static cn.edu.bjtu.myapplication.Msg.TYPE_RECYCLE;
import static cn.edu.bjtu.myapplication.Msg.TYPE_SENT;

public class MessageActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private List<Msg> rcvMsgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String target = intent.getStringExtra("target");
        TextView textView = (TextView)findViewById(R.id.target);
        textView.setText(target);
        initMsg();
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);
        msgRecyclerView = (RecyclerView)findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String content = inputText.getText().toString();
                if (content.length() != 0){
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                    String rcvContent = "您的消息被拒收了!";
                    msg = new Msg(rcvContent, TYPE_RECYCLE);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsg(){
        Msg msg1 = new Msg("Hello",Msg.TYPE_SENT);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello",Msg.TYPE_RECYCLE);
        msgList.add(msg2);
    }

}
