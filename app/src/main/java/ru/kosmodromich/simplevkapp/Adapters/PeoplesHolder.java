package ru.kosmodromich.simplevkapp.Adapters;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.kosmodromich.simplevkapp.Entities.User;
import ru.kosmodromich.simplevkapp.FriendsActivity;
import ru.kosmodromich.simplevkapp.R;

public class PeoplesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private User user;
    private ImageView avatar;
    private TextView firstName;
    private TextView lastName;

    PeoplesHolder (View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        avatar = itemView.findViewById(R.id.avatar);
        firstName = itemView.findViewById(R.id.first_name);
        lastName = itemView.findViewById(R.id.last_name);
    }

    void bindPeople(User user) {
        this.user = user;
        String photo = user.getPhoto();
        if(photo != null && photo.length() > 0) {
            Picasso.get().load(photo).into(avatar);
        }
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), FriendsActivity.class);
        String data = String.valueOf(user.getId());
        intent.putExtra("userId", data);
        v.getContext().startActivity(intent);
    }
}