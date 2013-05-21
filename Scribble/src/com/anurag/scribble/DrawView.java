package com.anurag.scribble;
import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;

public class DrawView extends View implements OnTouchListener {
	private Bitmap  mBitmap;
	private Canvas  mCanvas;
	private Path    mPath;
	private Paint   mBitmapPaint;
	private Paint   mPaint;
	private PathMeasure mPM;
	float aCoordinates[] = {1f, 1f};
	EditText showPoints;
	String Show = "";

	public DrawView(Context c) {
	    super(c);

	    mPath = new Path();
	    mPM = new PathMeasure(mPath, false);
	    mBitmapPaint = new Paint(Paint.DITHER_FLAG);

	    //showPoints = (TextView) findViewById.(R.id.editText1);
	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setDither(true);
	    mPaint.setColor(0xFF000000);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(3);
	}
	public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPath = new Path();
        mPM = new PathMeasure(mPath, false);
	    mBitmapPaint = new Paint(Paint.DITHER_FLAG);

	    //showPoints = (TextView) findViewById.(R.id.editText1);
	    
	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setDither(true);
	    mPaint.setColor(0xFF000000);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(3);
    }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	    super.onSizeChanged(w, h, oldw, oldh);
	    mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	    mCanvas = new Canvas(mBitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

	    canvas.drawPath(mPath, mPaint);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 0;

	private void touch_start(float x, float y) {
	    mPath.reset();
	    mPath.moveTo(x, y);
	    mX = x;
	    mY = y;
	}
	private void touch_move(float x, float y) {
	    float dx = Math.abs(x - mX);
	    float dy = Math.abs(y - mY);
	    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
	        mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
	        mX = x;
	        mY = y;
	    }
	    mPM = new PathMeasure(mPath, false);
	    System.out.println(Float.toString(mPM.getLength()));
	}
	
	    
	
	private void touch_up() {
	    mPath.lineTo(mX, mY);
	    // commit the path to our offscreen
	    mCanvas.drawPath(mPath, mPaint);
	    Show = "";
	    Show = getPoints();
	    System.out.println(Show);
	    mPath.reset();
	    /*mPM = new PathMeasure(mPath, false);
	    System.out.println(Float.toString(mPM.getLength()));
	    mPM.getPosTan(mPM.getLength() * 0.5f, aCoordinates, null);
	    // kill this so we don't double draw
	    mPath.reset();
	    System.out.println(Float.toString(aCoordinates[0]));
	    mPaint.setColor(Color.RED);
	    mCanvas.drawCircle(aCoordinates[0], aCoordinates[1], 5, mPaint);
	    mPaint.setColor(Color.BLACK);*/
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    float x = event.getX();
	    float y = event.getY();

	    switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            touch_start(x, y);
	            invalidate();
	            break;
	        case MotionEvent.ACTION_MOVE:
	            touch_move(x, y);
	            invalidate();
	            break;
	        case MotionEvent.ACTION_UP:
	            touch_up();
	            invalidate();
	            break;
	    }
	    return true;
	}

	public void clear(){
	    mBitmap.eraseColor(Color.TRANSPARENT);
	    invalidate();
	    System.gc();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
	    float y = event.getY();

	    switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            touch_start(x, y);
	            invalidate();
	            break;
	        case MotionEvent.ACTION_MOVE:
	            touch_move(x, y);
	            invalidate();
	            break;
	        case MotionEvent.ACTION_UP:
	            touch_up();
	            invalidate();
	            break;
	    }
	    return true;
	}
	public String getPoints(){
		//showPoints = (EditText) findViewById.(R.id.editText1);
	    String points = "";
		mPM = new PathMeasure(mPath, false);
		for(int i = 1; i<=20; i++)
		{
			mPM.getPosTan(mPM.getLength() * i/20, aCoordinates, null);
			points = points.concat(",(");
			points = points.concat(Float.toString(aCoordinates[0]));
			points = points.concat(",");
			points = points.concat(Float.toString(aCoordinates[1]));
			points = points.concat(")");
		    //points = points.concat(",(".concat(Float.toString(aCoordinates[0])).concat(",").concat(Float.toString(aCoordinates[1])).concat(")"));         
		    //System.out.println("concatenated");
		}
		return points;
		
	}
}


