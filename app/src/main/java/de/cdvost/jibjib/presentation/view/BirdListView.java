package de.cdvost.jibjib.presentation.view;

import android.app.Activity;

import java.util.List;

import de.cdvost.jibjib.presentation.presenter.IBirdListViewPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class BirdListView extends Activity implements IBirdListViewPresenter.View {
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
