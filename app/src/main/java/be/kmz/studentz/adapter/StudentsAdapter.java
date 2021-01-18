package be.kmz.studentz.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.kmz.studentz.R;
import be.kmz.studentz.fragment.StudentDetailsFragment;
import be.kmz.studentz.model.Student;
import be.kmz.studentz.model.StudentViewModel;

//filterable is nodig voor voor search, het is een interface met een publieke abstracte methode getFilter()
public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> implements Filterable {

    //adapter klasse is nodig voor onze data(bridge), het geeft toegang

    private FragmentActivity activity;
    private List<Student> allStudents;
    private List<Student> students;

    class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvFirstName, tvLastName, tvEducation, tvClassroom;
        final Button btnDelete;
        final CardView card;

        //Viewholder bevat constanten voor de componenten in de layout en een constructor
        //elke verwijzing/element wordt 1x opgebouwd en bijgehouden als cache
        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.firstname_tv);
            tvLastName = itemView.findViewById(R.id.lastname_tv);
            tvEducation = itemView.findViewById(R.id.education_tv);
            tvClassroom = itemView.findViewById(R.id.classroom_tv);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(deleteListener);
            card = itemView.findViewById(R.id.student_card);
            card.setOnClickListener(detailsListener);
        }

        //deleteListener om de student op de geklikte positie te verwijderen
        private View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Student toBeDeleted = students.get(position);
                StudentViewModel model = new ViewModelProvider(activity).get(StudentViewModel.class);
                model.deleteStudent(toBeDeleted);
                Toast.makeText(activity, R.string.toast_student_deleted, Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        };

        //detailsListener om meer gegevens van de student op de geklikte positie weer te geven
        private View.OnClickListener detailsListener = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //positie nemen met adapter
                int position = getAdapterPosition();
                Student toPass = students.get(position);

                Bundle data = new Bundle();
                //student data uit genomen positie plaatsen in Bundle
                data.putSerializable("passedStudent", toPass);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, StudentDetailsFragment.newInstance(data)).commit();
            }
        };
    }

    //ref: NoteDroid V4
    public StudentsAdapter(FragmentActivity activity) {
        this.allStudents = new ArrayList<>();
        this.students = new ArrayList<>();
        this.activity = activity;
    }

    //maakt nieuwe viewholder als er geen bestaande viewholders zijn
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View studentCard = LayoutInflater.from(context).inflate(R.layout.student_row, viewGroup, false);
        return new StudentViewHolder(studentCard);
    }

    //dit wordt gebruikt om data te tonen vanuit de juiste positie, wordt geupdate
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student s = students.get(i);
        studentViewHolder.tvFirstName.setText(s.getFirstName());
        studentViewHolder.tvLastName.setText(s.getLastName());
        studentViewHolder.tvEducation.setText(s.getEducation());
        studentViewHolder.tvClassroom.setText(s.getClassroom());
    }

    //dit is nodige voor onze adapter klasse, returned lijstgrootte
    @Override
    public int getItemCount() {
        return students.size();
    }


    //ref: NoteDroid V4, lijsten resetten
    public void addStudents(List<Student> student) {
        allStudents.clear();
        allStudents.addAll(student);
        students.clear();
        students.addAll(student);
    }

    //ref: NoteDroid V4 app van Docent
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();
                students = allStudents;
                if (!input.isEmpty()) {
                    ArrayList<Student> tempList = new ArrayList<>();

                    //firstName & lastName & email search
                    for (Student element : students) {
                        if (element.getLastName().toLowerCase().contains(input.toLowerCase()) || element.getFirstName().toLowerCase().contains(input.toLowerCase())
                        || element.getEmail().toLowerCase().contains(input.toLowerCase())) {
                            tempList.add(element);
                        }
                    }
                    students = tempList;
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

