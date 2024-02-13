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

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

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

    @Override
    public int getItemViewType(int position) {
        return isLoading && position == getItemCount() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.your_item_layout, parent, false); // Replace with your actual item layout
            return new ItemViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_loading, parent, false); // Replace with your actual loading item layout
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            YourDataModel dataModel = filteredList.get(position);
            ((ItemViewHolder) holder).bind(dataModel);
        } else if (holder instanceof LoadingViewHolder) {
            // Show loading indicator
            ((LoadingViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size() + (isLoading ? 1 : 0);
    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder  {

        TextView nameTextView;
        TextView id;
        TextView countryTextView;
        TextView trips;
        TextView country;
        TextView head;
        TextView website;
        ImageView logoImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id); // Replace with your actual TextView ID
            nameTextView = itemView.findViewById(R.id.name); // Replace with your actual TextView ID
            trips = itemView.findViewById(R.id.trips); // Replace with your actual TextView ID
            logoImageView = itemView.findViewById(R.id.logo); // Replace with your actual ImageView ID
            head = itemView.findViewById(R.id.country); // Replace with your actual ImageView ID
            website = itemView.findViewById(R.id.website); // Replace with your actual ImageView ID
        }
        public void bind(YourDataModel dataModel) {
            // Populate your ViewHolder views with data
            nameTextView.setText(dataModel.getName());
            id.setText(dataModel.getId());
//            id.setText(dataModel.getAirline().get(getAdapterPosition()).getCountry());
            id.setText(dataModel.getTrips());
            countryTextView.setText(dataModel.getAirline(0));

            // Use Glide or any other image loading library to load the image
            Glide.with(itemView.getContext())
                    .load(dataModel.getAirline().get(0).getLogo())
                    .into(logoImageView);
        }

    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar); // Replace with your actual ProgressBar ID
        }

        public void bind() {
            // Show loading indicator
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

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                // ... (same as before)
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                notifyDataSetChanged();
//            }
//        };
//    }

}


//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.bumptech.glide.Glide;
//import com.demo.example.R;
//import com.demo.example.model.YourDataModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class YourAdapter extends RecyclerView.Adapter<YourAdapter.ViewHolder> implements Filterable {
//
//    private List<YourDataModel> dataList;
//    private List<YourDataModel> filteredList;
//    private Context context;
//
//    public YourAdapter() {
//        this.dataList = new ArrayList<>();
//        this.filteredList = new ArrayList<>();
//    }
//
//    public void addData(List<YourDataModel> newData) {
//        dataList.addAll(newData);
//        filteredList.addAll(newData);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();
//        View view = LayoutInflater.from(context).inflate(R.layout.your_item_layout, parent, false); // Replace with your actual item layout
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        YourDataModel dataModel = filteredList.get(position);
//
//        // Populate your ViewHolder views with data
//        holder.nameTextView.setText(dataModel.getName());
////        holder.countryTextView.setText(dataModel.getAirline().get(0).getCountry());
//
//        // Use Glide or any other image loading library to load the image
//        Glide.with(context)
//                .load(dataModel.getAirline().get(0).getLogo())
//                .into(holder.logoImageView);
//    }
//
//    @Override
//    public int getItemCount() {
//        return filteredList.size();
//    }
//
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//
//                if (filterPattern.isEmpty()) {
//                    filteredList.clear();
//                    filteredList.addAll(dataList);
//                } else {
//                    List<YourDataModel> filtered = new ArrayList<>();
//                    for (YourDataModel dataModel : dataList) {
//                        if (dataModel.getName().toLowerCase().contains(filterPattern) || dataModel.getAirline().get(0).getCountry().toLowerCase().contains(filterPattern)) {
//                            filtered.add(dataModel);
//                        }
//                    }
//                    filteredList.clear();
//                    filteredList.addAll(filtered);
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                notifyDataSetChanged();
//            }
//        };
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView nameTextView;
//        TextView id;
//        TextView countryTextView;
//        TextView trips;
//        TextView country;
//        TextView head;
//        TextView website;
//        ImageView logoImageView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            id = itemView.findViewById(R.id.id); // Replace with your actual TextView ID
//            nameTextView = itemView.findViewById(R.id.name); // Replace with your actual TextView ID
//            trips = itemView.findViewById(R.id.trips); // Replace with your actual TextView ID
//            logoImageView = itemView.findViewById(R.id.logo); // Replace with your actual ImageView ID
//            head = itemView.findViewById(R.id.country); // Replace with your actual ImageView ID
//            website = itemView.findViewById(R.id.website); // Replace with your actual ImageView ID
//        }
//    }
//}
//
