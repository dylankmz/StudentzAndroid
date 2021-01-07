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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.kmz.studentz.R;
import be.kmz.studentz.adapter.StudentsAdapter;
import be.kmz.studentz.model.Student;
import be.kmz.studentz.model.StudentViewModel;

public class StudentListFragment extends Fragment {

    private RecyclerView studentsCard;
    private StudentsAdapter studentsAdapter;
    private FragmentActivity mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);

        studentsAdapter = new StudentsAdapter(getActivity());
        studentsCard = rootView.findViewById(R.id.student_card);
        studentsCard.setAdapter(studentsAdapter);

        FloatingActionButton faBtn = rootView.findViewById(R.id.fab_add);
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_studentListFragment_to_studentDetailsFragment);
            }
        });

        StudentViewModel model = new ViewModelProvider(mContext).get(StudentViewModel.class);
        model.getStudents().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                studentsAdapter.addStudents(students);
                studentsAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

}
