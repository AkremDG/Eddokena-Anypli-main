package com.digid.eddokenaCM.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DecompressAync extends AsyncTask<Void, Void, Boolean> {


    private File _zipFile;
    private InputStream _zipFileStream;
    private static final String TAG = "UnzipAsyncTask";
    private DecompressCallback callback;

    public DecompressAync( File zipFile, DecompressCallback callback) {
        _zipFile = zipFile;
        this.callback = callback;

        _dirChecker(_zipFile.getParent() + "/Multimedia");
    }

    public DecompressAync( InputStream zipFile, DecompressCallback callback) {
        _zipFileStream = zipFile;
        this.callback = callback;

        _dirChecker(_zipFile.getParent());
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        return unzip();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {
            callback.decompressSuccess();
        } else {
            callback.decompressFailled();
        }
    }

    public boolean unzip() {

        boolean result = true;
        try {
            Log.i("eeeeeeeee", "Starting to unzip");
            InputStream fin = _zipFileStream;
            if (fin == null) {
                fin = new FileInputStream(_zipFile);
            }
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.i("eeeeeeeee", "Unzipping " + ze.getName());

                if (ze.isDirectory()) {
                    _dirChecker(_zipFile.getParent() + "/" + ze.getName());
                } else {
                    Log.i("eeeeeeeee", "unzip: " + _zipFile.getParent());
                    FileOutputStream fout = new FileOutputStream(new File(_zipFile.getParent(), ze.getName()));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;

                    // reading and writing
                    while ((count = zin.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                        byte[] bytes = baos.toByteArray();
                        fout.write(bytes);
                        baos.reset();
                    }

                    fout.close();
                    zin.closeEntry();
                }

            }
            zin.close();
            Log.i("eeeeeeeee", "Finished unzip");
        } catch (Exception e) {
            result = false;
            Log.i("eeeeeeeee", "Unzip Error", e);
        }


        return result;
    }

    private void _dirChecker(String dir) {
        File f = new File(dir);
        Log.i("eeeeeeeee", "creating dir " + dir);

        if (dir.length() >= 0 && !f.isDirectory()) {
            f.mkdirs();
        }
    }

}
