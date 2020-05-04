package com.example.shibam.covid_19livetracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapterState extends ArrayAdapter<StateModel> {

    private List<StateModel> stateModelsListFiltered;
    private List<StateModel> stateModelsList;

    public MyCustomAdapterState(Context context, List<StateModel> stateModelsList) {
        super(context, R.layout.list_custom_item_states, stateModelsList);

        //this.context = context;

        this.stateModelsListFiltered = stateModelsList;
        this.stateModelsList = stateModelsList;
        AffectedStates.stateModelsList = stateModelsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item_states, null, true);

        TextView tvStateName = view.findViewById(R.id.tvStateName);

        tvStateName.setText(stateModelsListFiltered.get(position).getState());
        //Glide.with(context).load(stateModelsListFiltered.get(position).getFlag()).into(imageView);
        return view;
    }
    @Override
    public int getCount() {
        return stateModelsListFiltered.size();
    }

    @Nullable
    @Override
    public StateModel getItem(int position) {
        return stateModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = stateModelsList.size();
                    filterResults.values = stateModelsList;
                }else{
                    List<StateModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(StateModel itemsModel:stateModelsList){
                        if(itemsModel.getState().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }

                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stateModelsListFiltered = (List<StateModel>) results.values;
                AffectedStates.stateModelsList = (List<StateModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
