package com.digid.eddokenaCM.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.digid.eddokenaCM.FTP.ftp.FTP;
import com.digid.eddokenaCM.FTP.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DownloadFileAsync extends AsyncTask<String, String, Boolean> {

    private Context context;
    private PostDownload callback;
    private String downloadLocation;

    public DownloadFileAsync(Context context, String downloadLocation, PostDownload callback) {
        this.context = context;
        this.callback = callback;
        this.downloadLocation = downloadLocation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... aurl) {
        int count;
        boolean success = false;

        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect("196.203.211.17", 21);
            Log.i("TestDownload", "Connected. Reply: " + ftp.getReplyString());
            ftp.login("Administrateur", "Sage@2021!");
            Log.i("TestDownload", "Logged in");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.i("TestDownload", "Downloading");
            ftp.enterLocalPassiveMode();

            Log.i("TestDownload", "doInBackground: ");

            String remoteFile2 = "/Ar_Photos.zip";

            File downloadFile2;
            if (android.os.Build.VERSION.SDK_INT >= 30){
                downloadFile2 = new File(context.getExternalFilesDir("").getAbsolutePath(), "/SccArticleImgs/Ar_Photos.zip");
            } else{
                downloadFile2 = new File(Environment.getExternalStorageDirectory(), "/SccArticleImgs/Ar_Photos.zip");
            }


            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftp.retrieveFileStream(remoteFile2);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream2.write(bytesArray, 0, bytesRead);
            }

            success = ftp.completePendingCommand();
            if (success) {
                Log.i("TestDownload", "doInBackground: File #2 has been downloaded successfully. ");
            }
            outputStream2.close();
            inputStream.close();
        } catch (IOException e) {
            Log.i("TestDownload", "doInBackground:W "+ e.getMessage());
        } finally {
            if (ftp != null) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("TestDownload", "doInBackground: Y");
                }
            }
        }
        return success;

    }

    protected void onProgressUpdate(String... progress) {
    }

    @Override
    protected void onPostExecute(Boolean unused) {
        if (unused)
            callback.downloadDone();
        else callback.downloadError();
    }

    public static interface PostDownload {
        void downloadDone();

        void downloadError();
    }
}