package com.example.magicwang.catvsdogv2;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MagicWang on 2016/2/26.
 */
public class AnimalContentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_context, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.list_txt_content);
        ImageView pic_content=(ImageView) view.findViewById(R.id.list_pic_content);

        //getArgument获取传递过来的Bundle对象
        txt_content.setText(getArguments().getString("txt_content"));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),getArguments().getInt("pic_content"));
        pic_content.setImageBitmap(bitmap);

        TextView txt_titile=(TextView) getActivity().findViewById(R.id.txt_topbar);
        txt_titile.setText("动物信息");
        return view;
    }
}
