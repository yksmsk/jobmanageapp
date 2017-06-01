package jobmanege.you.co.jp.jobmanageapp.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InputDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static InputDialogFragment newInstance(Bundle bundle){
        InputDialogFragment instance = new InputDialogFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle){
        Dialog dialog = super.onCreateDialog(bundle);
        dialog.setCancelable(false);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
//        view = inflater.inflate(R.layout.activity_reservation, container, false);
//        dialog = InputDialogFragment.newInstance(null);
//        webView = (WebView) view.findViewById(R.id.webView);

    }
}
