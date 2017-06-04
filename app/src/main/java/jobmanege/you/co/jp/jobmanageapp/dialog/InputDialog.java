package jobmanege.you.co.jp.jobmanageapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import jobmanege.you.co.jp.jobmanageapp.R;

public class InputDialog extends DialogFragment implements View.OnClickListener {
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static InputDialog newInstance(Bundle bundle) {
        InputDialog instance = new InputDialog();
        instance.setArguments(bundle);

        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // Viewの設定
        LayoutInflater i = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = i.inflate(R.layout.fragment_input_dialog, null);

        // タブ設定
        TabHost tabs = (TabHost) content.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec tab1 = tabs.newTabSpec(getString(R.string.dialog_tab_site));
        tab1.setContent(R.id.tab1);
        tab1.setIndicator(getString(R.string.dialog_tab_site));
        TabHost.TabSpec tab2 = tabs.newTabSpec(getString(R.string.dialog_tab_office));
        tab2.setContent(R.id.tab2);
        tab2.setIndicator(getString(R.string.dialog_tab_office));
        tabs.addTab(tab1);
        tabs.addTab(tab2);
        // Dialogの設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(content);
        builder.setPositiveButton(R.string.btn_register, null);
        builder.setNegativeButton(R.string.btn_cancel, null);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        return super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_input_dialog, null);
////        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel);
////        cancelBtn.setOnClickListener(this);
////        InputDialog dialog = InputDialog.newInstance(null);
//        return view;
//    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
//        switch(id){
//            case R.id.btn_cancel:
//                dialog.dismiss();
//                break;
//        }
    }
}
