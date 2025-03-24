package com.example.groupproject;

import android.content.SharedPreferences;

public interface TestContext {
    SharedPreferences getSharedPreferences(String name, int mode);
}
