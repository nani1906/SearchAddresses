package com.example.naveen.searchaddresses.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.naveen.searchaddresses.R;
import com.example.naveen.searchaddresses.core.SelectedLocation;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<SelectedLocation>{

    private ArrayList<SelectedLocation> dataSet;
    Context mContext;

    // This custom adapter for adding list of locations dynamically
    // Following fields will be shown in the list item.
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    public CustomAdapter(ArrayList<SelectedLocation> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SelectedLocation selectedLocation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.selectedLocationName);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.description);

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        // This is a custom animation effects for UI purpose.
        // For up scroll and down scroll animations.
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(selectedLocation.getTitle());

        // Here am showing the "id" as Pickup number because of lack of information about pickup id in the question:

        viewHolder.txtType.setText("Pickup: "+selectedLocation.getId());

        // Return the completed view to render on screen

        return convertView;
    }
}
