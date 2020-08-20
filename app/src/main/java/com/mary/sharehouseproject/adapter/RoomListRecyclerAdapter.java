package com.mary.sharehouseproject.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RoomListRecyclerAdapter extends RecyclerView.Adapter<RoomListRecyclerAdapter.RoomListViewHolder>{

    private List<Room> roomList;

    public void addRoomList(List<Room> roomList){
        this.roomList=roomList;
    }

    @NonNull
    @Override
    public RoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.lendroom_item,parent,false);
        return new RoomListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListViewHolder holder, int position) {
        Room room=roomList.get(position);
        holder.setItem(room);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class RoomListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvRoomName,tvGender,tvType,tvDeposit,tvMonthly,tvMoveInDate;

        public RoomListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRoomName=itemView.findViewById(R.id.tv_roomName);
            tvGender=itemView.findViewById(R.id.tv_gender);
            tvType=itemView.findViewById(R.id.tv_type);
            tvDeposit=itemView.findViewById(R.id.tv_deposit);
            tvMonthly=itemView.findViewById(R.id.tv_monthly);
            tvMoveInDate=itemView.findViewById(R.id.tv_moveInDate);
        }

        private void setItem(Room room){
            tvRoomName.setText(room.getRoomName());
            tvRoomName.setText(room.getRoomName());
            tvGender.setText(room.getGender());
            tvType.setText(room.getType());
            tvDeposit.setText(room.getDeposit());
            tvMonthly.setText(room.getMonthly());
            long now=System.currentTimeMillis();
            Date date=new Date(now);
            Date moveInDate=room.getMoveInDate().toDate();
            if(date.after(moveInDate)){
                tvMoveInDate.setText("즉시입주");
            }else{
                SimpleDateFormat dateFormat=new SimpleDateFormat("yy-MM-dd");
                tvMoveInDate.setText(dateFormat.format(moveInDate));
            }
        }
    }
}
