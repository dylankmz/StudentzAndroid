package be.kmz.studentz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import be.kmz.studentz.R;
import be.kmz.studentz.dialog.ClassroomDialog;
import be.kmz.studentz.dialog.EducationDialog;
import be.kmz.studentz.dialog.GenderDialog;
import be.kmz.studentz.dialog.InfoDialog;
import be.kmz.studentz.model.Student;
import be.kmz.studentz.model.StudentViewModel;

public class StudentDetailsFragment extends Fragment {

    private EditText edFirstName, edLastName, edBirthDate, edEmail, edAddress, edLocation, edZip;
    private TextView tvGender, tvEducation, tvClassroom, tvAddStudentTitle;
    private TextInputLayout firstNameLayout, lastNameLayout, birthDateLayout, emailLayout, addressLayout, locationLayout, zipLayout;
    private Button btnGender, btnEducation, btnClassroom;
    private ImageButton btnInfo;
    private Student selectedStudent;
    private FragmentActivity mContext;

    public StudentDetailsFragment() {
    }

    @NonNull
    public static StudentDetailsFragment newInstance() {
        return new StudentDetailsFragment();
    }

    public static Fragment newInstance(Bundle data) {
        StudentDetailsFragment studentDetailsFragment = new StudentDetailsFragment();
        studentDetailsFragment.setArguments(data);
        return studentDetailsFragment;
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

        selectedStudent = (getArguments() != null)?(Student) getArguments().getSerializable("passedStudent"):null;

        edFirstName = rootView.findViewById(R.id.txtFirstName);
        edLastName = rootView.findViewById(R.id.txtLastName);
        edBirthDate = rootView.findViewById(R.id.txtBirthDate);
        edEmail = rootView.findViewById(R.id.txtEmail);
        edAddress = rootView.findViewById(R.id.txtAddress);
        edLocation = rootView.findViewById(R.id.txtLocation);
        edZip = rootView.findViewById(R.id.txtZip);

        tvGender = rootView.findViewById(R.id.txtGender);
        tvEducation = rootView.findViewById(R.id.txtEducation);
        tvClassroom = rootView.findViewById(R.id.txtClassroom);
        tvAddStudentTitle = rootView.findViewById(R.id.titleAddStudent);

        btnGender = rootView.findViewById(R.id.btn_gender);
        btnGender.setOnClickListener(openGenderDialogListener);

        btnClassroom = rootView.findViewById(R.id.btn_classroom);
        btnClassroom.setOnClickListener(openClassroomDialogListener);

        btnEducation = rootView.findViewById(R.id.btn_education);
        btnEducation.setOnClickListener(openEducationDialogListener);

        btnInfo = rootView.findViewById(R.id.btn_info);
        btnInfo.setOnClickListener(infoListener);

        firstNameLayout = rootView.findViewById(R.id.txtFistNameLayout);
        lastNameLayout = rootView.findViewById(R.id.txtLastNameLayout);
        birthDateLayout = rootView.findViewById(R.id.txtBirthDateLayout);
        emailLayout = rootView.findViewById(R.id.txtEmailLayout);
        addressLayout = rootView.findViewById(R.id.txtAddressLayout);
        locationLayout = rootView.findViewById(R.id.txtLocationLayout);
        zipLayout = rootView.findViewById(R.id.txtZipLayout);

        StudentViewModel model = new ViewModelProvider(mContext).get(StudentViewModel.class);
        model.getSharedStudent().observe(getViewLifecycleOwner(), new Observer<Student>() {
                    @Override
                    public void onChanged(Student student) {
                        //als ik deze verificatie niet uitvoer zet hij de waarde van gender, education en classroom
                        //op de default waarde van sharedStudent altijd
                        if (selectedStudent == null){
                            tvGender.setText(student.getGender());
                            tvEducation.setText(student.getEducation());
                            tvClassroom.setText(student.getClassroom());
                        }
                    }
                });

        if (selectedStudent != null) {
            edFirstName.setText(selectedStudent.getFirstName());
            edLastName.setText(selectedStudent.getLastName());
            tvGender.setText(selectedStudent.getGender());
            edBirthDate.setText(selectedStudent.getBirthDate());
            tvEducation.setText(selectedStudent.getEducation());
            tvClassroom.setText(selectedStudent.getClassroom());
            edEmail.setText(selectedStudent.getEmail());
            edAddress.setText(selectedStudent.getAddress());
            edLocation.setText(selectedStudent.getLocation());
            edZip.setText(selectedStudent.getZip());
        }

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentViewModel model = new ViewModelProvider(mContext).get(StudentViewModel.class);
                if (selectedStudent == null && checkFirstName() && checkLastName() && checkBirthDate()
                        && checkEmail() && checkAddress() && checkLocation() && checkZip()) {
                    Student s = new Student(edFirstName.getText().toString(),
                            edLastName.getText().toString(),
                            tvGender.getText().toString(),
                            edBirthDate.getText().toString(),
                            tvEducation.getText().toString(),
                            tvClassroom.getText().toString(),
                            edEmail.getText().toString(),
                            edAddress.getText().toString(),
                            edLocation.getText().toString(),
                            edZip.getText().toString());
                    Toast.makeText(mContext, "Student added!", Toast.LENGTH_LONG).show();
                    model.insertStudent(s);
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, StudentListFragment.newInstance()).commit();
                } else if (checkFirstName() && checkLastName() && checkBirthDate()
                        && checkEmail() && checkAddress() && checkLocation() && checkZip()){
                    selectedStudent.setFirstName(edFirstName.getText().toString());
                    selectedStudent.setLastName(edLastName.getText().toString());
                    selectedStudent.setGender(tvGender.getText().toString());
                    selectedStudent.setBirthDate(edBirthDate.getText().toString());
                    selectedStudent.setEducation(tvEducation.getText().toString());
                    selectedStudent.setClassroom(tvClassroom.getText().toString());
                    selectedStudent.setEmail(edEmail.getText().toString());
                    selectedStudent.setAddress(edAddress.getText().toString());
                    selectedStudent.setLocation(edLocation.getText().toString());
                    selectedStudent.setZip(edZip.getText().toString());
                    model.updateStudent(selectedStudent);
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, StudentListFragment.newInstance()).commit();
                }
            }
        });

        return rootView;
    }

    private View.OnClickListener openGenderDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GenderDialog genderDialog = new GenderDialog();
            genderDialog.show(getParentFragmentManager(), "gd");
        }
    };

    private View.OnClickListener openEducationDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EducationDialog educationDialog = new EducationDialog();
            educationDialog.show(getParentFragmentManager(), "ed");
        }
    };

    private View.OnClickListener openClassroomDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClassroomDialog classroomDialog = new ClassroomDialog();
            classroomDialog.show(getParentFragmentManager(), "cd");
        }
    };

    private View.OnClickListener infoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new InfoDialog().show(getParentFragmentManager(), "infolst");
        }
    };

    //ref voor regex: stackoverflow & https://regexlib.com/Search.aspx?k=street&AspxAutoDetectCookieSupport=1
    private boolean checkFirstName() {
        if (!edFirstName.getText().toString().matches("[A-Z][a-z]*")) {
            firstNameLayout.setError("Please insert a valid first name!");
            return false;
        } else {
            firstNameLayout.setError(null);
            return true;
        }
    }

    private boolean checkLastName() {
        if (!edLastName.getText().toString().matches("[A-Z][a-z]*")) {
            lastNameLayout.setError("Please insert a valid last name!");
            return false;
        } else {
            lastNameLayout.setError(null);
            return true;
        }
    }

    private boolean checkBirthDate() {
        if (!edBirthDate.getText().toString().matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$")) {
            birthDateLayout.setError("Please insert a valid birth date!");
            return false;
        } else {
            birthDateLayout.setError(null);
            return true;
        }
    }

    //ref: https://stackoverflow.com/questions/8204680/java-regex-email
    private boolean checkEmail() {
        if (!edEmail.getText().toString().matches("^(.+)@(.+)$")) {
            emailLayout.setError("Please insert a valid email!");
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }
    }

    private boolean checkAddress() {
        if (!edAddress.getText().toString().matches("\\A(.*?)\\s+(\\d+[a-zA-Z]?\\s?[-]\\s?\\d*[a-zA-Z]?|\\d+[a-zA-Z-]?\\d*[a-zA-Z]?)")) {
            addressLayout.setError("Please insert a valid home address and house number!");
            return false;
        } else {
            addressLayout.setError(null);
            return true;
        }
    }

    private boolean checkLocation() {
        if (!edLocation.getText().toString().matches("[A-Z][a-z]*")) {
            locationLayout.setError("Please insert a valid location!");
            return false;
        } else {
            locationLayout.setError(null);
            return true;
        }
    }

    private boolean checkZip() {
        if (!edZip.getText().toString().matches("[0-9]+")) {
            zipLayout.setError("Please insert a valid belgian zip!");
            return false;
        } else {
            zipLayout.setError(null);
            return true;
        }
    }
}
