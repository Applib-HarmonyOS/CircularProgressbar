package com.jackandphantom.circularprogressbar;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.Component.DrawTask;
import ohos.agp.components.Component.EstimateSizeListener;
import ohos.agp.components.Component.TouchEventListener;
import ohos.agp.render.Arc;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;
import com.hmos.compat.utils.AttrUtils;

/**
 * CircleProgressbar.
 */
public class CircleProgressbar extends Component implements DrawTask, EstimateSizeListener, TouchEventListener {
    /* InnerCircle  is used to draw reference surface on which progress circle is drawn and moved. */
    private Paint innerCircle = new Paint();
    /* OuterCircle is used for progress circle which progress your status. */
    private Paint outerCircle = new Paint();
    /* Width and height is used to get view's width and height. */
    private int width;
    private int height;
    /**
     * These two values are used as default value for foreground (progress) circle
     * and other is used for background circle width how much width will both circle's.
     */
    private static final int DEFAULT_FOREGROUND_PROGRESS_WIDTH = 10;
    private static final int DEFAULT_BACKGROUND_CIRCLE_WIDTH = 10;
    /* rectF is used for make progress circle which is basically a arc so it's contain the bounds of arc. */
    private RectFloat rectF = new RectFloat();
    /* This boolean value is for touch event is move. */
    private boolean moveCorrect;
    /* BackgroundProgressWidth is used for background progress width. */
    private int backgroundProgressWidth;
    /* foregroundProgressWidth is used for foreground progress width. */
    private int foregroundProgressWidth;
    /* backgroundProgressColor is used for background progress color. */
    private int backgroundProgressColor;
    /* This is the default value of background progress color. */
    private static final int DEFAULT_BACKGROUND_PROGRESS_COLOR = ohos.agp.utils.Color.GRAY.getValue();
    /**
     * These two variables are used for foreground progress color if no color is specified
     * by the user then for default we use these variables.
     */
    private int foregroundProgressColor;
    private static final int DEFAULT_FOREGROUND_PROGRESS_COLOR = ohos.agp.utils.Color.BLACK.getValue();
    /* Progress is used for progress in progressbar. */
    private float progress = 0;
    /* start angle is used where the progress should start so according to android it will -90. */
    private int startAngle = -90;
    /* sweep angle is used for progress angle in progress bar. */
    private float sweepAngle = 0;
    /**
     * max progress as name implies this is used for finding the max value for progress
     *  default it's value is 100.
     */
    private float maxProgress = 100;
    /* centerPoint is used for finding the smallest value between width and height of view. */
    private int centerPoint;
    /* subtractingValues is used to find which one is bigger among the background circle and foreground circle. */
    private int subtractingValue;
    /* As the name says this will used for checking that progress start from clockwise or counter clockwise. */
    private boolean clockWise;
    private int drawRadius;
    private int drawOuterRadius;
    /* This name says that progress bar should be touchable or not. */
    private boolean isTouchEnabled = false;
    private boolean roundedCorner;
    private OnProgressbarChangeListener onProgressbarChangeListener;

    /**
     * This is CircleProgressbar constructor.
     *
     * @param context Context
     */
    public CircleProgressbar(Context context) {
        super(context);
        init();
        addDrawTask(this);
        setEstimateSizeListener(this);
        setTouchEventListener(this);
    }

    /**
     * This is CircleProgressbar constructor.
     *
     * @param context Context
     * @param attrs AttrSet
     */
    public CircleProgressbar(Context context, AttrSet attrs) {
        this(context, attrs, 0);
        addDrawTask(this);
        setEstimateSizeListener(this);
        setTouchEventListener(this);
    }

