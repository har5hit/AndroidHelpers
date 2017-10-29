package customui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.ideatransport.ideacabs.partner.R;

import helpers.Utils;

/**
 * Created by harshit on 5/5/17.
 */

public class CarouselViewPager extends ViewPager {


    private float mRatio = 1f;
    private int mPadding = 0;
    private int mPageMargin = 0;

    public CarouselViewPager(Context context) {
        super(context);
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.CarouselViewPager);
            mRatio = styled.getFloat(R.styleable.CarouselViewPager_ratio, 1f);
            mPadding = styled.getInt(R.styleable.CarouselViewPager_padding, 0);
            mPageMargin = styled.getInt(R.styleable.CarouselViewPager_page_margin, 0);

            Utils.log("carousel"+mRatio+"/"+mPadding+"/"+mPageMargin);
            styled.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int cwidth = MeasureSpec.getSize(widthMeasureSpec-128);
        int height = (int) (cwidth /mRatio);
        setMeasuredDimension(width, height);
        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        initSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initSize() {
        setPadding(mPadding,0,mPadding,0);
        setClipToPadding(false);
        setPageMargin(mPageMargin);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position>0) {
                    getChildAt(position - 1).animate().scaleY(0.8f);
                }

                getChildAt(position).clearAnimation();
                getChildAt(position).setScaleY(1f);

                try{
                    getChildAt(position + 1).animate().scaleY(0.8f);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
