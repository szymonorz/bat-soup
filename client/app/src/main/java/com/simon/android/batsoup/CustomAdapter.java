package com.simon.android.batsoup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simon.android.batsoup.model.User;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<User> users;
    private Context context;

    public CustomAdapter(ArrayList<User> users, Context context)
    {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_view, parent, false);
        }
        User user = getItem(position);
        TextView uname = convertView.findViewById(R.id.username);
        uname.setText(user.getLogin());
        TextView x = convertView.findViewById(R.id.x);
        x.setText(String.format("%5.3f",user.getLocation().getX()));
        TextView y = convertView.findViewById(R.id.y);
        y.setText(String.format("%5.3f",user.getLocation().getY()));

        return convertView;
    }
}