    /**
     * TypedArray used to getting all the values from xml (user).
     *
     * @param context Context
     * @param attrs AttrSet
     * @param i integer
     */
    public CircleProgressbar(Context context, AttrSet attrs, int i) {
        super(context, attrs, i);
        backgroundProgressWidth = AttrUtils.getIntFromAttr(attrs, "cpb_backgroundProgressWidth",
                DEFAULT_BACKGROUND_CIRCLE_WIDTH);
        foregroundProgressWidth = AttrUtils.getIntFromAttr(attrs, "cpb_foregroundProgressWidth",
                DEFAULT_FOREGROUND_PROGRESS_WIDTH);
        backgroundProgressColor = AttrUtils.getColorFromAttr(attrs, "cpb_backgroundProgressColor",
                DEFAULT_BACKGROUND_PROGRESS_COLOR);
        foregroundProgressColor = AttrUtils.getColorFromAttr(attrs, "cpb_foregroundProgressColor",
                DEFAULT_FOREGROUND_PROGRESS_COLOR);
        this.progress = AttrUtils.getFloatFromAttr(attrs, "cpb_progress", progress);
        this.roundedCorner = AttrUtils.getBooleanFromAttr(attrs, "cpb_roundedCorner", false);
        this.clockWise = AttrUtils.getBooleanFromAttr(attrs, "cpb_clockwise", false);
        this.isTouchEnabled = AttrUtils.getBooleanFromAttr(attrs, "cpb_touchEnabled", false);
        init();
        if (roundedCorner) {
            setRoundedCorner(roundedCorner);
        }
        if (this.progress > 0) {
            setProgress(this.progress);
        }
        if (clockWise) {
            setClockwise(clockWise);
        }
        if (isTouchEnabled) {
            enabledTouch(isTouchEnabled);
        }
        addDrawTask(this);
        setEstimateSizeListener(this);
        setTouchEventListener(this);
    }

    /* Initialize paint object for drawing shapes  */
    private void init() {
        innerCircle.setStrokeWidth(foregroundProgressWidth);
        innerCircle.setAntiAlias(true);
        innerCircle.setStyle(ohos.agp.render.Paint.Style.STROKE_STYLE);
        Color hmosColor = CircleProgressbar.changeParamToColor(foregroundProgressColor);
        innerCircle.setColor(hmosColor);
        outerCircle.setStrokeWidth(backgroundProgressWidth);
        outerCircle.setAntiAlias(true);
        Color hmosColor1 = CircleProgressbar.changeParamToColor(backgroundProgressColor);
        outerCircle.setColor(hmosColor1);
        outerCircle.setStyle(ohos.agp.render.Paint.Style.STROKE_STYLE);
    }

    /**
     * Here we draw two things, first circle you can say this is surface on which arc means progress will drawn.
     *
     * @param component Component
     * @param canvas Canvas
     */
    @Override
    public void onDraw(Component component, ohos.agp.render.Canvas canvas) {
        onEstimateSize(component.getEstimatedWidth(), component.getEstimatedHeight());
        canvas.drawCircle(centerPoint, centerPoint, drawRadius, outerCircle);
        Arc arc = CircleProgressbar.changeParamToArc(startAngle, sweepAngle, false);
        canvas.drawArc(rectF, arc, innerCircle);
    }

    /**
     * This is callback method which is used to find how much size will be assigned to this child view.
     *
     * @param widthMeasureSpec integer
     * @param heightMeasureSpec integer
     * @return false
     */
    @Override
    public boolean onEstimateSize(int widthMeasureSpec, int heightMeasureSpec) {
        width = getEstimatedWidth();
        height = getEstimatedHeight();
        centerPoint = Math.min(width, height);
        int min = Math.min(width, height);
        setEstimatedSize(min, min);
        setRadiusRect();
        return false;
    }

    /* Getting the bounds of both background and foreground circle. */
    private void setRadiusRect() {
        centerPoint = Math.min(width, height) / 2;
        subtractingValue = (backgroundProgressWidth > foregroundProgressWidth)
                ? backgroundProgressWidth : foregroundProgressWidth;
        int newSeekWidth = subtractingValue / 2;
        drawRadius = Math.min((width - subtractingValue) / 2, (height - subtractingValue) / 2);
        drawOuterRadius = Math.min((width - newSeekWidth), (height - newSeekWidth));
        rectF.modify(subtractingValue / 2, subtractingValue / 2, drawOuterRadius, drawOuterRadius);
    }

