package be.kmz.studentz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.kmz.studentz.R;
import be.kmz.studentz.model.Student;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> implements Filterable {

    private FragmentActivity activity;
    private List<Student> allStudents;

    class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvFirstName, tvLastName, tvEducation;
        final Button btnDelete;
        final CardView card;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.firstname_tv);
            tvLastName = itemView.findViewById(R.id.lastname_tv);
            tvEducation = itemView.findViewById(R.id.education_tv);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            //deleteListener
            card = itemView.findViewById(R.id.student_card);
            //card details listener
        }

        private View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Student toBeDeleted = allStudents.get(position);

                StudentViewModel model = new ViewModelProvider(activity).get(StudentViewModel.class);
                model.deteteStudent(toBeDeleted);
                notifyDataSetChanged();
            }
        };

        private View.OnClickListener detailsListener = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int position = getAdapterPosition();
                Student toPass = allStudents.get(position);

                Bundle data = new Bundle();
                data.putSerializable("passedStudent", toPass);

                Navigation.findNavController(itemView).navigate(R.id.action_nav_home_to_noteDetailsFragment, data);
            }
        };
    }

    public StudentsAdapter(FragmentActivity activity) {
        this.allStudents = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View studentCard = LayoutInflater.from(context).inflate(R.layout.student_row, viewGroup, false);
        return new StudentViewHolder(studentCard);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student s = allStudents.get(i);

        studentViewHolder.tvFirstName.setText(s.getFirstName());
        studentViewHolder.tvLastName.setText(s.getLastName());
        studentViewHolder.tvEducation.setText(s.getEducation().toString());
    }

    public void addStudents(List<Student> students) {
        allStudents.clear();
        allStudents.addAll(students);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();
                allStudents = allStudents;
                if (!input.isEmpty()) {
                    ArrayList<Student> tempList = new ArrayList<>();

                    for (Student element : allStudents) {
                        if (element.getLastName().toLowerCase().contains(input.toLowerCase())) {
                            tempList.add(element);
                        }
                    }
                    allStudents = tempList;
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}

