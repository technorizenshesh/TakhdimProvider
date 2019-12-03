package com.techno.takhdimprovider.Utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Tools {
    public static Tools get() {
        return new Tools();
    }
    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
