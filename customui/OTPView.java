package com.justadeveloper96.customviews;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import helpers.Utils;

/**
 * Created by Harshith on 26-10-2017.
 */

public class OTPView extends LinearLayout implements TextWatcher {
    int mDigits;
    int digit_margin_left;
    int digit_margin_right;
    int digit_margin_top;
    int digit_margin_bottom;

    int digit_padding_left;
    int digit_padding_right;
    int digit_padding_top;
    int digit_padding_bottom;
    EditText[] mViews;

    ValidityListener listener;

    @DrawableRes int errorResource;

    public void setErrorResource(@DrawableRes int errorResource) {
        this.errorResource = errorResource;
    }

    public void setListener(ValidityListener listener) {
        this.listener = listener;
    }

    public OTPView(Context context, int digits) {
        super(context);
        init(digits);
    }

    public OTPView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }


    public OTPView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OTPView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(int digits) {
        mDigits=digits;
        init();
    }
    private void init(AttributeSet attrs) {
        TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.OTPView);
        mDigits = styled.getInt(R.styleable.OTPView_digits, 4);
        digit_margin_top = (int) styled.getDimension(R.styleable.OTPView_margin, -1);
        if (digit_margin_top==-1)
        {
            digit_margin_top = (int) styled.getDimension(R.styleable.OTPView_margin_top, 0);
            digit_margin_bottom = (int) styled.getDimension(R.styleable.OTPView_margin_bottom, 0);
            digit_margin_left = (int) styled.getDimension(R.styleable.OTPView_margin_left, 0);
            digit_margin_right = (int) styled.getDimension(R.styleable.OTPView_margin_right, 0);
        }else 
        {
            digit_margin_left=digit_margin_right=digit_margin_bottom=digit_margin_top;
        }

        digit_padding_top = (int) styled.getDimension(R.styleable.OTPView_padding, -1);
        if (digit_padding_top==-1)
        {
            digit_padding_top = (int) styled.getDimension(R.styleable.OTPView_padding_top, 0);
            digit_padding_bottom = (int) styled.getDimension(R.styleable.OTPView_padding_bottom, 0);
            digit_padding_left = (int) styled.getDimension(R.styleable.OTPView_padding_left, 0);
            digit_padding_right = (int) styled.getDimension(R.styleable.OTPView_padding_right, 0);
        }else
        {
            digit_padding_left=digit_padding_right=digit_padding_bottom=digit_padding_top;
        }
        styled.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init()
    {
        LayoutParams params=new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(digit_margin_left,digit_margin_top,digit_margin_right,digit_margin_bottom);
        mViews=new EditText[mDigits];
        for (int i = 0; i < mDigits; i++) {
            EditText digit=new EditText(getContext());
            digit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
            digit.setRawInputType(InputType.TYPE_CLASS_NUMBER);
            digit.setPadding(digit.getPaddingLeft()+digit_padding_left,
                    digit.getPaddingTop()+digit_padding_top,
                    digit.getPaddingRight()+digit_padding_right,
                    digit.getPaddingBottom()+digit_padding_bottom);
            digit.addTextChangedListener(this);
            digit.setLayoutParams(params);
            mViews[i]=digit;
            addView(digit);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(editable.toString().isEmpty())
        {
            for (int i = mDigits-1; i >=0 ; i--) {
                if (!Utils.getText(mViews[i]).isEmpty())
                {
                    mViews[i].requestFocus();
                    mViews[i].setSelection(mViews[i].length());
                    break;
                }
            }
            listener.onValidityChange(false);
        }else
        {
            checkValidity();
        }
    }

    private void checkValidity() {
        boolean valid=true;
        for (int i = 0; i < mDigits; i++) {
            if (Utils.getText(mViews[i]).isEmpty())
            {
                valid=false;
                mViews[i].requestFocus();
                break;
            }
        }
        listener.onValidityChange(valid);
    }


    public interface ValidityListener{
        void onValidityChange(boolean valid);
    }

    public void enableErrorMode()
    {
        if (errorResource!=0)
        {

            for (int i = 0; i <mDigits ; i++) {
                mViews[i].setBackgroundResource(errorResource);
            }
        }

        ObjectAnimator shake_anim1 = ObjectAnimator.ofFloat( this, "translationX", 0, 25,-25,15,-15,0);
        shake_anim1.start();
    }


    public String extractOTP()
    {
        StringBuilder otp=new StringBuilder();
        for (int i = 0; i <mDigits ; i++) {
            otp.append(Utils.getText(mViews[i]));
        }
        return otp.toString();
    }

    public void setOTP(String otp)
    {
        int min=Math.min(mDigits,otp.length());
        for (int i = 0; i < min ; i++) {
            (mViews[i]).setText(String.valueOf(otp.charAt(i)));
        }
        checkValidity();
    }
}
