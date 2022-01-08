package com.example.carbonfootprinttracker.Graphs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.carbonfootprinttracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * PieChart
 * PieCHart.addPieSlice(new PieChart.PieSlice(Color, sweepAngle));
 *
 * Utku Sağocak
 */
public class PieChart extends View {
    RectF rectF;
    Paint backCirclePaint;

    float circleWidth = 40;
    List<PieSlice> circles = new ArrayList<>();
    int backCircleColor = Color.GRAY;

    Paint textPaint;
    Paint.FontMetricsInt fontMetrics;
    float textSize = 50f;
    int textColor = Color.BLACK;
    String text = "";

    private int width = 0;
    private int height = 0;


    public PieChart(Context context) {
        super(context);
        init(context,null, 0);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context,AttributeSet attrs, int defStyle) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PieChart);
        circleWidth = (int) array.getDimension(R.styleable.PieChart_backcircle_width, circleWidth);
        backCircleColor = array.getColor(R.styleable.PieChart_backcircle_color, backCircleColor);
        textColor = array.getColor(R.styleable.PieChart_text_color, textColor);
        textSize = (int) array.getDimension(R.styleable.PieChart_text_size, textSize);
        text = (String) array.getString(R.styleable.PieChart_text);


        rectF = new RectF(circleWidth, circleWidth, width - circleWidth, height - circleWidth);
        intCirclePaint();
        intTextPaint();
    }

    private void intCirclePaint() {
        backCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backCirclePaint.setStyle(Paint.Style.STROKE);
        backCirclePaint.setColor(backCircleColor);
        backCirclePaint.setStrokeWidth(circleWidth);
    }

    private void intTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = textPaint.getFontMetricsInt();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawOval(rectF, backCirclePaint);

        int currentStartAngle = -90;
        for (int i = 0; i < circles.size(); i++) {
            canvas.drawArc(rectF, currentStartAngle, circles.get(i).getSweepAngle() + 1, false, circles.get(i).getPainter());
            currentStartAngle += circles.get(i).getSweepAngle();
        }

        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(text, rectF.centerX(), baseline, textPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        rectF = new RectF(circleWidth, circleWidth, width - circleWidth, height - circleWidth);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setCircleWidth(float circleWidth) {
        rectF = new RectF(circleWidth, circleWidth, width - circleWidth, width - circleWidth);
        this.circleWidth = circleWidth;
        backCirclePaint.setStrokeWidth(circleWidth);
        for (PieSlice pie : circles) {
            pie.getPainter().setStrokeWidth(circleWidth);
        }
    }

    public void setBackCircleColor(int backCircleColor) {
        this.backCircleColor = backCircleColor;
        this.backCirclePaint.setColor(backCircleColor);
    }

    public void createPieSlices(int n) {
        PieSlice pie;
        for (int i = 0; i < n; i++) {
            pie = new PieSlice(Color.rgb(i * 40, 0, i * 40), 50);
            circles.add(pie);
        }
    }

    public void addPieSlice(PieSlice pie) {
        pie.getPainter().setStrokeWidth(circleWidth);
        circles.add(pie);
    }

    public PieSlice getPieSlice(int index) {
        return circles.get(index);
    }

    public static class PieSlice {
        Paint painter;
        float sweepAngle = 0;
        int color = Color.GRAY;

        public PieSlice(int color) {
            this.color = color;
            initPainter();
        }

        public PieSlice(int color, float sweepAngle) {
            this.color = color;
            this.sweepAngle = sweepAngle;
            initPainter();
        }

        private void initPainter() {
            this.painter = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.painter.setStyle(Paint.Style.STROKE);
            this.painter.setColor(this.color);
        }

        public float getSweepAngle() {
            return sweepAngle;
        }

        public int getColor() {
            return color;
        }

        public Paint getPainter() {
            return painter;
        }

        public void setColor(int color) {
            this.color = color;
            this.painter.setColor(this.color);
        }

        public void setSweepAngle(float sweepAngle) {
            this.sweepAngle = sweepAngle;
        }
    }
}