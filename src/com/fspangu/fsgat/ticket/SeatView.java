package com.fspangu.fsgat.ticket;

import java.util.ArrayList;
import java.util.List;

import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.model.seatNumber;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

@SuppressLint({ "DrawAllocation", "WrongCall" })
public class SeatView extends View {
	Paint paint = new Paint();
	Bitmap seat_ok;
	Bitmap seat_selled;
	Bitmap seat_select;
	Bitmap seat_null;
	int WIDTH, HEIGHT;
	int BOX_MAX_SIZE = 50;
	int SEAT_MAX_SIZE = 40;
	int box_size = BOX_MAX_SIZE;
	int seat_size = SEAT_MAX_SIZE;
	int hang = 12;
	int lie = 5;
	double zoom;
	double zoom_beilv;
	int zoom_level = 0;
	long mLastTime;
	long mCurTime;
	Canvas canvas;
	public static List<Integer> mySeatList;// 保存选中座位
	private List<Integer> unavaliableSeatList;
	Bitmap SeatOk, SeatSelled, SeatSelect, SeatNull;
	boolean isFirstLoad = true;
	
	DisplayMetrics displayMetrics;
	seatNumber seatnumber;
	ArrayList<seatNumber> thisseatNumbersList;
	ArrayList<seatNumber> postSeatNumbersList;
	
