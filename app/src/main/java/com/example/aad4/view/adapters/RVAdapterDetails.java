package com.example.aad4.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.aad4.R;
import com.example.aad4.databinding.HolderDetailsBinding;
import com.example.aad4.entity.Comment;

import java.util.List;

public class RVAdapterDetails extends RecyclerView.Adapter<RVAdapterDetails.ViewHolder> {


    private List<Comment> list;

    public RVAdapterDetails(List<Comment> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_details, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment comment = list.get(position);
        holder.holderBinding.setVar(comment);

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        HolderDetailsBinding holderBinding;

        public ViewHolder(HolderDetailsBinding holderBinding) {
            super(holderBinding.getRoot());
            this.holderBinding = holderBinding;

        }

    }


}