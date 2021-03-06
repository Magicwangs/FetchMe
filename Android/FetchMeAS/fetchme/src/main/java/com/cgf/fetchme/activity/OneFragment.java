package com.cgf.fetchme.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.cgf.fetchme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguanfu on 3/30/2016.
 */
public class OneFragment extends Fragment {
    private static final String TAG = "OneFragment";
    private List<Person> persons;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate----in----");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate----out----");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView----IN----");
        View rootView = inflater.inflate(R.layout.recyclerview_activity, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
        Log.d(TAG, "onCreateView----out----");

        return rootView;
    }

    private void initializeData() {
        Log.d(TAG, "initializeData----in----");
        persons = new ArrayList<>();
        persons.add(new Person("American Bulldog", "23 years old", R.drawable.american_bulldog));
        persons.add(new Person("American Pit Bull Terrier", "25 years old", R.drawable.american_pit_bull_terrier));
        persons.add(new Person("Basset hound", "35 years old", R.drawable.basset_hound));
        Log.d(TAG, "initializeData----out----");
    }

    private void initializeAdapter() {
        Log.d(TAG, "initializeAdapter----in----");
        RVAdapter adapter = new RVAdapter(getActivity(), persons);
        rv.setAdapter(adapter);
        Log.d(TAG, "initializeAdapter----out----");
    }
}
