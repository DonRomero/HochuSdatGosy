package ru.kosmodromich.simplevkapp.Adapters;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.kosmodromich.simplevkapp.Entities.User;
import ru.kosmodromich.simplevkapp.R;

public class FriendsHolder extends RecyclerView.ViewHolder{
    private User user;
    private ImageView avatar;
    private TextView firstName;
    private TextView lastName;

    FriendsHolder (View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        firstName = itemView.findViewById(R.id.first_name);
        lastName = itemView.findViewById(R.id.last_name);
    }

    void bindFriend(User user) {
        this.user = user;
        String photo = user.getPhoto();
        if(photo != null && photo.length() > 0) {
            Picasso.get().load(photo).into(avatar);
        }
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
    }
}