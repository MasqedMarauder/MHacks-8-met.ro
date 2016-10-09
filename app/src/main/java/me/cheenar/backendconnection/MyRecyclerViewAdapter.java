package me.cheenar.backendconnection;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
 
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;
        TextView isActive;
        ImageView imageView;

 
        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            isActive = (TextView)itemView.findViewById(R.id.isActive);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }
 
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }
 
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
 
    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }
 
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);
 
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
 
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.dateTime.setText(mDataset.get(position).getmText2());
        holder.imageView.setImageBitmap(mDataset.get(position).getBitmap());

        try
        {
            Date currentDate = Calendar.getInstance().getTime();

            String s = mDataset.get(position).getObject().getString("ACTIVE_START");
            String e = mDataset.get(position).getObject().getString("ACTIVE_END");

            SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy");
            Date start = sdf.parse(s);
            Date end = sdf.parse(e);

            holder.dateTime.setText(mDataset.get(position).getmText2() + " | " + start.toString() + " -> "+ end.toString());

            System.out.println(currentDate.getTime());
            System.out.println(start.getTime());
            System.out.println(end.getTime());

            if(currentDate.getTime() >= start.getTime() && currentDate.getTime() <= end.getTime())
            {
                holder.isActive.setText("Active");
                holder.isActive.setTextColor(Color.GREEN);
            }
            else
            {
                holder.isActive.setText("Inactive");
                holder.isActive.setTextColor(Color.RED);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
 
    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
 
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
 
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
 
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}