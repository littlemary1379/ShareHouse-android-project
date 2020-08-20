package com.mary.sharehouseproject.adapter;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.LendHouse;
import com.mary.sharehouseproject.model.Room;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LendingHouseRecyclerAdapter extends RecyclerView.Adapter<LendingHouseRecyclerAdapter.LendingHouseViewHolder>{
    private static final String TAG = "LendingHouseRecyclerAda";

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private List<LendHouse> lendHouseList;
    private RoomListRecyclerAdapter adapter=new RoomListRecyclerAdapter();

    private Room room;
    private List<Room> roomList=new ArrayList<>();

    private RecyclerView rcRoom;

    public void addLendHouseList(List<LendHouse> lendHouseList){
        this.lendHouseList=lendHouseList;
    }

    @NonNull
    @Override
    public LendingHouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.mypage1_item,parent,false);
        return new LendingHouseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LendingHouseViewHolder holder, int position) {
        LendHouse lendHouse=lendHouseList.get(position);
        holder.setItem(lendHouse);
    }

    @Override
    public int getItemCount() {
        return lendHouseList.size();
    }

    public class LendingHouseViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMypage1Image;
        private TextView tvMypage1HouseNumber;

        public LendingHouseViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMypage1Image=itemView.findViewById(R.id.iv_mypage1_image);
            tvMypage1HouseNumber=itemView.findViewById(R.id.tv_mypage1_housenumber);
        }
        private void setItem(LendHouse lendHouse){
            Glide.with(itemView).load(lendHouse.getImageUri()).into(ivMypage1Image);
            tvMypage1HouseNumber.setText(lendHouse.getHouseNumber()+"호");
            getAdapter(lendHouse.getRoomId(),itemView);
        }
    }
    private void getAdapter(String roomId, final View v){
        db.collection(roomId+"/roomDetail").orderBy("roomName")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                room=documentSnapshot.toObject(Room.class);
                                roomList.add(room);
                            }
                            adapter.addRoomList(roomList);
                            rcRoom=v.findViewById(R.id.rc_room);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(v.getContext());
                            rcRoom.setLayoutManager(layoutManager);
                            rcRoom.setAdapter(adapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }


}
