import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class LoadingView extends View {

    private Paint paint;
    private Path path;
    private Path tempPath;
    private int radiuWid;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAnim() {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(animation -> {
            tempPath.reset();
            float value = (float) animation.getAnimatedValue();
            float length = pathMeasure.getLength();
            float end = length * value;
            float start = (float) ((end - (0.5f - Math.abs(value - 0.5)) * length));
            pathMeasure.getSegment(start, end, tempPath, true);
            invalidate();
        });

        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);

        tempPath = new Path();

        path = new Path();
        path.addCircle(radiuWid, radiuWid, radiuWid * 0.5f, Path.Direction.CW);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wid = getMySize(300, widthMeasureSpec);
        int hei = getMySize(310, heightMeasureSpec);

        int widthFinal;
        if (wid < hei) {
            widthFinal = wid;
        } else {
            widthFinal = hei;
        }

        radiuWid = widthFinal / 2;

        setMeasuredDimension(widthFinal, widthFinal);

        initPaint();
        initAnim();
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: { //如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(tempPath, paint);
    }
}
