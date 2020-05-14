package cn.edu.bjtu.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.AlteredCharSequence;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Viewholder> {
    private List<MailList> mMailList;

    static class Viewholder extends RecyclerView.ViewHolder{
        View mailView;
        TextView mailName;
        TextView mailNumber;

        public Viewholder(View view) {
            super(view);
            mailView = view;
            mailName = (TextView) view.findViewById(R.id.mail_name);
            mailNumber = (TextView)view.findViewById(R.id.mail_number);
        }
    }


    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    public ListAdapter(List<MailList> mailList){
        mMailList = mailList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mail_item,parent,false);
        return new ListAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        MailList list = mMailList.get(position);
        holder.mailName.setText(list.getName());
        holder.mailNumber.setText(list.getNumber());
        holder.mailView.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               if (listener != null) {
                   listener.onClick(position);
               }
           }
        });

        holder.mailView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMailList.size();
    }

}
