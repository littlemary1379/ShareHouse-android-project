package com.mary.sharehouseproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.dto.SearchHouseDto;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdaper extends RecyclerView.Adapter<SearchRecyclerAdaper.SearchViewHolder>{

    private List<SearchHouseDto> searchHouseDtoList=new ArrayList<>();

    public void addList(SearchHouseDto searchHouseDto){
        searchHouseDtoList.add(searchHouseDto);
    }

    public void addList(List<SearchHouseDto> searchHouseDtoList){
        this.searchHouseDtoList=searchHouseDtoList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.search_item,parent,false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchHouseDto searchHouseDto=searchHouseDtoList.get(position);
        holder.setItem(searchHouseDto);
    }

    @Override
    public int getItemCount() {
        return searchHouseDtoList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSearchId, tvSearchAddress;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchAddress=itemView.findViewById(R.id.tv_search_address);
            tvSearchId=itemView.findViewById(R.id.tv_search_id);
        }


        public void setItem(SearchHouseDto searchHouseDto){
            tvSearchId.setText(searchHouseDto.getHouseId()+"호");
            tvSearchAddress.setText(searchHouseDto.getAddress()+"");
        }
    }
}
