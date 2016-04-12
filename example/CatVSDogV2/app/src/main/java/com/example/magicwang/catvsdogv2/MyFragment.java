package com.example.magicwang.catvsdogv2;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by MagicWang on 2016/2/18.
 */
public class MyFragment extends Fragment {

    private String titile;

    public MyFragment(String titile){
        this.titile=titile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,container,false);
        TextView txt_titile=(TextView) getActivity().findViewById(R.id.txt_topbar);
        txt_titile.setText(titile);
        return view;
    }
}
