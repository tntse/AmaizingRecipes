package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;


/**
 * Created by Chau on 11/7/2015.
 */


public class NutritionFragment extends Fragment {

    StringBuilder nutrients = new StringBuilder();

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //do your stuff for your fragment here
        text = (TextView) getActivity().findViewById(R.id.textView7);
        if(getArguments().getString("API").equals("Food2Fork")){
            // Don't show nutrients
            text.setText("No nutrients are available for this recipe, please select nutrition options in the profile page.");
        }else {
            String[] nutrientLines = getArguments().getStringArray("Nutrients");
            int dailyTotals[] = getArguments().getIntArray("Totals");
            for (int i = 0; i < nutrientLines.length; i++) {
                nutrients.append(nutrientLines[i]);
                nutrients.append('\n');
            }
            text.setText(nutrients.toString());
            text.setMovementMethod(new ScrollingMovementMethod());
            ArrayList<BarEntry> entries = new ArrayList<>();
            int fat = dailyTotals[0];
            int carbs = dailyTotals[1];
            int protein = dailyTotals[2];

            entries.add(new BarEntry(fat, 0));
            entries.add(new BarEntry(carbs, 1));
            entries.add(new BarEntry(protein, 2));

            BarDataSet dataSet = new BarDataSet(entries, "Daily Value(%)");

            ArrayList<String> labels = new ArrayList<>();
            labels.add("Fat");
            labels.add("Carbs");
            labels.add("Protein");

            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            BarData data = new BarData(labels, dataSet);

            BarChart chart = (BarChart) getActivity().findViewById(R.id.chart);

            YAxis leftAxis = chart.getAxisLeft();
            leftAxis.setDrawGridLines(false);
            YAxis rightAxis = chart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            XAxis bottomAxis = chart.getXAxis();
            bottomAxis.setDrawGridLines(false);

            chart.setData(data);
            chart.animateY(1000);
            chart.setDescription("");



        }



    }
}
