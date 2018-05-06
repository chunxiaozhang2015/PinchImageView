package com.boycy815.pinchimageviewexample.grid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.boycy815.pinchimageviewexample.Global;
import com.boycy815.pinchimageviewexample.R;
import com.boycy815.pinchimageviewexample.zoomtransition.PicViewActivity;
import com.boycy815.pinchimageviewexample.zoomtransition.ThumbViewActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import view.SquareImageView;

public class GridActivity extends AppCompatActivity {

    private GridView gridView = null;
    private int[] imageId = new int[] { R.drawable.a377adab44aed2e73de5f1c728b01a18b87d6fa3e, R.drawable.a810a19d8bc3eb135c0a22de5aa1ea8d3fd1f4403,
            R.drawable.a03087bf40ad162d998b6f8421ddfa9ec8a13cd10, R.drawable.ac6eddc451da81cbe337459b5e66d01608243197, R.drawable.b812c8fcc3cec3fd1547442bda88d43f87942777,
            R.drawable.b812c8fcc3cec3fdec253d2bda88d43f87942750, R.drawable.b3119313b07eca803c860ea69d2397dda04483eb, R.drawable.caef76094b36acaf465ba1bd70d98d1001e99c33,
            R.drawable.d0c8a786c9177f3e662f66537ccf3bc79f3d5650, R.drawable.e1fe9925bc315c609e11bbb781b1cb13485477e6, R.drawable.eaf81a4c510fd9f99f899a31292dd42a2934a4c8,
            R.drawable.eaf81a4c510fd9f99f899a31292dd42a2934a4c8, }; // 定义并初始化保存图片id的数组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gridView = (GridView) findViewById(R.id.gridView);
        GridViewAdapter gridViewAdapter = new GridViewAdapter();
        gridView.setAdapter(gridViewAdapter);
        // 为GridView设定监听器
        gridView.setOnItemClickListener(new gridViewListener());
    }

    class gridViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            System.out.println("arg2 = " + arg2); // 打印出点击的位置
        }
    }


    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
           // return imageId.length;
            return Global.getTestImagesCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final SquareImageView imageview; // 声明ImageView的对象
            if (convertView == null) {
                imageview = new SquareImageView(GridActivity.this); // 实例化ImageView的对象
                imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // 设置缩放方式
                imageview.setPadding(5, 5, 5, 5); // 设置ImageView的内边距
            } else {
                imageview = (SquareImageView) convertView;
            }
            imageview.setBackgroundColor(Color.rgb(0, 12, 0));

            final DisplayImageOptions thumbOptions = new DisplayImageOptions.Builder().cacheInMemory(true).build();
            final ImageLoader imageLoader = Global.getImageLoader(getApplicationContext());
            final ImageViewAware thumbAware = new ImageViewAware(imageview);
            final String url = Global.getTestImage(position).getThumb(300, 300).url;
            imageLoader.displayImage(url, thumbAware, thumbOptions);

            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GridActivity.this, PicViewActivity.class);
                    intent.putExtra("image", Global.getTestImage(position));
                    ImageSize targetSize = new ImageSize(thumbAware.getWidth(), thumbAware.getHeight());
                    String memoryCacheKey = MemoryCacheUtils.generateKey(url, targetSize);
                    intent.putExtra("cache_key", memoryCacheKey);
                    Rect rect = new Rect();
                    imageview.getGlobalVisibleRect(rect);
                    intent.putExtra("rect", rect);
                    intent.putExtra("scaleType", imageview.getScaleType());
                    startActivity(intent);
                }
            });

            //imageview.setImageResource(imageId[position]); // 为ImageView设置要显示的图片
            return imageview; // 返回ImageView
        }
    }

}
