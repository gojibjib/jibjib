package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.BirdListViewPresenter;
import de.cdvost.jibjib.presentation.presenter.IBirdListViewPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class BirdListView extends Activity implements IBirdListViewPresenter.View {

    @BindView(R.id.BirdList)
    TextView birdlist;
    @BindView(R.id.list_saved_birds)
    RecyclerView savedBirds;

    private IBirdListViewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birdlist_view);
        this.presenter = new BirdListViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        presenter.getBirdList(this);

    }

    @Override
    public void showBirdList(List<Bird> birdList) {


        //result list
        for (Bird bird : birdList)

            savedBirds = (RecyclerView) findViewById(R.id.list_saved_birds);


        RecyclerView.LayoutManager blLayoutManager = new LinearLayoutManager(this);
        savedBirds.setLayoutManager(blLayoutManager);
        RecyclerView.Adapter blAdapter = new SavedBirdListAdapter(this, birdList);
        savedBirds.setAdapter(blAdapter);

        /*List<String> birdListResult = new ArrayList<String>();
        for (Bird result : birdList) {
            birdListResult.add(result.getTitle_de() + "     " + "(" + result.getName() + ")");
        }

        ListAdapter birdListAdapter = new ArrayAdapter(
                this,
                R.layout.match_bird_item,
                R.id.match_bird_name,
                birdListResult);

        savedBirds.setAdapter(SavedBirdListAdapter);
        savedBirds.setVisibility(View.VISIBLE);*/
    }

   /* @OnItemClick(R.id.list_saved_birds)
    public void onItemClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }*/

    public void itemClick(Bird bird) {
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        intent.putExtra(MatchBirdDetailView.EXTRA_BIRD_KEY, new MatchedBird(bird, -1));
        startActivity(intent);
    }

   /* @OnItemClick(R.id.list_match)
    public void onItemClick(int position) {
        Toast.makeText(this, "on item click" + matchBirds.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        //TODO get ID
        int id = 0;
        intent.putExtra(MatchBirdDetailView.BIRD_ID, id);
        startActivity(intent);
    }*/

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
