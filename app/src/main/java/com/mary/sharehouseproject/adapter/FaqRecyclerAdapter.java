package com.mary.sharehouseproject.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mary.sharehouseproject.FAQActivity;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.Faq;

import java.util.ArrayList;
import java.util.List;

public class FaqRecyclerAdapter extends RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolder> {
    private static final String TAG = "FaqRecyclerAdapter";

    List<Faq> faqLists = new ArrayList<>();

    public void addFaqList(Faq faq) {
        faqLists.add(faq);
    }

    public void addFaqList(List<Faq> faqLists) {
        this.faqLists = faqLists;
    }


    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.faq_item, parent, false);
        return new FaqViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        Faq faq = faqLists.get(position);
        holder.setItem(faq);
    }

    @Override
    public int getItemCount() {
        return faqLists.size();
    }

    public static class FaqViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFaqTitle, tvFaqContent;

        public FaqViewHolder(@NonNull final View itemView) {
            super(itemView);


            tvFaqTitle = itemView.findViewById(R.id.tv_faq_title);
            tvFaqContent = itemView.findViewById(R.id.tv_faq_content);
            tvFaqContent.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvFaqContent.setVisibility(View.VISIBLE);
                }
            });
        }

        public void setItem(Faq faq) {
            tvFaqTitle.setText(faq.getFaqTitle());
            tvFaqContent.setText(faq.getFaqContent());
        }
    }
}

