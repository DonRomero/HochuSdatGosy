package ru.kosmodromich.simplevkapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.kosmodromich.simplevkapp.Entities.User;
import ru.kosmodromich.simplevkapp.R;

public class PeoplesAdapter extends RecyclerView.Adapter<PeoplesHolder> {

    private List<User> peoples;

    public PeoplesAdapter(List<User> peoples){
        this.peoples = peoples;
    }

    @NonNull
    @Override
    public PeoplesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new PeoplesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PeoplesHolder holder, int position) {
        User user = peoples.get(position);
        holder.bindPeople(user);
    }

    @Override
    public int getItemCount() {
        if(peoples == null){
            return 0;
        }
        return peoples.size();
    }
}
