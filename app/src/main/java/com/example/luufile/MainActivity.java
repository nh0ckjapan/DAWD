package com.example.luufile;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    EditText edtDuLieu, edtTenFile;
    Button btnLuuDuLieu, btnDocDuLieu;
    FileOutputStream outputStream;
    RadioButton rbInter, rbExter;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtDuLieu = findViewById(R.id.edtDuLieu);
        btnLuuDuLieu = findViewById(R.id.btnLuuDuLieu);
        btnDocDuLieu = findViewById(R.id.btnDocDuLieu);
        edtTenFile = findViewById(R.id.edtTenFile);
        rbExter = findViewById(R.id.rbExter);
        rbInter = findViewById(R.id.rbInter);
        rg = findViewById(R.id.radioGroup);

        this.rbInter.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnLuuDuLieu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        writeInter();
                    }
                });
                btnDocDuLieu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readInter();
                    }
                });
            }
        });


        this.rbExter.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnLuuDuLieu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        writeExter();
                    }
                });
                btnDocDuLieu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readExter();
                    }
                });
            }
        });
    }

    public void writeInter() {
        String dulieu = edtDuLieu.getText().toString();
        try {
            GhiDuLieu(dulieu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void GhiDuLieu(String dulieu) throws IOException {
        try {
            outputStream = openFileOutput(edtTenFile.getText() + ".txt", Context.MODE_PRIVATE);
            outputStream.write(dulieu.getBytes());
            Toast.makeText(this, "Ghi Du Lieu thanh COng", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
    }

    public void readInter() {
        try {
            String dulieu;
            FileInputStream inputStream = openFileInput(edtTenFile.getText() + ".txt");
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer chuoDuLieu = new StringBuffer();
            while ((dulieu = bufferedReader.readLine()) != null) {
                chuoDuLieu.append(dulieu + " ");
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();
            Toast.makeText(this, chuoDuLieu, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean KiemTraTheNhoChiDoc() {
        String trangthai = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(trangthai)) {
            return true;
        }
        return false;
    }

    private boolean KiemTraTheNhoDaGan() {
        String trangthai = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(trangthai)) {
            return true;
        }
        return false;
    }

    public void writeExter() {
        if (KiemTraTheNhoDaGan() == false) {
            Toast.makeText(this, "Bạn chưa gắn thẻ nhớ!", Toast.LENGTH_SHORT).show();

        } else {
            if (KiemTraTheNhoChiDoc() == true) {
                Toast.makeText(this, "Thẻ Nhớ chỉ đọc", Toast.LENGTH_SHORT).show();
            } else {
                File folderTheNho = new File(Environment.getExternalStorageDirectory().getPath() + "/TheNho");
                folderTheNho.mkdirs();
                File fileTheNho = new File(folderTheNho.getPath(), edtTenFile.getText() + ".txt");
                try {
                    FileOutputStream outputStream = new FileOutputStream(fileTheNho);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                    bufferedOutputStream.write(edtDuLieu.getText().toString().getBytes());
                    bufferedOutputStream.close();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void readExter() {
        if (KiemTraTheNhoDaGan() == false) {
            Toast.makeText(this, "Bạn chưa gắn thẻ nhớ!", Toast.LENGTH_SHORT).show();

        } else {
            if (KiemTraTheNhoChiDoc() == true) {
                Toast.makeText(this, "Thẻ Nhớ chỉ đọc", Toast.LENGTH_SHORT).show();
            } else {
                File fileMuonDoc = new File(Environment.getExternalStorageDirectory().getPath() + "/TheNho", edtTenFile.getText() + ".txt");
                try {
                    FileInputStream inputStream = new FileInputStream(fileMuonDoc);
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String dulieu = " ";
                    StringBuilder stringBuffer = new StringBuilder();
                    while ((dulieu = bufferedReader.readLine()) != null) {
                        stringBuffer.append(dulieu);
                    }
                    bufferedReader.close();
                    reader.close();
                    inputStream.close();
                    Toast.makeText(MainActivity.this, stringBuffer, Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

