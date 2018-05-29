package ru.kosmodromich.simplevkapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.kosmodromich.simplevkapp.Entities.User;
import ru.kosmodromich.simplevkapp.R;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsHolder> {

    private List<User> friends;

    public FriendsAdapter(List<User> peoples){
        this.friends = peoples;
    }

    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new FriendsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder holder, int position) {
        User user = friends.get(position);
        holder.bindFriend(user);
    }

    @Override
    public int getItemCount() {
        if(friends == null){
            return 0;
        }
        return friends.size();
    }
}