import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class CustomListview extends ListView {

    private boolean TouchCheck = true;

    public CustomListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(TouchCheck == false){
            super.onInterceptTouchEvent(event);
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    public void setTouch(boolean check){
        TouchCheck = check;
    }
}
