package hk.ed.android.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;

public class DontPressWithParentCheckedTextView extends CheckedTextView {
	public DontPressWithParentCheckedTextView(Context context) {
        super(context);
    }

    public DontPressWithParentCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DontPressWithParentCheckedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setPressed(boolean pressed) {
        if (pressed && getParent() instanceof View && ((View) getParent()).isPressed()) {
            return;
        }
        super.setPressed(pressed);
    }
}
