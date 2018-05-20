package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchView extends Activity implements IMatchViewPresenter.View, View.OnClickListener  {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button btnMatch;
    @BindView(R.id.list_match)
    ListView matchBirds;
    @BindView(R.id.progress)
    ProgressBar matchProgress;
    @BindView(R.id.birdlist)
    ImageButton birdlist;

    private IMatchViewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_layout);
        this.presenter = new MatchViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        btnMatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            presenter.matchSound("audioInput");
        }
    }
    @Override
    public void showMatchResults(List<MatchResult> results) {


        /*StringBuilder builder = new StringBuilder();
        results.forEach(result->
                builder.append(result.getPercentage())
                        .append(": ")
                        .append(result.getName())
                        .append("\n"));
        textView.setText(builder.toString());*/

        List<String> matchResult = new ArrayList<String>();
        for (MatchResult result : results) {
            matchResult.add(result.getName() /*+ " " + result.getPercentage()*/);
        }

        ListAdapter birdListAdapter = new ArrayAdapter(
                this,
                R.layout.match_bird_item,
                R.id.match_bird_text,
                matchResult);

        matchBirds.setAdapter(birdListAdapter);
        matchBirds.setVisibility(View.VISIBLE);
        textView.setText("MATCHES");
    }

    @OnItemClick(R.id.list_match)
    public void onItemClick(int position) {
        Toast.makeText(this, "on item click" + matchBirds.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        //TODO get ID
        //int id = matchBirds.getItemAtPosition(position);
        //intent.putExtra(MatchBirdDetailView.BIRD_ID, id);
        String id = matchBirds.getItemAtPosition(position).toString();
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    @OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        matchProgress.setVisibility(View.VISIBLE);
        textView.setText("recording");
        matchProgress.animate();
        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        matchBirds.setVisibility(View.VISIBLE);
        matchProgress.setVisibility(View.GONE);
        //Toast.makeText(this, "Matching done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {

    }
}
