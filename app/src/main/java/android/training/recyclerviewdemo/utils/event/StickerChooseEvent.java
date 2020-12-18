package android.training.recyclerviewdemo.utils.event;

import android.training.recyclerviewdemo.model.CapooCat;

//Event class for EventBus
//Read more at
//https://github.com/greenrobot/EventBus

public class StickerChooseEvent {
    CapooCat capooCat;

    public StickerChooseEvent(CapooCat capooCat) {
        this.capooCat = capooCat;
    }

    public CapooCat getCapooCat() {
        return capooCat;
    }

    public void setCapooCat(CapooCat capooCat) {
        this.capooCat = capooCat;
    }
}