    /**
     * This touchEvent callback is active when user needs this for getting the touch event in his app so
     * he needs to make touch event true.
     *
     * @param component Component
     * @param event Touchevent
     * @return false
     */
    @Override
    public boolean onTouchEvent(Component component, ohos.multimodalinput.event.TouchEvent event) {
        MmiPoint point = event.getPointerPosition(0);
        if (isTouchEnabled) {
            switch (event.getAction()) {
                case TouchEvent.PRIMARY_POINT_DOWN:
                    if (onProgressbarChangeListener != null) {
                        onProgressbarChangeListener.onStartTracking(this);
                    }
                    checkForCorrect(point.getX(), point.getY());
                    break;
                case TouchEvent.POINT_MOVE:
                    if (moveCorrect) {
                        justMove(point.getX(), point.getY());
                    }
                    upgradeProgress(this.progress, true);
                    break;
                case TouchEvent.PRIMARY_POINT_UP:
                    if (onProgressbarChangeListener != null) {
                        onProgressbarChangeListener.onStopTracking(this);
                    }
                    moveCorrect = false;
                    break;
                default:
                    //do nothing
            }
            return true;
        }
        return false;
    }

    /**
     * Update progress is used when setProgress is used, so first this method updates the value of progress and then
     * update the value of sweepangle and according to clockwise it also decides
     * that sweep angle to be positive or negative.
     *
     * @param progress float
     * @param b boolean
     */
    private void upgradeProgress(float progress, boolean b) {
        this.progress = (progress <= maxProgress) ? progress : maxProgress;
        sweepAngle = (360 * progress / maxProgress);
        if (this.clockWise && sweepAngle > 0) {
            sweepAngle = -sweepAngle;
        }
        if (onProgressbarChangeListener != null) {
            onProgressbarChangeListener.onProgressChanged(this, progress, b);
        }
        invalidate();
    }

    /**
     * When the user make touch event true then this method will be called and it's work is to increase and decrease the
     * sweep value so it's directly increases and decreases the values of progress bar.
     *
     * @param x float
     * @param y float
     */
    private void justMove(float x, float y) {
        if (clockWise) {
            float degree = (float) Math.toDegrees(Math.atan2(x - centerPoint, centerPoint - y));
            if (degree > 0) {
                degree -= 360;
            }
            sweepAngle = degree;
        } else {
            float degree = (float) Math.toDegrees(Math.atan2(x - centerPoint, centerPoint - y));
            if (degree < 0) {
                degree += 360;
            }
            sweepAngle = degree;
        }
        progress = (sweepAngle * maxProgress / 360);
        invalidate();
    }

    /* This method is also used by touch event so it's just find that user click on circle or not */
    private void checkForCorrect(float x, float y) {
        float distance = (float) Math.sqrt(Math.pow((x - centerPoint), 2) + Math.pow((y - centerPoint), 2));
        if (distance < drawOuterRadius / 2 + subtractingValue && distance
                > drawOuterRadius / 2 - subtractingValue * 2) {
            moveCorrect = true;
            justMove(x, y);
        }
    }

    /**
     * This method is used to set the OnProgressbarChangeListener.
     *
     * @param onProgressbarChangeListener OnProgressbarChangeListener
     */
    public void setOnProgressbarChangeListener(OnProgressbarChangeListener onProgressbarChangeListener) {
        this.onProgressbarChangeListener = onProgressbarChangeListener;
    }

    /**
     * This is interface for informing about the progress.
     */
    public interface OnProgressbarChangeListener {
        void onProgressChanged(CircleProgressbar circleSeekbar, float progress, boolean fromUser);

        void onStartTracking(CircleProgressbar circleSeekbar);

