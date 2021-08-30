package com.project.caaryajava;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    ArrayList<Model> listdata;
    String listName;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<Model> listdata, String listName, Context context) {
        this.listdata = listdata;
        this.listName = listName;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(listdata.get(position).productName);
        holder.marketPrice.setText( "$"+Integer.toString(listdata.get(position).marketPrice));
        holder.storePrice.setText("$"+Integer.toString(listdata.get(position).storePrice));
        String descrip = "";
        if(listdata.get(position).description.length() > 15)
            descrip = listdata.get(position).description.substring(0,15) + "...";
        else
            descrip = listdata.get(position).description;
        holder.description.setText(descrip);
        Glide.with(context).load(listdata.get(position).image).into(holder.image);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Model>>() {}.getType();

                SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
                String jsonText = sharedPreferences.getString(listName, null);

                listdata = gson.fromJson(jsonText, type);;

                listdata.remove(listdata.get(position));

                jsonText = gson.toJson(listdata);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(listName, jsonText);
                editor.apply();
                notifyDataSetChanged();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listdata.size());
            }
        });

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity2.class);
                intent.putExtra("list_item",  listdata.get(position));
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView marketPrice;
        TextView storePrice;
        TextView description;
        ImageView image;
        ImageView deleteItem;
        ImageView editItem;

        public ViewHolder(View itemView) {
            super(itemView);
//            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            this.textView = (TextView) itemView.findViewById(R.id.textView);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            this.productName = itemView.findViewById(R.id.product_name);
            this.marketPrice = itemView.findViewById(R.id.market_price);
            this.storePrice = itemView.findViewById(R.id.store_price);
            this.description = itemView.findViewById(R.id.description);
            this.image = itemView.findViewById(R.id.image);
            this.deleteItem = itemView.findViewById(R.id.delete);
            this.editItem = itemView.findViewById(R.id.edit_icon);

        }
    }
}



