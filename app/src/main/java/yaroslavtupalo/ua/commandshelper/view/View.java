package yaroslavtupalo.ua.commandshelper.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import yaroslavtupalo.ua.commandshelper.R;

/**
 * Created by Yaroslav on 03/01/2016.
 */
public class View extends TextView {

    private Paint marginPaint;
    private Paint lainPaint;
    private int paperColor;
    private float margin;

    public View(Context context, AttributeSet as, int ds){
       super(context, as, ds);
        init();
    }

    public View(Context context, AttributeSet as){
        super(context, as);
        init();
    }

    public View(Context context){
        super(context);
        init();
    }

    private void init(){

        // �������� ������ �� ������� ��������.
        Resources myResources = getResources();
        // �������� ����� ��� ���������, ������� �� ����� ������������ � ������ onDraw.
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(myResources.getColor(R.color.notepad_margin));
        lainPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        lainPaint.setColor(myResources.getColor(R.color.line_color));
        // �������� ���� ���� ��� ����� � ������ ������.
        paperColor = myResources.getColor(R.color.paper_color);
        margin = myResources.getDimension(R.dimen.notepad_margin);
    }

    @Override
    public void onDraw(Canvas canvas){

        // ������� ���� ��� �����.
        canvas.drawColor(paperColor);
        // ��������� ������������ �����
        canvas.drawLine(0,0,getMeasuredHeight(),0,lainPaint);
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), lainPaint);
        // ��������� ������
        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
        // ����������� ����� � ������� �� ������,
        canvas.save();
        canvas.translate(margin, 0);
        // ����������� TextView ��� ������ ������,
        super.onDraw(canvas);
        canvas.restore();
    }
}
