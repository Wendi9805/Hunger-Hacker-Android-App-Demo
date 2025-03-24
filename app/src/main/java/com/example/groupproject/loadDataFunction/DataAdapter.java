package com.example.groupproject.p2pFunction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.DetailActivity;
import com.example.groupproject.databinding.ItemContainerDisplaydataBinding;
import com.example.groupproject.loadDataFunction.FoodData;

import java.util.List;

/**
 * The main function of this class is to put loaded data from the database into a RecyclerView and visualize it.
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private final List<FoodData> dataList;
    private final Context context;

    public DataAdapter(Context context, List<FoodData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Using binding to help us organize the layout more accurately
        ItemContainerDisplaydataBinding binding = ItemContainerDisplaydataBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        ItemContainerDisplaydataBinding binding;

        DataViewHolder(ItemContainerDisplaydataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Display foodData in the layout page
         *
         * @param data an foodData instance
         */
        void setData(FoodData data) {
            binding.attribute1.setText(data.getProvider());
            binding.attribute3.setText(data.getProductName());
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("foodData", data);
                context.startActivity(intent);
            });
        }

        /**
         * This method is mainly used to display users' images in loading page
         *
         * @param encodedImage the String version of jpg and png image
         */
        private Bitmap getImageBitmap(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
