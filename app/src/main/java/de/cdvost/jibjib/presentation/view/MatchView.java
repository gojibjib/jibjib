package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.executor.impl.ThreadExecutor;
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;

public class MatchView extends Activity implements IMatchViewPresenter.View, View.OnClickListener  {

    private IMatchViewPresenter presenter;
    private TextView textView;
    private Button btnMatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_layout);
        this.presenter = new MatchViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        textView = findViewById(R.id.textView);
        btnMatch = findViewById(R.id.button);
        btnMatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            System.out.println("bla");
            presenter.matchSound("audioInput");
        }
    }
    @Override
    public void showMatchResults(Object results) {
        textView.setText(results.toString());
    }

    @Override
    public void showProgress() {
        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        //Toast.makeText(this, "Matching done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {

    }
}
