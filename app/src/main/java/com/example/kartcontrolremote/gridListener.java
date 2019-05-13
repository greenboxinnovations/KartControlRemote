package com.example.kartcontrolremote;

import android.view.View;

public interface gridListener {
    void changeKart(View view, int i);

    void dialog(int i);

    boolean gridCheckActive(View view, int i);

    void gridCutEngine(View view, int i);

    void makeKartActive(View view, int i);


}