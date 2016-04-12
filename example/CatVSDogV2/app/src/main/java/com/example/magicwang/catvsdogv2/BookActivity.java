package com.example.magicwang.catvsdogv2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener
{
    private RadioGroup rg_tab_bar;
    private RadioButton rb_picture;

    private MyFragment fg1,fg3;
    private BookFragment fg2;
    private FragmentManager fManager;

    private List<Animal> mData = null;
    private Context mContext;
    private long exitTime = 0;
    private TextView txt_titile;

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        txt_titile=(TextView) findViewById(R.id.txt_topbar);
        mContext=BookActivity.this;
        fManager=getFragmentManager();
        rg_tab_bar=(RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_picture = (RadioButton) findViewById(R.id.rb_picture);
        rb_picture.setChecked(true);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId){
        FragmentTransaction fTransaction = fManager.beginTransaction();
        switch (checkedId){
            case R.id.rb_picture:
                fg1 = new MyFragment("猫狗识别");
                fTransaction.replace(R.id.ly_content,fg1);
                break;
            case R.id.rb_book:
                mData=new LinkedList<Animal>();
                mData.add(new Animal("狗一", "我是狗一", R.drawable.dog,"这里是狗一的内容"));
                mData.add(new Animal("狗二", "我是狗二", R.drawable.dog,"这里是狗二的内容"));
                mData.add(new Animal("狗三", "我是狗三", R.drawable.dog,"这里是狗三的内容"));
                mData.add(new Animal("猫一", "我是猫一", R.drawable.cat,"这里是猫一的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                mData.add(new Animal("猫二", "你是猫二", R.drawable.cat,"这里是猫二的内容"));
                fg2 = new BookFragment("猫狗数据库",fManager, mData);
                fTransaction.replace(R.id.ly_content, fg2);
                break;
            case R.id.rb_setting:
                fg3 = new MyFragment("设置");
                fTransaction.replace(R.id.ly_content,fg3);
                break;
        }
        fTransaction.commit();
    }

    public void takePicture(View view){
        Intent testIntent = new Intent(BookActivity.this, TestActivity.class);
        testIntent.putExtra("key","fromPic");
        startActivity(testIntent);
    }
    public void openAlbum(View view){
        Intent openAlbumIntent = new Intent(BookActivity.this, TestActivity.class);
        openAlbumIntent.putExtra("key","fromAlbum");
        startActivity(openAlbumIntent);
    }
    //点击回退键的处理：判断Fragment栈中是否有Fragment
    //没，双击退出程序，否则像是Toast提示
    //有，popbackstack弹出栈
    @Override
    public void onBackPressed() {
        if (fManager.getBackStackEntryCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        } else {
            fManager.popBackStack();
            txt_titile.setText("猫狗数据库");
        }
    }

}
