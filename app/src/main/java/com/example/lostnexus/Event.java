package com.example.lostnexus;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class Event extends MutableLiveData {
    private AtomicBoolean pending = new AtomicBoolean(false);

    @Override
    public void observe(LifecycleOwner owner, Observer observer) {
        super.observe(owner, new Observer<String >() {
            @Override
            public void onChanged(String st) {
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(st);
                }
            }
        });
    }

    public void setValue(String st) {
        pending.set(true);
        super.setValue(st);
    }


}
