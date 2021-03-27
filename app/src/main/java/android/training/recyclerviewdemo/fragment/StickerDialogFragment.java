package android.training.recyclerviewdemo.fragment;

import android.os.Bundle;
import android.training.recyclerviewdemo.R;
import android.training.recyclerviewdemo.activity.MainActivity;
import android.training.recyclerviewdemo.adapter.StickerAdapter;
import android.training.recyclerviewdemo.model.CapooCat;
import android.training.recyclerviewdemo.utils.event.StickerChooseEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

public class StickerDialogFragment extends DialogFragment {
    RecyclerView rvSticker;
    StickerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sticker_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initListener();
    }

    void initView(View view) {

        rvSticker = view.findViewById(R.id.rvSticker);

        adapter = new StickerAdapter(getContext(), position -> {
            EventBus.getDefault().post(new StickerChooseEvent(CapooCat.values()[position]));
            dismiss();
        });

        rvSticker.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvSticker.setAdapter(adapter);
    }

    void initListener() {
    }
}
