package com.omo_lanke.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.data.Step;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by omo_lanke on 20/06/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{
    private Context context;
    private List<Step> steps;

    final private StepAdapterOnClickHandler clickHandler;

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int layoutIdForList = R.layout.step_item;
        View view = inflater.inflate(layoutIdForList, parent, shouldAttachToParentImmediately);
        view.setFocusable(true);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.textViewIndex.setText(position +".");
        holder.textViewStep.setText(step.getShortDescription().trim());

        if (!(step.getThumbnailURL()).isEmpty()) {
            Picasso.with(context).load(step.getThumbnailURL())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(holder.imageViewStep);
        }
    }

    @Override
    public int getItemCount() {
        if (null == steps) return 0;
        return steps.size();
    }

    public interface StepAdapterOnClickHandler{
        void onClick(int index, Step step);
    }

    public StepAdapter(@NonNull Context context, StepAdapterOnClickHandler clickHandler, List<Step> steps){
        this.context = context;
        this.clickHandler = clickHandler;
        this.steps = steps;
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewStep;
        public TextView textViewIndex;
        public ImageView imageViewStep;
        StepAdapterViewHolder(View view){
            super(view);
            textViewIndex = (TextView)view.findViewById(R.id.textViewIndex);
            textViewStep = (TextView)view.findViewById(R.id.textViewStep);
            imageViewStep = (ImageView)view.findViewById(R.id.imageViewStep);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            clickHandler.onClick(pos, steps.get(pos));
        }
    }

    public void resetData(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

}
