package com.example.ivan.mylolstatistics;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private ImageView image;
    private TextView text;

    public MainArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values.clone();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MainArrayAdapter mViewHolder;

        if (convertView == null) {

            mViewHolder = new MainArrayAdapter(context, values);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowlayout, parent, false);
            mViewHolder.text = (TextView) convertView.findViewById(R.id.label);
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(mViewHolder);

        }else{

            mViewHolder =   (MainArrayAdapter) convertView.getTag();

        }

        mViewHolder.text.setText(values[position]);

            String s = values[position];
            if (s.startsWith("Add")) {
                mViewHolder.image.setImageResource(R.drawable.ic_action_new);
            } else if (s.startsWith("Delete")) {
                mViewHolder.image.setImageResource(R.drawable.ic_action_discard);
            } else if (s.startsWith("Edit")) {
                mViewHolder.image.setImageResource(R.drawable.ic_action_edit);
            } else if (s.startsWith("Check") || s.startsWith("Best")) {
                mViewHolder.image.setImageResource(R.drawable.ic_action_search);
            }

            //ovaj dio koda omogucava listi da koristi cijelu velicinu ekrana
            //odavdje
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int itemHeight;
            int itemWidth;
            itemHeight = height / getCount();
            itemWidth = width / getCount();

            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }

            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            int actionBarHeight = context.getResources().getDimensionPixelSize(tv.resourceId);

            int itemHeightFinal = itemHeight - (actionBarHeight / getCount()) - (result / getCount()) - getCount();

            convertView.setMinimumHeight(itemHeightFinal);
            convertView.setMinimumWidth(itemWidth);
            //do ovdje

            return convertView;
        }


}
