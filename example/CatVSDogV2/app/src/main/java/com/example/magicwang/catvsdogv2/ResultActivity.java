package com.example.magicwang.catvsdogv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by MagicWang on 2016/2/6.
 */
public class ResultActivity extends ActionBarActivity {
    private String mFilePath;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = (TextView)findViewById(R.id.result_text);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        mImageView = (ImageView) findViewById(R.id.resultiv);
        Intent getintent=getIntent();
        mFilePath=getintent.getStringExtra("filePath");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mFilePath);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            mImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件不存在!", Toast.LENGTH_LONG).show();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
