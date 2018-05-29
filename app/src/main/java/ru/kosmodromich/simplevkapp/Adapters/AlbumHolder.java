package ru.kosmodromich.simplevkapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.kosmodromich.simplevkapp.Entities.Album;
import ru.kosmodromich.simplevkapp.Entities.Community;
import ru.kosmodromich.simplevkapp.R;

public class AlbumHolder extends RecyclerView.ViewHolder{

    private TextView title;
    private ImageView avatar;

    AlbumHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.community_name);
        avatar = itemView.findViewById(R.id.avatar);
    }

    void bindAlbum(Album album) {
        String photo = album.getPhoto();
        if(photo != null && photo.length() > 0) {
            Picasso.get().load(photo).into(avatar);
        }
        title.setText(album.getTitle());
    }
}
