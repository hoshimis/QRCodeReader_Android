package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new IntentIntegrator(this)
                // 読み取ったときにビープ音を鳴らさない
                .setBeepEnabled(false)
                // 画面の向きを固定しない
                //.setOrientationLocked(true)
                // 読み取るバーコードのフォーマットを指定する
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                // アクティビティを設定する
                //.setCaptureActivity(CustomScannerActivity.class)
                .initiateScan();
    }

    //読み取ったコードを受け取る
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_SHORT).show();

                //qrコードから取得したURLにアクセスする。
                Uri uri = Uri.parse(result.getContents());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                onDestroy();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}