package helpers;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnImageReceivedListener} interface
 * to handle interaction events.
 */
public class ImageHelperFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    FragmentImageHelperBinding binding;
    private OnImageReceivedListener mListener;
    public static final int REQUEST_CAMERA_IMAGE = 825;
    public static final int REQUEST_CAMERA_VIDEO = 780;
    public static final int REQUEST_GALLERY_IMAGE = 122;
    public static final int REQUEST_GALLERY_VIDEO = 311;
    private String imagepath="";

    public ImageHelperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_image_helper, container, false);
        return binding.getRoot();
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnImageReceivedListener) {
            mListener = (OnImageReceivedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCameraImage.setOnClickListener(this);
        binding.btnGalleryImage.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_camera_image:
                try {
                    imagepath=Utils.takeImageFromCameraAndSave(getActivity(),null,REQUEST_CAMERA_IMAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_gallery_image:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_GALLERY_IMAGE);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_CAMERA_IMAGE:
                    mListener.onImageReceived(imagepath,REQUEST_CAMERA_IMAGE);
                    Utils.log("image path "+imagepath);
                    dismiss();
                    break;
                case REQUEST_GALLERY_IMAGE:
                    String imagepath=Utils.getFilePathFromUri(getContext(),data.getData());
                    mListener.onImageReceived(imagepath,REQUEST_GALLERY_IMAGE);
                    Utils.log("image path "+imagepath);
                    dismiss();
                    break;
            }
        }
    }

    public void showIt(FragmentManager supportFragmentManager, String s) {
        try {
            show(supportFragmentManager,s);
        }catch (Exception e)
        {
            e.printStackTrace();
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            ft.add(this, s);
            ft.commitAllowingStateLoss();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnImageReceivedListener {
        // TODO: Update argument type and name
        void onImageReceived(String uri, int request_type);
    }
}
