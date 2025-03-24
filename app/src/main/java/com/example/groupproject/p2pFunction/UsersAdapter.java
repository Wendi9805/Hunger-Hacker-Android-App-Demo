package com.example.groupproject.p2pFunction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.databinding.ItemContainerUserBinding;

import java.util.List;

/**
 * The main function of this class is to put User data from the database into a RecyclerView and visualize it.
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    //Use this list to save users and create diagram
    private final List<User> users;
    private final UserListener userListener;

    //Constructor for UserAdapter
    public UsersAdapter(List<User> users, UserListener userListener){
        this.users = users;
        this.userListener = userListener;
    }


    //Every viewHolder will charge a layout of list
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Use ItemContainerUserBinding to get layout and load layout, then create one userViewHolder
        //inflate method is to create ItemContainerUserBinding

        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()), //LayoutInflater is to get XML
                parent, //parent is the container of RecyclerView's ViewGroup
                false //Don't add it to parent, because we only need it in RecyclerView
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    //This method is to bind Users to userViewHolder
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder{

        ItemContainerUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        //This method is to set users data to ViewHolder
        void setUserData(User user){
            binding.textName.setText(user.name);
            binding.textEmail.setText(user.email);
            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            //Pass the user object as parameter. This means that when the user clicks on the view,
            // the onUserClicked method will be triggered and its implementation logic will be executed.
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }

    }

    //This method is to change encodedImage into Bitmap and display it as profile photo
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);

    }
}
