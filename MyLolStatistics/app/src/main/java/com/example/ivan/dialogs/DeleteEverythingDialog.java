package com.example.ivan.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ivan.database.DatabaseStuff;
import com.example.ivan.mylolstatistics.DisplayStatistics;
import com.example.ivan.mylolstatistics.R;

public class DeleteEverythingDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_everything)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        db.deleteEverything();
                        Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();

                        DisplayStatistics newFragment = new DisplayStatistics();
                        Bundle args = new Bundle();
                        newFragment.setArguments(args);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        if (getActivity().findViewById(R.id.selected_option_fragment) != null) {

                            transaction.replace(R.id.selected_option_fragment, newFragment);
                            transaction.addToBackStack(DisplayStatistics.class.getName());
                            transaction.commit();

                        } else {

                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(DisplayStatistics.class.getName());
                            transaction.commit();

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
