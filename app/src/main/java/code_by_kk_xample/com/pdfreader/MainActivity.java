package code_by_kk_xample.com.pdfreader;



import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.media.audiofx.AudioEffect;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfView = (PDFView)findViewById(R.id.pdfView);

       // new RetrievePDFStream().execute("http://www.tutorialspoint.com/java/java_tutorial.pdf");
        new RetrievePDFBytes().execute("http://iiti.ac.in/people/~tanimad/JavaTheCompleteReference.pdf");
    }
    class RetrievePDFBytes extends AsyncTask<String,Void,byte[]> {

        @Override
        protected byte[] doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection URLConnection = (HttpURLConnection)url.openConnection();

                if (URLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(URLConnection.getInputStream());
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new byte[0];
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            pdfView.fromBytes(bytes).load();
        }
   /* class RetrievePDFStream extends AsyncTask<String,Void,InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection URLConnection = (HttpURLConnection)url.openConnection();

                if (URLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(URLConnection.getInputStream());
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        } */
    }
}