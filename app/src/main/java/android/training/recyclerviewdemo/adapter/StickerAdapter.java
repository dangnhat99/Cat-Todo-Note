package android.training.recyclerviewdemo.adapter;

import android.content.Context;
import android.training.recyclerviewdemo.R;
import android.training.recyclerviewdemo.model.CapooCat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;


public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {
    private final static String TAG = "STICKER_ADAPTER";

    Context context;
    StickerAdapterClickListener callBack;
    CapooCat[] list;

    public StickerAdapter(Context context, StickerAdapterClickListener callBack) {
        this.context = context;
        this.callBack = callBack;
        this.list = CapooCat.values();
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sticker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSticker;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSticker = itemView.findViewById(R.id.imgSticker);

            imgSticker.setOnClickListener(v -> {
                callBack.onItemCLick(getAdapterPosition());
            });
        }

        void bindView() {
            try {
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imgSticker);
                Glide.with(context)
                        .load(list[getAdapterPosition()].drawableId)
                        .placeholder(R.drawable.ic_baseline_notes_24)
                        .centerCrop()
                        .transition(
                                new DrawableTransitionOptions().crossFade()
                        )
                        .into(imageViewTarget);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "bindView: " + e.getLocalizedMessage());
            }
        }
    }

    public interface StickerAdapterClickListener {
        void onItemCLick(int position);
    }
}
