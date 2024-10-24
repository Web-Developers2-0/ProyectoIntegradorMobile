package com.example.planetsuperheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private Context context;
    private List<Comic> comics;

    public ComicAdapter(Context context, List<Comic> comics) {
        this.context = context;
        this.comics = comics;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de cada tarjeta de cómic (item_comic.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_comic, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        Comic comic = comics.get(position);

        // Asignar datos a las vistas del ViewHolder
        holder.comicTitle.setText(comic.getTitle());
        holder.comicSubtitle.setText(comic.getSubtitle());
        holder.comicRating.setText(String.valueOf(comic.getRating()));

        // Asignar la imagen del cómic
        holder.comicImage.setImageResource(comic.getImageResId());
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {

        TextView comicTitle, comicSubtitle, comicRating;
        ImageView comicImage, comicStarIcon;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);

            // Vincular las vistas del diseño (item_comic.xml)
            comicTitle = itemView.findViewById(R.id.comic_title);
            comicSubtitle = itemView.findViewById(R.id.comic_subtitle);
            comicRating = itemView.findViewById(R.id.comic_rating);
            comicImage = itemView.findViewById(R.id.comic_image);
            comicStarIcon = itemView.findViewById(R.id.comic_star_icon);
        }
    }
}