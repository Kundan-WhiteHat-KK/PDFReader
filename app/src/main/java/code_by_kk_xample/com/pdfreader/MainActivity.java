package code_by_kk_xample.com.pdfreader;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private int pageCount = 0;
    private Button previous, next;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previous = (Button)findViewById(R.id.previous);
        next = (Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount++;
                render();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount--;
                render();
            }
        });
    }
    private void render() {
        try {
            imageView = (ImageView)findViewById(R.id.image);
            int weidth = imageView.getWidth();
            int height = imageView.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(weidth,height,Bitmap.Config.ARGB_4444);

            File file = new File("sdcard/download/sample.pdf");
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file , ParcelFileDescriptor.MODE_READ_ONLY));

            if (pageCount < 0) {
                pageCount = 0;
            } else if (pageCount > renderer.getPageCount()) {
                pageCount = renderer.getPageCount()-1;
            }
            Matrix matrix = imageView.getImageMatrix();
            Rect rect = new Rect(0,0,weidth,height);
            renderer.openPage(pageCount).render(bitmap,rect,matrix,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageMatrix(matrix);
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}