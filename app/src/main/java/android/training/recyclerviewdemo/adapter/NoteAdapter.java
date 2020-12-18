package android.training.recyclerviewdemo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.training.recyclerviewdemo.R;
import android.training.recyclerviewdemo.model.Note;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final static String TAG = "NOTE_ADAPTER";

    Context context;
    ArrayList<Note> arrayList;
    NoteAdapterClickListener callBack;

    public NoteAdapter(Context context, ArrayList<Note> arrayList, NoteAdapterClickListener callBack) {
        this.context = context;
        this.arrayList = arrayList;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Class LayoutInflater help turn a layout xml file into a View
        //read more at
        //https://viblo.asia/p/tim-hieu-ve-layoutinflater-trong-android-07LKXzL2lV4
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //Create ViewHolder class
    class NoteViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        ImageView imgNote;
        TextView txtNote;
        CheckBox cbNoteDone;

        //read more at
        // https://www.howkteam.vn/course/khoa-hoc-lap-trinh-android-co-ban/recyclerview-va-viewholder-trong-android-studio-126
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.layoutNoteRoot);
            imgNote = itemView.findViewById(R.id.imgNote);
            txtNote = itemView.findViewById(R.id.txtContent);
            cbNoteDone = itemView.findViewById(R.id.cbDone);

            //setOnClickListener is only defined once so you don't need call this function in bindView()
            //defining in constructor of ViewHolder is enough
            rootView.setOnClickListener(v -> {
                callBack.onItemCLick(getAdapterPosition());
            });
            imgNote.setOnClickListener(v -> {
                callBack.onImageClick(getAdapterPosition());
            });
            cbNoteDone.setOnClickListener(v -> {
                callBack.onCheckBoxCLick(getAdapterPosition());
            });
        }

        //this function used to update item when notify data change
        public void bindView() {
            Note currentNote = arrayList.get(getAdapterPosition());

            rootView.setCardBackgroundColor(ContextCompat.getColor(context, currentNote.getColor().colorId));

            txtNote.setText(currentNote.getContent());

            //load gif using Glide library
            //read more at https://github.com/bumptech/glide
            try {
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imgNote);
                Glide.with(context)
                        .load(currentNote.getImgNote().drawableId)
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

            cbNoteDone.setChecked(currentNote.getDone());

            txtNote.setPaintFlags(currentNote.getDone() ?Paint.STRIKE_THRU_TEXT_FLAG : 0);
        }
    }

    //note that RecyclerAdapter is only used for updating UI
    //to process something related to model, you better create a callback then do it in your activity or fragment
    //Create Callback class
    //read more about interface at
    //https://vntalking.com/interface-trong-java-ban-da-hieu-dung.html
    public interface NoteAdapterClickListener {
        //for whole item click
        void onItemCLick(int position);

        void onCheckBoxCLick(int position);

        void onImageClick(int position);
    }
}
