package com.rafaellroca.moviedb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaellroca.moviedb.R;
import com.rafaellroca.moviedb.models.VideoData;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Rafael on 3/08/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    private ClickListener clickListener;
    private List<VideoData> videosData;

    public VideosAdapter(ClickListener clickListener, List<VideoData> videosData) {
        this.clickListener = clickListener;
        this.videosData = videosData;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        final VideoData video = videosData.get(position);
        if (video != null) {
            holder.itemView.setOnClickListener(view -> clickListener.onClick(video, holder.itemView.getContext(), holder.imgThumbnail, holder.title));
            holder.title.setText(video.getTitle());
            Picasso.get()
                    .load(video.getImagePath())
                   .noFade()
                   .error(R.drawable.img_place_holder)
                    .centerInside()
                    .fit()
                    .into(holder.imgThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return videosData.size();
    }

    public void setList(List<VideoData> videoData) {
        this.videosData = videoData;
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgThumbnail;
        TextView title;

        VideoViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            imgThumbnail = itemView.findViewById(R.id.thumbnail_img);
            title = itemView.findViewById(R.id.title_video);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface ClickListener {
        void onClick(VideoData video, Context context, ImageView imageView, TextView textView);
    }
}
