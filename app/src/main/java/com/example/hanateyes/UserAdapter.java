package com.example.hanateyes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
    private static final String TAG = "UserAdapter";

    //리스트는 무조건 데이터를 필요로함
    private List<User> items=new ArrayList<>();

    public void addItem(User user){
        items.add(user);
    }

    //껍데기만 만듬. 1번 실행
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(view);
    }

    //껍데기에 데이터 바인딩. 2번 실행
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        User user=items.get(position);
        holder.setItem(user);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }

    //ViewHolder : 뷰들의 책꽂이
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //규칙1
        private TextView tvTitle1;
        private TextView tvTitle2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //규칙2
            tvTitle1=itemView.findViewById(R.id.list_tv_name);
            tvTitle2=itemView.findViewById(R.id.list_tv_number);
        }

        //규칙3
        public void setItem(User user){

            tvTitle1.setText(user.getAccount());
            tvTitle2.setText(user.getFin_num());
        }
    }
}