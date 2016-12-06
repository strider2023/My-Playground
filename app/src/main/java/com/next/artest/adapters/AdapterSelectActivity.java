package com.next.artest.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.next.artest.R;
import com.next.artest.dao.ActivityTypeDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by school on 6/12/16.
 */
public class AdapterSelectActivity extends BaseAdapter {

    private List<ActivityTypeDAO> activityTypeDAOList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public AdapterSelectActivity(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<ActivityTypeDAO> data) {
        this.activityTypeDAOList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return activityTypeDAOList.size();
    }

    @Override
    public Object getItem(int i) {
        return activityTypeDAOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.adapter_activity_types_list, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.icon.setImageResource(activityTypeDAOList.get(i).getImageId());
        holder.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/font.ttf"));
        holder.title.setText(activityTypeDAOList.get(i).getTitle());
        holder.description.setText(activityTypeDAOList.get(i).getDescription());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.adapter_activity_type_icon) ImageView icon;
        @BindView(R.id.adapter_activity_title) TextView title;
        @BindView(R.id.adapter_activity_description) TextView description;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
