package zblibrary.xscan.camera.camera;

import android.graphics.Bitmap;

import java.io.File;

public interface OnPictureTakenListener {
    void onPicktureTaken(File file, Bitmap bitmap);
}
