package helpers;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by harshith on 27/9/17.
 */

public class Font {

    public static void setAllTextView(ViewGroup parent) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            final View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                setAllTextView((ViewGroup) child);
            } else if (child instanceof TextView) {
                TextView tchild= (TextView) child;
                boolean bold=false;
                try {
                    bold=tchild.getTypeface().isBold();
                    Utils.log("it is bold"+bold);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                tchild.setTypeface(getFont(bold)) ;
            }
        }
    }

    public static void setAllMenuItems(Menu m)
    {
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private static void applyFontToMenuItem(MenuItem mi) {
        Typeface font = getFont(false);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public static Typeface getFont(boolean bold) {
        if (bold)
        {
            return Typeface.createFromAsset(MyApplication.getContext().getAssets(), "<font-name-bold>.ttf");

        }
        return Typeface.createFromAsset(MyApplication.getContext().getAssets(), "<font-name>.ttf");
    }
}
