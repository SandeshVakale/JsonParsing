package www.sandeshvakale.aquafadas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class CameraCapture extends Activity {
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    EditText mEdit;
    EditText mEditPhoto;
    private static final int PERMISSION_ACCESS_CAMERA = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA },
                    PERMISSION_ACCESS_CAMERA);
        }



        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        checking();
                    }
                },
                2000
        );
    }

        // pics taken by the camera using this application.


    public void checking() {





        Button capture = (Button) findViewById(R.id.btnCapture);
        mEdit   = (EditText)findViewById(R.id.edittext);
        mEditPhoto   = (EditText)findViewById(R.id.edittext1);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


               String folder = mEdit.getText().toString();
                String filePhoto = mEditPhoto.getText().toString();

                if("".equals(folder))
                {
                    mEdit.setText("aquafadas");
                    //Default Folder Name in case user didn't enter anything
                    Toast.makeText(getApplicationContext(), "Photos will be store in default folder aquafadas", Toast.LENGTH_LONG).show();
                }
                if("".equals(filePhoto))
                {
                    mEdit.setText("aqua");
                    //Default photo Name in case user didn't enter anything
                }
                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"+folder+"/";
                File newdir = new File(dir);
                newdir.mkdirs();


                if (ContextCompat.checkSelfPermission(CameraCapture.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Here, the counter will be incremented each time, and the
                    // picture taken by camera will be stored as photoName1.jpg,photoName2.jpg
                    // and likewise.
                    count++;
                    String file = dir + filePhoto + count + ".jpg";
                    File newfile = new File(file);
                    try {
                        newfile.createNewFile();
                    } catch (IOException e) {
                    }

                    Uri outputFileUri = Uri.fromFile(newfile);

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                    startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", "Pic saved");
        }
    }
}
