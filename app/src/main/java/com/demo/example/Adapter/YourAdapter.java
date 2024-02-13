package com.demo.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.model.YourDataModel;

import java.util.ArrayList;
import java.util.List;

public class YourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<YourDataModel> dataList;
    private List<YourDataModel> filteredList;
    private Context context;

    private boolean isLoading = false;

    public YourAdapter() {
        this.dataList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    public void addData(List<YourDataModel> newData) {
        dataList.addAll(newData);
        filteredList.addAll(newData);
        notifyDataSetChanged();
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.your_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        YourDataModel dataModel = filteredList.get(position);
        ((ItemViewHolder) holder).bind(dataModel);
    }

    public List<YourDataModel> getDataList() {
        return dataList;
    }

    @Override
    public int getItemCount() {
        return filteredList.size() + (isLoading ? 1 : 0);
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView id;
        TextView trips;
        TextView country;
        TextView head;
        TextView website;
        ImageView logoImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            nameTextView = itemView.findViewById(R.id.name);
            trips = itemView.findViewById(R.id.trips);
            logoImageView = itemView.findViewById(R.id.logo);
            head = itemView.findViewById(R.id.head);
            website = itemView.findViewById(R.id.website);
            country = itemView.findViewById(R.id.country);
        }

        public void bind(YourDataModel dataModel) {
            nameTextView.setText(dataModel.getName());
            id.setText(dataModel.get_id().toString());
            trips.setText(dataModel.getTrips() + "");
            country.setText(dataModel.getAirline().get(0).getCountry());
            head.setText(dataModel.getAirline().get(0).getHeadQuarters());
            website.setText(dataModel.getAirline().get(0).getWebsite());

            Glide.with(itemView.getContext())
                    .load(dataModel.getAirline().get(0).getLogo())
                    .into(logoImageView);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                if (filterPattern.isEmpty()) {
                    filteredList.clear();
                    filteredList.addAll(dataList);
                } else {
                    List<YourDataModel> filtered = new ArrayList<>();
                    for (YourDataModel dataModel : dataList) {
                        if (dataModel.getName().toLowerCase().contains(filterPattern) || dataModel.getAirline().get(0).getCountry().toLowerCase().contains(filterPattern)) {
                            filtered.add(dataModel);
                        }
                    }
                    filteredList.clear();
                    filteredList.addAll(filtered);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

}
