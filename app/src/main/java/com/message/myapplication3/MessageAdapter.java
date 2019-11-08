package com.message.myapplication3;


import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    public boolean isPlaying = false;
    private MediaPlayer mPlayer;
    private StorageReference mStorage;
    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        public TextView messageText;
        public TextView messageTimeText;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;
        public Button audioButton;
        public ImageView audioImage;
        public boolean isPlaying = false;
        private MediaPlayer mPlayer;
        private int STORAGE_PERMISSION_CODE = 1;


        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            messageTimeText = (TextView) view.findViewById(R.id.time_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            audioImage = (ImageView) view.findViewById(R.id.play_audio_image);

        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        final Messages c = mMessageList.get(i);

        String from_user = c.getFrom();
        String message_type = c.getType();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();

                viewHolder.displayName.setText(name);

                Picasso.with(viewHolder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text")) {

            viewHolder.messageText.setText(c.getMessage());
            viewHolder.messageImage.setVisibility(View.INVISIBLE);
            viewHolder.audioImage.setVisibility(View.INVISIBLE);


        } else if(message_type.equals("image")){

            viewHolder.messageText.setVisibility(View.INVISIBLE);
            viewHolder.audioImage.setVisibility(View.INVISIBLE);
            Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);

        } else if(message_type.equals("audio")){
            viewHolder.messageText.setVisibility(View.INVISIBLE);
            viewHolder.messageImage.setVisibility(View.INVISIBLE);
            Log.e("asdasdasdsd", c.getMessage());
           Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage()).noFade()
                    .placeholder(R.drawable.play_audio_button).into(viewHolder.audioImage);

            viewHolder.audioImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view == viewHolder.audioImage){

                        if(isPlaying == false){
                            isPlaying = true;
                            Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage()).noFade()
                                    .placeholder(R.drawable.pause).into(viewHolder.audioImage);
                            mPlayer = new MediaPlayer();
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference down = storage.getReferenceFromUrl(c.getMessage());

                            down.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try{
                                        String url = String.valueOf(uri);
                                        mPlayer.setDataSource(url);
                                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mediaPlayer) {
                                                mediaPlayer.start();
                                                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage()).noFade()
                                                        .placeholder(R.drawable.play_audio_button).into(viewHolder.audioImage);
                                            }
                                        });
                                        mPlayer.prepare();
                                    } catch (IOException e){
                                        e.printStackTrace();
                                    }
                                 }
                            });



                            Log.e("Button", "PLAYYYYYYYY1231 Listening");
                        } else {
                            isPlaying = false;
                            Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage()).noFade()
                                    .placeholder(R.drawable.pause).into(viewHolder.audioImage);

                            try{
                                mPlayer.release();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            mPlayer = null;

                            Log.e("Button", "STOOOOOPPP123213");
                        }
                    }
                }
            });
        }

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        viewHolder.messageTimeText.setText(currentTime);

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}
