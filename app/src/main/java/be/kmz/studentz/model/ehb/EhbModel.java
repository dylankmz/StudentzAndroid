package be.kmz.studentz.model.ehb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class EhbModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Ehb>> ehbCampusList;

    public EhbModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Ehb>> getEhbCampusList(){
        if (ehbCampusList == null) {
            ehbCampusList = new MutableLiveData<>();
            ArrayList<Ehb> ehbSchools = new ArrayList<>();
            ehbSchools.add(new Ehb(new LatLng(50.84765158611861, 4.344008196195696),
                    "Ehb Bloemenhof",
                    "Campus Bloemenhof"));
            ehbSchools.add((new Ehb(new LatLng(50.842266078753354, 4.322805316393921),
                    "Ehb Kaai",
                    "Ehb Campus Kaai")));
            ehbSchools.add(new Ehb(new LatLng(50.884500571360995, 4.306714298044455),
                    "Ehb Jette",
                    "Ehb Campus Jette"));
            ehbCampusList.setValue(ehbSchools);
        }
        return ehbCampusList;
    }
}
