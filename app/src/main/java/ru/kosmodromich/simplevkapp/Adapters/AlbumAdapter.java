package ru.kosmodromich.simplevkapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.kosmodromich.simplevkapp.Entities.Album;
import ru.kosmodromich.simplevkapp.Entities.Community;
import ru.kosmodromich.simplevkapp.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder>{
    private List<Album> albums;

    public AlbumAdapter(List<Album> albums){
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.community_list_item, parent, false);
        return new AlbumHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Album album = albums.get(position);
        holder.bindAlbum(album);
    }

    @Override
    public int getItemCount() {
        if(albums == null){
            return 0;
        }
        return albums.size();
    }
}
