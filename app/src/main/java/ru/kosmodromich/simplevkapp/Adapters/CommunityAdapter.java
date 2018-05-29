package ru.kosmodromich.simplevkapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.kosmodromich.simplevkapp.AlbumActivity;
import ru.kosmodromich.simplevkapp.CommunityActivity;
import ru.kosmodromich.simplevkapp.Constants;
import ru.kosmodromich.simplevkapp.Entities.Community;
import ru.kosmodromich.simplevkapp.Entities.RequestCommunityData;
import ru.kosmodromich.simplevkapp.MainActivity;
import ru.kosmodromich.simplevkapp.R;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityHolder>{
    private Context context;
    private List<Community> communities;

    public CommunityAdapter(Context context, List<Community> communities){
        this.context = context;
        this.communities = communities;
    }

    @NonNull
    @Override
    public CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.community_list_item, parent, false);
        return new CommunityHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommunityHolder holder, int position) {
        final Community community = communities.get(position);
        holder.bindCommunity(community);
        holder.getAvatar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumActivity.class);
                intent.putExtra("ownerId", community.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(communities == null){
            return 0;
        }
        return communities.size();
    }
}
