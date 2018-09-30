package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.IMatchResultPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchResultPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchResultView extends Activity implements IMatchResultPresenter.View, View.OnClickListener {


    public static final String EXTRA_BIRD_LIST = "matchResultBirdList";

    //@BindView(R.id.list_match)
    RecyclerView matchBirds;
    //@BindView(R.id.birdlist)
    //ImageButton birdlist;
    @BindView(R.id.pieChart)
    PieChart resultChart;

    ArrayList<MatchedBird> matchedBirds;

    private File audioFile;
    private IMatchResultPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchresult);
        this.presenter = new MatchResultPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);

        //result list
        matchedBirds = (ArrayList<MatchedBird>) getIntent().getSerializableExtra(EXTRA_BIRD_LIST);
        matchBirds = (RecyclerView) findViewById(R.id.list_match);
        resultChart.setUsePercentValues(true);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (MatchedBird bird : matchedBirds) {
            if (bird.getAccuracy() * 100 > 0.5)
                entries.add(new PieEntry(bird.getAccuracy() * 100, bird.getBird().getTitle_de()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        //dataSet.setAutomaticallyDisableSliceSpacing(true);
        dataSet.setColors(new int[]{R.color.ciRed, R.color.ciDarkBlue, R.color.ciMidBlue}, this);
        //dataSet.setValueLineVariableLength(true);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(R.color.colorText);
        resultChart.setContentDescription("");
        resultChart.setHoleRadius(30);
        resultChart.setCenterTextColor(R.color.ciRed);
        resultChart.setCenterTextSize(16f);
        resultChart.setData(data);
        resultChart.invalidate();
        resultChart.setTransparentCircleRadius(35);
        resultChart.getDescription().setEnabled(false);
        resultChart.getLegend().setTextColor(R.color.colorContentText);
        resultChart.getLegend().setTextSize(16f);
        resultChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        resultChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        resultChart.getLegend().setYEntrySpace(10f);
        resultChart.getLegend().setFormToTextSpace(10f);
        resultChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        resultChart.getLegend().setYOffset(7f);
        resultChart.getLegend().setEnabled(false);
        resultChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry entry = (PieEntry) e;
                Log.e("MatchResult", "e: " + entry.getLabel());
                if (e == null)
                    return;
                else {
                    String label = entry.getLabel();
                    for (MatchedBird bird : matchedBirds) {
                        if (bird.getBird().getTitle_de().equals(label)) {
                            Intent intent = new Intent(getBaseContext(), MatchBirdDetailView.class);
                            intent.putExtra(MatchBirdDetailView.EXTRA_BIRD_KEY, bird);
                            startActivity(intent);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected() {
            }
        });

        RecyclerView.LayoutManager blLayoutManager = new LinearLayoutManager(this);
        matchBirds.setLayoutManager(blLayoutManager);
        RecyclerView.Adapter blAdapter = new BirdListAdapter(this, matchedBirds);
        matchBirds.setAdapter(blAdapter);
    }


    @Override
    public void onClick(View view) {
    }

    public void itemClick(MatchedBird bird) {
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        intent.putExtra(MatchBirdDetailView.EXTRA_BIRD_KEY, bird);
        startActivity(intent);
    }

    /*@OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent);
    }*/

    @Override
    public void showProgress() {
        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, R.string.general_exception_text, Toast.LENGTH_LONG).show();
    }

}