	public SeatView(Context context,ArrayList<seatNumber> seatNumbersList) {
		super(context);
		mySeatList = new ArrayList<Integer>();
		unavaliableSeatList = new ArrayList<Integer>();
		
		thisseatNumbersList = new ArrayList<seatNumber>();
		
		thisseatNumbersList.addAll(seatNumbersList);
		
		SeatOk = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.seat_ok);
		SeatSelled = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.seat_selled);
		SeatSelect = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.seat_select);
		SeatNull = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.seat_null);
		
		displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		
	}

	public SeatView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(box_size * lie, box_size * hang);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		if (isFirstLoad) {
			double zoom_temp1 = ((displayMetrics.widthPixels * 1.0 - 100)
					/ lie / box_size);
			double zoom_temp2 = ((displayMetrics.heightPixels * 1.0 /2.5)
					/ hang / box_size);
			if (zoom_temp1>zoom_temp2) {
				zoom_temp1=zoom_temp2;
			}
			zoom_beilv = Math.pow(1 / zoom_temp1, 1 / 4.0);
			box_size = (int) (box_size * zoom_temp1);
			seat_size = (int) (seat_size * zoom_temp1);
			OnZoomChangeListener.ZoomChange(box_size);
			isFirstLoad = false;
			zoom = 1.0;
			zoom_level = 1;
		}
		box_size = (int) (box_size * zoom);
		seat_size = (int) (seat_size * zoom);

		// 可购买座位
		seat_ok = Bitmap.createScaledBitmap(SeatOk, seat_size, seat_size, true);
		// 红色 已售座位
		seat_selled = Bitmap.createScaledBitmap(SeatSelled, seat_size,
				seat_size, true);
		// 绿色 我的选择
		seat_select = Bitmap.createScaledBitmap(SeatSelect, seat_size,
				seat_size, true);
		// 没座位 替换成透明图
		seat_null = Bitmap.createScaledBitmap(SeatNull, seat_size, seat_size,
				true);
		
		
		
		// 画座位
		for (int i = 0; i < hang; i++) {
			for (int j = 0; j < lie; j++) {
				// 我自己是根据服务器数据排座位的 这里我就随便改了 以便demo等跑起来
				canvas.drawBitmap(seat_ok, j * (box_size), i * (box_size), null);
				
				/*if (i < 7) {
					canvas.drawBitmap(seat_selled, j * (box_size), i
							* (box_size), null);
					unavaliableSeatList.add(i * lie + j);
				}*/
				
				// 过道
				if (j == 2) {
					canvas.drawBitmap(seat_null, j * (box_size),
							i * (box_size), null);
					unavaliableSeatList.add(i * lie + j);
				}
			}
		}
		// 我的座位 变成绿色
		for (int i = 0; i < mySeatList.size(); i++) {
			canvas.drawBitmap(seat_select,
					(mySeatList.get(i) % lie) * box_size,
					(mySeatList.get(i) / lie) * box_size, null);
		}
		
		seatnumber = new seatNumber();
		
		for (int j = 0; j < thisseatNumbersList.size(); j++) {
			
			
			seatnumber = thisseatNumbersList.get(j);
			
			int row;
			int col;
			Boolean isSelect = true;
			
		    row = seatnumber.getRowNumber();
		    col = seatnumber.getColumnNumber();
		    isSelect = seatnumber.getIsOptional();
		    
		    //服务器获取座位，已选变成红色
		    if (!isSelect) {
		    	unavaliableSeatList.add(toSeatListCount(row, col));
		    	canvas.drawBitmap(seat_selled, (col-1) * (box_size), (row-1) * (box_size), null);
				
			}
		   }
		
		this.setLayoutParams(new LinearLayout.LayoutParams(box_size * lie,
				box_size * hang));
		zoom = 1.0;
	}

	int mode = 0;
	float oldDist;
	float newDist;
	int oldClick;
	int newClick;
	boolean zoomType;
	int count = 0;
	float currentXPosition;
	float currentYPosition;

	public boolean onTouchEvent(MotionEvent event) {
		
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				count++;
				if (count == 1) {
					mLastTime = getClickTime(event);
				} else if (count == 2) {
					mCurTime = getClickTime(event);
					if (zoom_level == 1 && mCurTime - mLastTime < 400) {
						box_size = BOX_MAX_SIZE;
						seat_size = SEAT_MAX_SIZE;
						zoom = 1.0;
						zoom_level = 5;
						OnZoomChangeListener.ZoomChange(box_size);
						invalidate();
					}
					count = 0;
					mCurTime = 0;
					mLastTime = 0;
				}
				mode = 1;
				oldClick = getClickPoint(event);
				break;
			case MotionEvent.ACTION_UP:
				mode = 0;
				newClick = getClickPoint(event);
				if (newClick == oldClick) {
					if (mySeatList.contains(newClick)) {
						mySeatList.remove(mySeatList.indexOf(newClick));
						invalidate();
					} else if (!unavaliableSeatList.contains(newClick)) {
						mySeatList.add(newClick);
						invalidate();
					}
				}
				zoomType = false;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				mode -= 1;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				count = 0;
				mode += 1;
				zoomType = true;
				oldDist = spacing(event);// 两点按下时的距离
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode >= 2) {
					newDist = spacing(event);
					if (zoomType && newDist - oldDist > 10) {
						if (zoom_level == 4) {
							box_size = BOX_MAX_SIZE;
							seat_size = SEAT_MAX_SIZE;
							OnZoomChangeListener.ZoomChange(box_size);
							zoom = 1.0;
							zoom_level = 5;
							invalidate();
						} else if (zoom_level < 5) {
							zoom = zoom_beilv;
							OnZoomChangeListener
									.ZoomChange((int) (box_size * zoom));
							zoom_level++;
							invalidate();
						}
						zoomType = false;
					} else if (zoomType && oldDist - newDist > 10) {
						if (zoom_level > 1) {
							zoom = 1 / zoom_beilv;
							OnZoomChangeListener
									.ZoomChange((int) (box_size * zoom));
							zoom_level--;
							invalidate();
						}
						zoomType = false;
					}
				}
				break;
			}

		return true;

	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private int getClickPoint(MotionEvent event) {
		float currentXPosition = event.getX();
		float currentYPosition = event.getY();
		for (int i = 0; i < hang; i++) {
			for (int j = 0; j < lie; j++) {
				if ((j * box_size) < currentXPosition
						&& currentXPosition < j * box_size + box_size
						&& (i * box_size) < currentYPosition
						&& currentYPosition < i * box_size + box_size) {
					return i * lie + j;
				}
			}
		}
		
		
		return 0;
	}

	private long getClickTime(MotionEvent event) {
		return System.currentTimeMillis();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		WIDTH = w;
		HEIGHT = h;
	}

	public interface ZoomChangeListener {
		public void ZoomChange(int box_size);
	}

	private ZoomChangeListener OnZoomChangeListener = null;

	public void setZoomChangeListener(ZoomChangeListener listener) {
		OnZoomChangeListener = listener;
	}

	public void SetseatNumberList(ArrayList<seatNumber> seatNumbersList) {
		
		
		invalidate();
	}
	
	public int toSeatListCount(int row,int col) {
		// TODO Auto-generated method stub
		int SeatListCount = 0;
		if (row==1) {
		SeatListCount = col;
	}else if (row==2) {
		SeatListCount = col+5;
	}else if (row==3) {
		SeatListCount = col+15;
	}else if (row==4) {
		SeatListCount = col+20;
	}else if (row==5) {
		SeatListCount = col+25;
	}else if (row==6) {
		SeatListCount = col+30;
	}else if (row==7) {
		SeatListCount = col+35;
	}else if (row==8) {
		SeatListCount = col+40;
	}else if (row==9) {
		SeatListCount = col+45;
	}else if (row==10) {
		SeatListCount = col+50;
	}else if (row==11) {
		SeatListCount = col+55;
	}else if (row==12) {
		SeatListCount = col+60;
	}
		SeatListCount -=1;
      return SeatListCount;
	}
}
