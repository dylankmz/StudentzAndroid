package be.kmz.studentz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public StudentListFragment() {
    }

    @NonNull
    public static StudentListFragment newInstance() {
        return new StudentListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);

        studentsAdapter = new StudentsAdapter(getActivity());
        studentsCard = rootView.findViewById(R.id.rv_students);
        studentsCard.setAdapter(studentsAdapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        studentsCard.setLayoutManager(manager);

        StudentViewModel model = new ViewModelProvider(mContext).get(StudentViewModel.class);
        model.getStudents().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                studentsAdapter.addStudents(students);
                studentsAdapter.notifyDataSetChanged();
            }
        });

        //ref: https://abhiandroid.com/ui/searchview & NoteDroid V4 van Docent
        SearchView searchView = rootView.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                studentsAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                studentsAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return rootView;
    }
}
