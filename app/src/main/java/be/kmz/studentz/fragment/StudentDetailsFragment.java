package be.kmz.studentz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import be.kmz.studentz.R;
import be.kmz.studentz.model.Student;

public class StudentDetailsFragment extends Fragment {

    private EditText edFirstName, edLastName, edBirthDate, edEmail, edAddress, edLocation, edZip;
    private TextView tvGender, tvEducation, tvClassroom;
    private Button btnGender, btnEducation, btnClassroom;
    private Student selectedStudent;
    private FragmentActivity mContext;

    public StudentDetailsFragment() {
    }

    @NonNull
    public static  StudentDetailsFragment newInstance() {
        return new StudentDetailsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_details, container, false);

        selectedStudent = (Student) getArguments().getSerializable("passedStudent");

        edFirstName = rootView.findViewById(R.id.txt_firstname);
        edLastName = rootView.findViewById(R.id.txt_lastname);
        edBirthDate = rootView.findViewById(R.id.txt_birthdate);
        edEmail = rootView.findViewById(R.id.txt_email);
        edAddress = rootView.findViewById(R.id.txt_address);
        edLocation = rootView.findViewById(R.id.txt_location);
        edZip = rootView.findViewById(R.id.txt_zip);
        tvGender = rootView.findViewById(R.id.tv_gender);
        tvEducation = rootView.findViewById(R.id.tv_education);
        tvClassroom = rootView.findViewById(R.id.tv_classroom);
        btnGender = rootView.findViewById(R.id.btn_gender);
        btnClassroom = rootView.findViewById(R.id.btn_classroom);
        btnEducation = rootView.findViewById(R.id.btn_education);

        if (selectedStudent != null) {
            edFirstName.setText(selectedStudent.getFirstName());
            edLastName.setText(selectedStudent.getLastName());
            edBirthDate.setText(selectedStudent.getBirthDate().toString());
            edEmail.setText(selectedStudent.getEmail());
            edAddress.setText(selectedStudent.getAddress());
            edLocation.setText(selectedStudent.getLocation());
            edZip.setText(selectedStudent.getZip());
        }
        return rootView;
    }


}
