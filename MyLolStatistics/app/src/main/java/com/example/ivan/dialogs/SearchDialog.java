package com.example.ivan.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivan.database.DatabaseStuff;
import com.example.ivan.mylolstatistics.R;

public class SearchDialog extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(String champion);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

            mListener = (NoticeDialogListener) getTargetFragment();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.search_dialog, null);
        final EditText champion = (EditText) v.findViewById(R.id.etChampionName);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        final DatabaseStuff db = new DatabaseStuff(getActivity());


        builder.setMessage(R.string.champion_name)
                .setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(!db.ifChampionExists(champion.getText().toString())){
                            champion.setError("No such champion entered yet");
                            Toast.makeText(getActivity(), "No such champion entered yet", Toast.LENGTH_SHORT).show();
                        }else{
                            mListener.onDialogPositiveClick(champion.getText().toString());
                        }

                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }


}
