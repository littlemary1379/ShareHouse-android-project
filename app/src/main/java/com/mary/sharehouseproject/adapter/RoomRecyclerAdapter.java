package com.mary.sharehouseproject.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.RoomViewHolder>{
    private static final String TAG = "RoomRecyclerAdapter";

    private List<Room> roomList=new ArrayList<>();

    public void addRoomList(List<Room> roomList){
        this.roomList=roomList;
        Log.d(TAG, "addRoomList: "+this.roomList);
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 1번 동작 : 어댑터 돌아가는지 확인하기.");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.room_item,parent,false);
        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room=roomList.get(position);
        holder.setItem(room);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder{

        private TextView tvRoomName,tvGender,tvType,tvRoomArea,tvDeposit,tvMonthly,tvMoveInDate;
        private Button btnApply;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName=itemView.findViewById(R.id.tv_roomName);
            tvGender=itemView.findViewById(R.id.tv_gender);
            tvType=itemView.findViewById(R.id.tv_type);
            tvRoomArea=itemView.findViewById(R.id.tv_roomArea);
            tvDeposit=itemView.findViewById(R.id.tv_deposit);
            tvMonthly=itemView.findViewById(R.id.tv_monthly);
            tvMoveInDate=itemView.findViewById(R.id.tv_moveInDate);
            btnApply=itemView.findViewById(R.id.btn_apply);
        }


        public void setItem(Room room){
            tvRoomName.setText(room.getRoomName());
            tvGender.setText(room.getGender());
            tvType.setText(room.getType());
            tvRoomArea.setText(room.getRoomArea());
            tvDeposit.setText(room.getDeposit());
            tvMonthly.setText(room.getMonthly());
            long now=System.currentTimeMillis();
            Date date=new Date(now);
            Date moveInDate=room.getMoveInDate().toDate();
            Log.d(TAG, "setItem: "+date);
            if(date.after(moveInDate)){
                tvMoveInDate.setText("즉시입주");
                btnApply.setText("신청");
                btnApply.setBackgroundResource(R.color.colorMainChorale);
                btnApply.setTextColor(Color.WHITE);
            }else{
                SimpleDateFormat dateFormat=new SimpleDateFormat("yy-MM-dd");
                tvMoveInDate.setText(dateFormat.format(moveInDate));
                btnApply.setText("일정 조율");
            }

        }
    }

}
