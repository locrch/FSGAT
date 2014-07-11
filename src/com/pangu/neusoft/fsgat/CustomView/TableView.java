package com.pangu.neusoft.fsgat.CustomView;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TableView extends ViewGroup{
    private static final int STARTX = 0;// 起始X坐标
    private static final int STARTY = 0;// 起始Y坐标
    private static final int BORDER = 2;// 表格边框宽度
       
    private int mRow;// 行数
    private int mCol;// 列数
    private int mX;
    private int mY;
    private int mText;
    TextView view;
    
    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRow = 13;// 默认行数
        this.mCol = 6;// 默认列数
        // 添加子控件
        this.addOtherView(context);
    }
       
    public TableView(Context context, int row,int col) {
        super(context);
        if(row>20 || col>20){
            this.mRow = 20;// 大于20行时，设置行数为20
            this.mCol = 20;// 大于20列时，设置列数为20
        }else if(row==0 || col==0){
            this.mRow = 3;
            this.mCol = 3;
        }
        else{
            this.mRow = row;
            this.mCol = col;
        }
        // 添加子控件
        this.addOtherView(context);
    }
    
    public void SetText(Context context,int X,int Y,int text){
    	setmX(X+1);
    	setmY(Y+1);
    	mText = text;
    	
    	for(int i=1;i<=mRow;i++){
            for(int j=1;j<=mCol;j++){
            	if (i == X+1&&j==Y+1) {
            		TextView view = new TextView(context);
                    view.setText(String.valueOf(text));
                    this.addView(view);
                    
				}
            	
            	
            }
            }
    	this.addOtherView(context);
    	
    	
    }
       
    
    
    
    
    public void addOtherView(Context context){
        int value = 1;
        int k = 0;
        for(int i=1;i<=mRow;i++){
            for(int j=1;j<=mCol;j++){
            	
            	view = new TextView(context);
                //view.setText(String.valueOf(value++));
                view.setGravity(Gravity.CENTER);
              
                if (i == getmX()&& j==getmY()) {
                	view.setText(String.valueOf("22"));
            	}
                
                if (i<=8) {
                	view.setBackgroundColor(Color.rgb(252, 0, 0));
                    
				}else {
					view.setBackgroundColor(Color.rgb(0, 127, 0));
                    
				}
                
                
                if (j == 4) {
            		view.setBackgroundColor(Color.rgb(252, 252, 252));
            		view.setText("");
    			}
                
                if (j == 1) {
                	view.setText(String.valueOf(k)+"排");
                	view.setBackgroundColor(Color.rgb(252, 252, 252));
                	if (j ==1&& i== 1) {
                		view.setText("");
					}
    			}
                
                if (i == 1) {
    				if (j == 2) {
    					view.setText("靠窗位");
    				}
    				if (j == 3) {
    					view.setText("中间位");
    				}
    				if (j == 4) {
    					view.setText("通道");
    				}
    				if (j == 5) {
    					view.setText("中间位");
    				}
    				if (j == 6) {
    					view.setText("靠窗位");
    				}
    				view.setBackgroundColor(Color.rgb(252, 252, 252));
    			}
                
                
                this.addView(view);
            }
            
            k++;
        }
        
        
    }
       
    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(BORDER);
        paint.setColor(Color.rgb(79, 129, 189));
        paint.setStyle(Style.STROKE);
        // 绘制外部边框
        canvas.drawRect(STARTX, STARTY, getWidth()-STARTX, getHeight()-STARTY, paint);
        // 画列分割线
        for(int i=1;i<mCol;i++){
            canvas.drawLine((getWidth()/mCol)*i, STARTY, (getWidth()/mCol)*i, getHeight()-STARTY, paint);
        }
        // 画行分割线
        for(int j=1;j<mRow;j++){
            canvas.drawLine(STARTX, (getHeight()/mRow)*j, getWidth()-STARTX, (getHeight()/mRow)*j, paint);
        }
        super.dispatchDraw(canvas);
    }
       
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = STARTX+BORDER;
        int y = STARTY+BORDER;
        int i = 0;
        int count = getChildCount();
        for(int j=0; j<count; j++){
            View child = getChildAt(j);
            child.layout(x, y, x+getWidth()/mCol-BORDER*2, y+getHeight()/mRow-BORDER*2);
            if(i >=(mCol-1)){
                i = 0;
                x = STARTX+BORDER;
                y += getHeight()/mRow;
            }else{
                i++;
                x += getWidth()/mCol;
            }
        }
    }
       
    public void setRow(int row){
        this.mRow = row;
    }
       
    public void setCol(int col){
        this.mCol = col;
    }

	public int getmX() {
		return mX;
	}

	public void setmX(int mX) {
		this.mX = mX;
	}

	public int getmY() {
		return mY;
	}

	public void setmY(int mY) {
		this.mY = mY;
	}
   
}
