package ru.kosmodromich.simplevkapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.kosmodromich.simplevkapp.Entities.Community;
import ru.kosmodromich.simplevkapp.R;

public class CommunityHolder extends RecyclerView.ViewHolder{

    private TextView name;
    private ImageView avatar;

    public ImageView getAvatar() {
        return avatar;
    }

    CommunityHolder (View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.community_name);
        avatar = itemView.findViewById(R.id.avatar);
    }

    void bindCommunity(Community community) {
        String photo = community.getPhoto();
        if(photo != null && photo.length() > 0) {
            Picasso.get().load(photo).into(avatar);
        }
        name.setText(community.getName());
    }
}
