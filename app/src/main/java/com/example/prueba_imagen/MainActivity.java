package com.example.prueba_imagen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    ImageView imagen, imagen2,image23;
    TextView textV, text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as);
        setContentView(R.layout.activity_main);

        imagen = (ImageView) findViewById(R.id.imageid);
        imagen2 = (ImageView) findViewById(R.id.imageView4);
        image23 = (ImageView)findViewById(R.id.image23);
        text1 = (TextView) findViewById(R.id.textView3);
        textV = (TextView) findViewById(R.id.textid);
    }

    public void cargar_imagen(View view) {
        cargar();
    }

    private void cargar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
            String re = path.getPath();
            text1.setText(re);

            textV.setText(getRealPathFromURI(getApplicationContext(),path));

        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri){
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,null,null);
            int comumn_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(comumn_index);
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }


    private conexion con = new conexion();
    private Connection cn = con.conexionBD();


    public void regi(View view) {
        registro();
    }

    public void registro(){
        try {

            File file = new File(textV.getText().toString());
            byte[] icon = new byte[(int) file.length()];
            InputStream input = new FileInputStream(file);
            input.read(icon);

            PreparedStatement pst = cn.prepareStatement("insert into imagen values(?)");

            pst.setBytes(1,icon);

            Intent intent = new Intent(MainActivity.this, as.class);
            startActivity(intent);

            pst.executeUpdate();

        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
