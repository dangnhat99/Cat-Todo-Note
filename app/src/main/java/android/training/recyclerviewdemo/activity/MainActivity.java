package android.training.recyclerviewdemo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.training.database.NoteDatabaseHelper;
import android.training.recyclerviewdemo.fragment.StickerDialogFragment;
import android.training.recyclerviewdemo.R;
import android.training.recyclerviewdemo.adapter.NoteAdapter;
import android.training.recyclerviewdemo.model.CapooCat;
import android.training.recyclerviewdemo.model.Note;
import android.training.recyclerviewdemo.utils.ChoiceDialog;
import android.training.recyclerviewdemo.utils.EditTextDialog;
import android.training.recyclerviewdemo.utils.event.StickerChooseEvent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteAdapterClickListener {
    private final static String DIALOG_TAG = "dialog";
    private final static int STICKER_INTENT_CODE = 1;

    ArrayList<Note> noteArrayList = new ArrayList<>();
    NoteAdapter adapter;

    RecyclerView rvNote;
    FloatingActionButton fabAddNote;
    ImageView btnDeleteAll;

    int itemClickPosition = -1;

    NoteDatabaseHelper noteDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    void initView() {
        noteDatabaseHelper = new NoteDatabaseHelper(this);

        noteArrayList.addAll(noteDatabaseHelper.getAllNotes());

        rvNote = findViewById(R.id.rvNote);
        fabAddNote = findViewById(R.id.fabAddNote);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);

        adapter = new NoteAdapter(this, noteArrayList, this);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(adapter);
    }

    void initListener() {
        fabAddNote.setOnClickListener(v -> {
            EditTextDialog editTextDialog = new EditTextDialog(
                    this,
                    getString(R.string.add_new_note),
                    getString(R.string.write_the_message_here),
                    getString(R.string.ok),
                    getString(R.string.cancel)
            );
            editTextDialog.setOnEditTextDialogListener(new EditTextDialog.OnEditTextDialogListener() {

                @Override
                public void onAccept(String editText) {
                    Note noteAdd = new Note(editText);

                    if (noteDatabaseHelper.addNote(noteAdd)) {
                        noteArrayList.add(new Note(editText));
                        adapter.notifyItemInserted(noteArrayList.size() - 1);
                    }
                }

                @Override
                public void onCancel() {
                }
            });

            editTextDialog.show();
        });

        btnDeleteAll.setOnClickListener(v -> {
            new ChoiceDialog(
                    this,
                    getString(R.string.delete_all_notes),
                    getString(R.string.are_you_sure_delete_all_your_notes),
                    getString(R.string.ok),
                    getString(R.string.cancel), () -> {
                if (noteDatabaseHelper.deleteAllNotes()) {
                    noteArrayList.clear();
                    adapter.notifyDataSetChanged();
                };
            }).show();
        });
    }

    @Override
    public void onItemCLick(int position) {
        //todo
        Note currentNote = noteArrayList.get(position);

        currentNote.setColor(currentNote.getColor().next());
        Log.d("MAIN", "changeColor: " + currentNote.getColor().name());
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onCheckBoxCLick(int position) {
        Note currentNote = noteArrayList.get(position);
        currentNote.setDone(!currentNote.getDone());
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onImageClick(int position) {
        //todo
        itemClickPosition = position;
//        showDialogSticker();
        showChooseStickerActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event instanceof StickerChooseEvent) {

            noteArrayList.get(itemClickPosition).setImgNote(((StickerChooseEvent) event).getCapooCat());
            adapter.notifyItemChanged(itemClickPosition);
        }
    }

    private void showChooseStickerActivity() {
        startActivityForResult(new Intent(this, SelectStickerActivity.class), STICKER_INTENT_CODE);
    }

    void showDialogSticker() {
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
//        if (prev != null) {
//            fragmentTransaction.remove(prev);
//        }
//        fragmentTransaction.addToBackStack(null);

        StickerDialogFragment dialogFragment = new StickerDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), DIALOG_TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == STICKER_INTENT_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null ) {
               String mStickerName =  data.getStringExtra(SelectStickerActivity.STICKER_EXTRA);

                noteArrayList.get(itemClickPosition).setImgNote(CapooCat.valueOf(mStickerName));
                adapter.notifyItemChanged(itemClickPosition);
            }
        }
    }
}