package be.kmz.studentz.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

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
                int pos = getAdapterPosition();
                Student 
            }
        }
    }
}
