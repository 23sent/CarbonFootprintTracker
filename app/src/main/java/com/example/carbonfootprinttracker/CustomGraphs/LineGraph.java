package com.example.carbonfootprinttracker.CustomGraphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * LineGraph
 * Utku Sağocak
 */
public class LineGraph extends View {
    private Paint axisPainter;
    private float strokeWidth = 10;
    private float yPadding = 15;
    private float xPadding = 50;


    private int width = 0;
    private int height = 0;

    private Paint plotLinePainter;
    private Path plotLinePath;

    private Paint gridLinePainter;
    private float xCeil = 1;
    private float yCeil = 1;
    private float xFloor = 0;
    private float yFloor = 0;

    private Paint textPaint;
    private Paint.FontMetricsInt fontMetrics;
    private int textSize = 40;

    private List<PlotPoint> plotPoints = new ArrayList<>();


    public LineGraph(Context context) {
        super(context);
        init(null, 0);
    }

    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LineGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        gridLinePainter = new Paint();
        gridLinePainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridLinePainter.setStyle(Paint.Style.STROKE);
        gridLinePainter.setColor(Color.argb(100, 0, 0, 0));
        gridLinePainter.setStrokeWidth(strokeWidth / 4);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = textPaint.getFontMetricsInt();
        yPadding += textSize;

        initPlotLinePainter();
        initAxisPainter();
    }


    private void initPlotLinePainter() {
        plotLinePainter = new Paint();
        plotLinePainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        plotLinePainter.setStyle(Paint.Style.STROKE);
        plotLinePainter.setColor(Color.BLUE);
        plotLinePainter.setStrokeWidth(strokeWidth / 2);

        plotLinePath = new Path();
    }

    private void initAxisPainter() {
        axisPainter = new Paint();
        axisPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPainter.setStyle(Paint.Style.STROKE);
        axisPainter.setColor(Color.BLACK);
        axisPainter.setStrokeWidth(strokeWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xUnit = ((float) (width - (2f * xPadding)) / (xCeil - xFloor));
        float yUnit = ((float) (height - (2f * yPadding)) / (yCeil - yFloor));

        canvas.drawLine(xPadding, yPadding, xPadding, this.height - yPadding, axisPainter);//y-axis
        canvas.drawLine(xPadding, this.height - yPadding, this.width - xPadding, this.height - yPadding, axisPainter);//x-axis


        // Paint Gird Lines
        float startX;
        for (int i = 0; i <= plotPoints.size(); i++) {
            startX = (i * xUnit) + xPadding;
            canvas.drawLine(startX, yPadding, startX, height - yPadding, gridLinePainter);
        }

//        float startY;
//        for (int i = 0; i <= yUnitCount; i++) {
//            startY = this.height - (i * yUnit) - axisPadding;
//            canvas.drawLine(axisPadding, startY, width - axisPadding, startY, gridLinePainter);
//        }

        // Draw Plot
        float originX = xPadding;
        float originY = this.height - yPadding;
        plotLinePath.moveTo(originX, originY);



        for (PlotPoint point : plotPoints) {
            float x = originX + (point.getX() * xUnit);
            plotLinePath.lineTo(x, originY - ((point.getY() * yUnit) - (yFloor*yUnit)));
            canvas.drawCircle(x, originY - ((point.getY() * yUnit) - (yFloor*yUnit)), 5, plotLinePainter);
            canvas.drawText(point.getLabel(),  x, this.height - fontMetrics.bottom, textPaint);
        }
        canvas.drawPath(plotLinePath, plotLinePainter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }


    public void setPlotPoints(List<PlotPoint> plotPoints) {
        this.plotPoints = plotPoints;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        for (PlotPoint point : plotPoints) {
            if (point.getX() > maxX) {
                maxX = point.getX();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
            if (point.getX() < minX) {
                minX = point.getX();
            }
            if (point.getY() < minY) {
                minY = point.getY();
            }
        }
        if (Float.isInfinite(maxX)) maxX = 1;
        if (Float.isInfinite(maxY)) maxY = 1;
        if (Float.isInfinite(minX)) minX = 0;
        if (Float.isInfinite(minY)) minY = 0;

        xCeil = maxX;
        yCeil = maxY;
        xFloor = minX;
        yFloor = minY;
    }

    public static class PlotPoint {
        private float x;
        private float y;
        private String label;

        public PlotPoint(float x, float y, String label) {
            this.x = x;
            this.y = y;
            this.label = label;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public String getLabel() {
            return label;
        }
    }

}