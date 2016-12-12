package com.inovio.jubayernil.pdfreader;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class MainActivity extends AppCompatActivity {

    WebView readerWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();
        //File sdcard = Environment.getExternalStorageDirectory();
        //Get the text file
        //File file = new File(sdcard,"conrad-shadow-line.epub");

        try {
            InputStream epubInputStream = assetManager.open("conrad-shadow-line.epub");
            //InputStream epubInputStream = openFileInput(file.getName().toString());

            Book book = (new EpubReader()).readEpub(epubInputStream);

            Log.d("epublib", "onCreate: "+book.getTitle());
            Log.d("epublib", "onCreate: "+book.getMetadata().getAuthors());

            logTableOfContents(book.getTableOfContents().getTocReferences(), 0);



        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }

    }

    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {

        if (tocReferences == null) {

            return;

        }

        for (TOCReference tocReference : tocReferences) {

            StringBuilder tocString = new StringBuilder();

            for (int i = 0; i < depth; i++) {

                tocString.append("\t");

            }

            tocString.append(tocReference.getTitle());

            Log.i("epublib", tocString.toString());


            logTableOfContents(tocReference.getChildren(), depth + 1);

        }

    }

}
