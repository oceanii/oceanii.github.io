public class TransformController {

    private static final String TAG = "TransformController";

    public float currX = 0;
    public float currY = 0;
    public float lastX = 0;
    public float lastY = 0;

    public float currPointDis = 0;
    public float lastPointDis = 0;

    public float currAngle = 0;
    public float lastAngle = 0;

    public float offsetX = 0.0f;
    public float offsetY = 0.0f;
    public float scaleXY = 1.0f;
    public float rotateZ = 0.0f;

    public boolean isDoubleFingerMode = false;

    public static final float MAX_OFFSET_X = 0.5f;
    public static final float MAX_OFFSET_Y = 0.5f;
    public static final float MIN_SCALE_XY = 0.5f;
    public static final float MAX_SCALE_XY = 2.0f;
    public static final float MAX_ROTATE_Z = 360.0f;

    public RectF displayRectF = null;

    public TransformController(RectF displayRectF){
        this.displayRectF = displayRectF;
    }

    public float getOffsetX(){
        return offsetX;
    }

    public float getOffsetY(){
        return offsetY;
    }

    public float getScaleXY(){
        return scaleXY;
    }

    public float getRotateZ(){
        return rotateZ;
    }

    public void onFingerTouchChanged(View v, MotionEvent event){

        Log.d(TAG, "onFingerTouchChanged: event.getAction(): " + event.getAction());

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(TAG, "onFingerTouchChanged: " + "ACTION_DOWN");
            /*currX = event.getRawX();
            currY = event.getRawY();*/

            isDoubleFingerMode = false;

            currX = event.getX();
            currY = event.getY();
            lastX = currX;
            lastY = currY;
        }else if(event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN){
            Log.d(TAG, "onFingerTouchChanged: " + "ACTION_POINTER_1_DOWN" + "ACTION_POINTER_2_DOWN");

            isDoubleFingerMode = true;

            if(event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN){
                currX = event.getX(0);
                currY = event.getY(0);
                lastX = currX;
                lastY = currY;
            }else if(event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN){
                currX = event.getX(1);
                currY = event.getY(1);
                lastX = currX;
                lastY = currY;
            }

            float disX = event.getX(1) - event.getX(0);
            float disY = event.getY(1) - event.getY(0);
            currPointDis = (float)Math.sqrt(disX * disX + disY * disY); //计算两点的距离
            lastPointDis = currPointDis;

            currAngle = (float)Math.toDegrees(Math.atan2(disY, disX));
            lastAngle = currAngle;

        } else if(event.getAction() == MotionEvent.ACTION_MOVE){

            int pointCount = event.getPointerCount();

            if(pointCount == 1 && !isDoubleFingerMode){
                currX = event.getX();
                currY = event.getY();
                offsetX += MathUtils.linearMap(currX - lastX, displayRectF.width()/2.0f, MAX_OFFSET_X);
                offsetY += MathUtils.linearMap(currY - lastY, displayRectF.height()/2.0f, MAX_OFFSET_Y);
                offsetX = MathUtils.clamp(offsetX, -MAX_OFFSET_X, MAX_OFFSET_X);
                offsetY = MathUtils.clamp(offsetY, -MAX_OFFSET_Y, MAX_OFFSET_Y);
                lastX = currX;
                lastY = currY;
            } else if(pointCount == 2 && isDoubleFingerMode){

                float disX = event.getX(1) - event.getX(0);
                float disY = event.getY(1) - event.getY(0);
                currPointDis = (float)Math.sqrt(disX * disX + disY * disY); //计算两点的距离
                scaleXY *= currPointDis / lastPointDis;
                scaleXY = MathUtils.clamp(scaleXY, MIN_SCALE_XY, MAX_SCALE_XY);
                lastPointDis = currPointDis;

                currAngle = (float)Math.toDegrees(Math.atan2(disY, disX));
                rotateZ += currAngle - lastAngle;
                rotateZ = rotateZ % MAX_ROTATE_Z;
                lastAngle = currAngle;
            }
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            isDoubleFingerMode = false;
        }else if(event.getAction() == MotionEvent.ACTION_POINTER_1_UP || event.getAction() == MotionEvent.ACTION_POINTER_2_UP){
            Log.d(TAG, "onFingerTouchChanged: " + "ACTION_POINTER_1_UP" + "ACTION_POINTER_2_UP");
            isDoubleFingerMode = false;
            if(event.getAction() == MotionEvent.ACTION_POINTER_1_UP){
                currX = event.getX(1);
                currY = event.getY(1);
                lastX = currX;
                lastY = currY;
            }else if(event.getAction() == MotionEvent.ACTION_POINTER_2_UP){
                currX = event.getX(0);
                currY = event.getY(0);
                lastX = currX;
                lastY = currY;
            }
        }else{

        }
    }

}
