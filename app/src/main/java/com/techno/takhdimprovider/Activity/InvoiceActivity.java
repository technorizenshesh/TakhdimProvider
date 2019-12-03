package com.techno.takhdimprovider.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.techno.takhdimprovider.R;
public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_review;
    private ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        findId();
        btn_review.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }
    private void findId() {
        btn_review = findViewById(R.id.btn_review);
        img_back = findViewById(R.id.img_back);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }else if (v==btn_review){
            showDialog();
        }
    }
    private void showDialog(){
        BottomSheetDialog dialog = new BottomSheetDialog(InvoiceActivity.this);
        dialog.setContentView(R.layout.rating_dialog_view);
        dialog.show();
    }
}