        void onStopTracking(CircleProgressbar circleSeekbar);
    }

    /**
     * This setter method is used to set the boolean value clockwise.
     *
     * @param clockwise boolean
     */
    public void setClockwise(boolean clockwise) {
        this.clockWise = clockwise;
        if (this.clockWise && sweepAngle > 0) {
            sweepAngle = -sweepAngle;
        }
        invalidate();
    }

    public boolean isClockWise() {
        return this.clockWise;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    public float getMaxProgress() {
        return this.maxProgress;
    }

    /**
     * This method is used to set the background progress width.
     *
     * @param width integer
     */
    public void setBackgroundProgressWidth(int width) {
        this.backgroundProgressWidth = width;
        outerCircle.setStrokeWidth(backgroundProgressWidth);
        postLayout();
        invalidate();
    }

    public int getBackgroundProgressWidth() {
        return this.backgroundProgressWidth;
    }

    /**
     * This method is used to set the foreground progress width.
     *
     * @param width integer
     */
    public void setForegroundProgressWidth(int width) {
        this.foregroundProgressWidth = width;
        innerCircle.setStrokeWidth(foregroundProgressWidth);
        postLayout();
        invalidate();
    }

    public int getForegroundProgressWidth() {
        return this.foregroundProgressWidth;
    }

    /**
     * This method is used to set the background progress color.
     *
     * @param color integer
     */
    public void setBackgroundProgressColor(int color) {
        this.backgroundProgressColor = color;
        Color hmosColor = CircleProgressbar.changeParamToColor(color);
        outerCircle.setColor(hmosColor);
        postLayout();
        invalidate();
    }

    public int getBackgroundProgressColor() {
        return this.backgroundProgressColor;
    }

    /**
     * This method is used to set the foreground progress color.
     *
     * @param color integer
     */
    public void setForegroundProgressColor(int color) {
        this.foregroundProgressColor = color;
        Color hmosColor = CircleProgressbar.changeParamToColor(color);
        innerCircle.setColor(hmosColor);
        postLayout();
        invalidate();
    }

    public int getForegroundProgressColor() {
        return this.foregroundProgressColor;
    }

    public float getProgress() {
        return progress;
    }

    /**
     * This method is used to set progress.
     *
     * @param progress float
     */
    public void setProgress(float progress) {
        upgradeProgress(progress, false);
    }

    /**
     * This method is used to enable touch.
     *
     * @param enabled boolean
     */
    public void enabledTouch(boolean enabled) {
        this.isTouchEnabled = enabled;
        invalidate();
    }

    public boolean isTouchEnabled() {
        return this.isTouchEnabled;
    }

    /**
     * This method is used to set the round corner.
     *
     * @param roundedCorner boolean
     */
    public void setRoundedCorner(boolean roundedCorner) {
        if (roundedCorner) {
            innerCircle.setStrokeCap(ohos.agp.render.Paint.StrokeCap.ROUND_CAP);
            outerCircle.setStrokeCap(ohos.agp.render.Paint.StrokeCap.ROUND_CAP);
        } else {
            innerCircle.setStrokeCap(ohos.agp.render.Paint.StrokeCap.SQUARE_CAP);
            outerCircle.setStrokeCap(ohos.agp.render.Paint.StrokeCap.SQUARE_CAP);
        }
        invalidate();
    }

    public boolean isRoundedCorner() {
        return this.roundedCorner;
    }

    /**
     * This method is used to change parameter to color.
     *
     * @param color integer
     * @return new Color(color)
     */
    public static Color changeParamToColor(int color) {
        return new Color(color);
    }

    /**
     * This method is used to change parameter to arc.
     *
     * @param startAngle float
     * @param sweepAngle float
     * @param useCenter boolean
     * @return new Arc(startAngle, sweepAngle, useCenter)
     */
    public static Arc changeParamToArc(float startAngle, float sweepAngle, boolean useCenter) {
        return new Arc(startAngle, sweepAngle, useCenter);
    }
}
