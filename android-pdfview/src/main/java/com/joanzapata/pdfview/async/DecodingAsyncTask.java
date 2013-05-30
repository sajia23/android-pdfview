package com.joanzapata.pdfview.async;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.joanzapata.pdfview.PDFView;

import org.vudroid.core.DecodeService;
import org.vudroid.core.DecodeServiceBase;
import org.vudroid.pdfdroid.codec.PdfContext;

public class DecodingAsyncTask extends AsyncTask<Void, Void, Void> {

    /** The decode service used for decoding the PDF */
    private DecodeService decodeService;

    private boolean cancelled;

    private Uri uri;

    private PDFView pdfView;

    public DecodingAsyncTask(Uri uri, PDFView pdfView) {
        this.cancelled = false;
        this.pdfView = pdfView;
        this.uri = uri;
    }

    @Override
    protected void onPreExecute() {
        Log.i("Joan", "Will be decoding...");
    }

    @Override
    protected Void doInBackground(Void... params) {
        decodeService = new DecodeServiceBase(new PdfContext());
        decodeService.setContentResolver(pdfView.getContext().getContentResolver());
        decodeService.open(uri);
        return null;
    }

    protected void onPostExecute(Void result) {
        if (!cancelled) {
            pdfView.loadComplete(decodeService);
        }
    }

    protected void onCancelled() {
        cancelled = true;
    }
}
