package helpers;

import java.lang.ref.WeakReference;

/**
 * Created by harshith on 20/3/17.
 */

public class BasePresenter<T> {

    public WeakReference<T> view;


    public BasePresenter(T view)
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
