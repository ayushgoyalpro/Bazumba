package com.ayush.bazumba.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayush.bazumba.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

//public class PostListAdapter extends ArrayAdapter<Post> {
//    private Context ctx;
//    private int res;
//
//    public PostListAdapter(Context context, int resource,ArrayList<Post> objects) {
//        super(context, resource, objects);
//        ctx = context;
//        res = resource;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        String C_ID = getItem(position).getCid();
//        String Name = getItem(position).getTitle();
//        String Teacher = getItem(position).getTeacher();
//        Bitmap image = getItem(position).getThumbnail();
//
//        Post post = new Post(C_ID,Name,Teacher,image);
//        LayoutInflater inflater = LayoutInflater.from(ctx);
//        convertView = inflater.inflate(res,parent,false);
//
//        TextView cidtv = (TextView) convertView.findViewById(R.id.cid);
//        TextView titletv = (TextView) convertView.findViewById(R.id.name);
//        TextView teachertv = (TextView) convertView.findViewById(R.id.teacher);
//        ImageView imagebox = (ImageView) convertView.findViewById(R.id.imagebox);
//
//        cidtv.setText(C_ID);
//        titletv.setText(Name);
//        teachertv.setText(Teacher);
//        imagebox.setImageBitmap(image);
//        return convertView;
//    }
//}

class PostListAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> postList;
    LayoutInflater inflater;

    public PostListAdapter(Context context, ArrayList<HashMap<String, String>> postList) {
        this.context = context;
        this.postList = postList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postList.size() * 30;
    }

    @Override
    public HashMap<String, String> getItem(int i) {
        return postList.get(i % postList.size());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
            holder.name = view.findViewById(R.id.name);
            holder.teacher = view.findViewById(R.id.teacher);
            holder.imageView = view.findViewById(R.id.imagebox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            Log.e("Postlistadatper", "View is already in memory");
        }
        holder.name.setText(getItem(i).get("Name"));
        holder.teacher.setText(getItem(i).get("Teacher"));
        Picasso.get().load("http://192.168.1.6/images/123.jpg")
                .placeholder(R.drawable.homeicon)
                .into(holder.imageView);
        return view;
    }

    static class ViewHolder {
        TextView name, teacher;
        ImageView imageView;
    }
}
