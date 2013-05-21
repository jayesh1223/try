package com.anurag.scribble;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anurag.scribble.DrawView;

public class MainActivity extends Activity implements OnClickListener{
    DrawView drawView;
    Button bSave;
    TextView point;
    //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "test.png");
    //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.parent);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpVariables();
    }
    private void setUpVariables(){
    	//drawView = new DrawView(this);
        drawView = (DrawView) findViewById(R.id.drawView1);
        drawView.setBackgroundColor(Color.WHITE);
        drawView.requestFocus();
        bSave = (Button) findViewById(R.id.bSave);
        bSave.setOnClickListener(this);
        point = (TextView) findViewById(R.id.point);
        point.setText(drawView.Show);
        //bSave.setOnClickListener(this);
        /*try {
			file.createNewFile();
			System.out.println("here2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
   
	@Override
	public void onClick(View arg0) {
		drawView.setDrawingCacheEnabled(true);
		Bitmap b = drawView.getDrawingCache();	
		// TODO Auto-generated method stub
		switch (arg0.getId()){
		case (R.id.bClear):
			drawView.clear();
			System.out.println("entered clear");
			break;
		case (R.id.bSave):
			System.out.println("entered bsave");
			String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images"); 
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-"+ n +".jpeg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete (); 
		try {
		FileOutputStream out = new FileOutputStream(file);
		System.out.println("entered try");
		b.compress(Bitmap.CompressFormat.JPEG, 90, out);
		out.flush();
		out.close();
		} catch (Exception e) {
		e.printStackTrace();}
			break;}
		//point.setText(drawView.giveShow());
        /*System.out.println("here1");
		drawView.setDrawingCacheEnabled(true);
		Bitmap b = drawView.getDrawingCache();
		FileOutputStream fos = null;
		try {
		fos = new FileOutputStream(file);
		b.compress(CompressFormat.PNG, 95, fos);
		fos.flush();
		fos.close();
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		}
	}
    
