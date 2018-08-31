package de.cdvost.jibjib.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class SavedBirdListAdapter extends RecyclerView.Adapter<SavedBirdListAdapter.ViewHolder> {
    private List<String> nameValues;
    List<Bird> birdDataset;
    BirdListView mView;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView birdName;
        TextView birdAccuracy;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            birdName = (TextView) v.findViewById(R.id.match_bird_name);
            birdAccuracy = (TextView) v.findViewById(R.id.match_bird_accuracy);
            birdAccuracy.setVisibility(View.GONE);
        }
    }

    public void add(int position, String item) {
        nameValues.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        nameValues.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SavedBirdListAdapter(BirdListView view, List<Bird> myDataset) {
        mView = view;
        birdDataset = myDataset;
        nameValues = new ArrayList<>();
        for (Bird bird : myDataset) {
            nameValues.add(bird.getTitle_de() + "    " + "(" + bird.getName() + ")");
            //accuracyValues.add(Float.toString(bird.getAccuracy()));
        }
    }

    @Override
    public SavedBirdListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.saved_bird_item, parent, false);
        SavedBirdListAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SavedBirdListAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = nameValues.get(position);
        holder.birdName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.itemClick(birdDataset.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameValues.size();
    }


}
