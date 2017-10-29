package customui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.ideatransport.ideacabs.partner.R;

import helpers.Utils;

/**
 * Created by harshit on 17/5/17.
 */

public class RatioImageView extends android.support.v7.widget.AppCompatImageView {

    private float mRatio = 1f;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            mRatio = styled.getFloat(R.styleable.RatioImageView_ratio, 1f);
            Utils.log("ratio is"+mRatio);
            styled.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * mRatio);
        setMeasuredDimension(width, height);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }
}
