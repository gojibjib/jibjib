package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavBar;

    private IBirdListViewPresenter presenter;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birdlist_view);
        this.presenter = new BirdListViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        presenter.getBirdList(this);
        context = this;
        activity = this;

        bottomNavBar.setSelectedItemId(R.id.nav_collection);
        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_record:
                        if (!(activity instanceof MatchView)) {
                            startActivity(new Intent(context, MatchView.class));
                            finish();
                        }
                        break;
                    case R.id.nav_collection:
                        if (!(activity instanceof BirdListView)) {
                            Intent intent = new Intent(context, BirdListView.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case R.id.nav_info:
                        if (!(activity instanceof InfoView)) {
                            startActivity(new Intent(context, InfoView.class));
                            finish();
                        }
                        break;
                }

                return true;
            }
        });

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
        intent.putExtra(MatchBirdDetailView.EXTRA_BIRD_KEY, new MatchedBird(bird, -1, true));
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
        Toast.makeText(this, R.string.general_exception_text, Toast.LENGTH_LONG).show();
    }
}
