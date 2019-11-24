package com.example.aad4.view.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.aad4.R;
import com.example.aad4.databinding.HolderMasterBinding;
import com.example.aad4.entity.TouristAttraction;
import com.example.aad4.view.OnActionPerformedListener;

import java.util.List;

import static com.example.aad4.App.OBJECT_ID;
import static com.example.aad4.view.OnActionPerformedListener.BUNDLE_KEY;
import static com.example.aad4.view.OnActionPerformedListener.OPEN_DETAILS;

public class RVAdapterMaster extends RecyclerView.Adapter<RVAdapterMaster.ViewHolder> {


    private List<TouristAttraction> list;
    private OnActionPerformedListener listener;

    public RVAdapterMaster(List<TouristAttraction> list, OnActionPerformedListener listener) {
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderMasterBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_master, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TouristAttraction touristAttraction = list.get(position);
        holder.holderBinding.setVar(touristAttraction);

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        HolderMasterBinding holderBinding;

        public ViewHolder(HolderMasterBinding holderBinding) {
            super(holderBinding.getRoot());
            this.holderBinding = holderBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BUNDLE_KEY, OPEN_DETAILS);
                    bundle.putInt(OBJECT_ID, list.get(getAdapterPosition()).getId());
                    listener.onActionPerformed(bundle);
                }
            });
        }
    }


}