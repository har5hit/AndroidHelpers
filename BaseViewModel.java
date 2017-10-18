package helpers;

import android.arch.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

/**
 * Created by harshith on 16/10/17.
 */

public class BaseViewModel<T> extends ViewModel{
    public WeakReference<T> view;


    public void setView(T view)
    {
        this.view=new WeakReference<T>(view);
    }


    public boolean isViewAttached()
    {
        return view.get()!=null;
    }

    public T getView()
    {
        return view.get();
    }

    public void detachView()
    {
        view=null;
    }
}
