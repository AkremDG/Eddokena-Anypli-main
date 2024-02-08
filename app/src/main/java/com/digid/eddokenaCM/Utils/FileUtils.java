package com.digid.eddokenaCM.Utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class FileUtils {
    public static File getDataDir(Context context) {

        String path = context.getFilesDir().getAbsolutePath() + "/SampleZip";

        File file = new File(path);

        if (!file.exists()) {

            file.mkdirs();
        }

        return file;
    }

    public static File getDataDir(Context context, String folder) {

        String path = context.getFilesDir().getAbsolutePath() + "/" + folder;
        Log.e("eeeeeeeeeeeeee", "getDataDir: " + path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
