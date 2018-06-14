package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.presentation.presenter.BirdListViewPresenter;
import de.cdvost.jibjib.presentation.presenter.IBirdListViewPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class BirdListView extends Activity implements IBirdListViewPresenter.View {

    @BindView(R.id.BirdList)
    TextView birdlist;
    @BindView(R.id.list_saved_birds)
    ListView savedBirds;

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
        List<String> birdListResult = new ArrayList<String>();
        for (Bird result : birdList) {
            birdListResult.add(result.getName());
        }

        ListAdapter birdListAdapter = new ArrayAdapter(
                this,
                R.layout.match_bird_item,
                R.id.match_bird_name,
                birdListResult);

        savedBirds.setAdapter(birdListAdapter);
        savedBirds.setVisibility(View.VISIBLE);
    }

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
