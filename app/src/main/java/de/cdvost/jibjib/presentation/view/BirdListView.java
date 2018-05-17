package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.presentation.presenter.BirdListViewPresenter;
import de.cdvost.jibjib.presentation.presenter.IBirdListViewPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class BirdListView extends Activity implements IBirdListViewPresenter.View {

    @BindView(R.id.BirdList)
    TextView birdlist;

    private IBirdListViewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birdlist_view);
        this.presenter = new BirdListViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);

    }

    @Override
    public void showBirdList(List<Bird> birdList) {

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
