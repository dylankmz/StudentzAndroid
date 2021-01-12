package be.kmz.studentz.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import be.kmz.studentz.R;

public class InfoDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning: certain values cannot be changed!");
        builder.setMessage("You cannot change the gender, education and classroom of a student after registering the student," +
                " to do this please remove the student and create a new student!");
        builder.setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }
}
