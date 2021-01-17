package be.kmz.studentz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import be.kmz.studentz.R;
import be.kmz.studentz.model.StudentViewModel;

public class GenderDialog extends DialogFragment {

    private FragmentActivity mContext;
    private int selectedIndex;

    //positie van de geselectioneerde element
    private DialogInterface.OnClickListener selectionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            selectedIndex = which;
        }
    };

    //mijn gender array opvragen en koppelen met selectedIndex
    public DialogInterface.OnClickListener genderListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String gender = getResources().getStringArray(R.array.gender)[selectedIndex];
            StudentViewModel model = new ViewModelProvider(mContext).get(StudentViewModel.class);
            model.pickGender(gender);
        }
    };

    //wordt opgeroepen wanneer de fragment toegevoegd is aan de fragmentmanager en
    //gekoppeld aan zijn hostactivity, fragment is actief
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    //ik bouw mijn dialoog op met builder, ik geef de bovenstaande listeners mee aan
    //de juiste buttons
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.str_pick_gender);
        builder.setSingleChoiceItems(R.array.gender, selectedIndex, selectionListener);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, genderListener);
        return builder.create();
    }
}
