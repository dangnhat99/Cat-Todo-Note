package android.training.recyclerviewdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.training.recyclerviewdemo.R;
import android.training.recyclerviewdemo.adapter.StickerAdapter;
import android.training.recyclerviewdemo.model.CapooCat;

public class SelectStickerActivity extends AppCompatActivity {
    public static final String STICKER_EXTRA  = "Sticker";

    private RecyclerView mRvSticker;
    private StickerAdapter mAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sticker);

        initView();
        initListener();
    }

    void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRvSticker = findViewById(R.id.rvSticker);

        mAdapter = new StickerAdapter(this, position -> {
//            EventBus.getDefault().post(new StickerChooseEvent(CapooCat.values()[position]));
//            dismiss();
            Intent mResultIntent = new Intent();
            mResultIntent.putExtra(STICKER_EXTRA, CapooCat.values()[position].name());

            setResult(Activity.RESULT_OK, mResultIntent);
            finish();
        });

        mRvSticker.setLayoutManager(new GridLayoutManager(this, 3));
        mRvSticker.setAdapter(mAdapter);
    }

    void initListener() {
        mToolbar.setNavigationOnClickListener( v -> {
            finish();
        });
    }
}