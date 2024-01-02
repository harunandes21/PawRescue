package com.example.pawrescue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AvatarAdapter extends BaseAdapter {
    private Context context;
    private List<Avatar> avatarList;

    public AvatarAdapter(Context context, List<Avatar> avatarList) {
        this.context = context;
        this.avatarList = avatarList;
    }

    @Override
    public int getCount() {
        return avatarList != null ? avatarList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(context).inflate(R.layout.item_avatar, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(avatarList.get(i).getName());
        image.setImageResource(avatarList.get(i).getImage());

        return rootView;
    }
}
