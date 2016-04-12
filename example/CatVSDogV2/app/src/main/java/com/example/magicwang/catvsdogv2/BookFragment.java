package com.example.magicwang.catvsdogv2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MagicWang on 2016/2/22.
 */
public class BookFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private String titile;

    private FragmentManager fManager;
    private List<Animal> mData;
    private ListView list_animal;

    public BookFragment(String titile,FragmentManager fManager,List<Animal> mData){
        this.titile=titile;
        this.fManager = fManager;
        this.mData=mData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list,container,false);
        TextView txt_titile=(TextView) getActivity().findViewById(R.id.txt_topbar);
        txt_titile.setText(titile);
        list_animal=(ListView)view.findViewById(R.id.list_animal);
        AnimalAdapter myAdapter=new AnimalAdapter (mData,getActivity());
        list_animal.setAdapter(myAdapter);
        list_animal.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        FragmentTransaction fTransaction = fManager.beginTransaction();
        AnimalContentFragment ncFragment = new AnimalContentFragment();
        Bundle bd = new Bundle();
        bd.putString("txt_content", mData.get(position).getAnimalContent());
        bd.putInt("pic_content", mData.get(position).getaIcon());
        ncFragment.setArguments(bd);
        fTransaction.replace(R.id.ly_content, ncFragment);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }
}
