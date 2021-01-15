package be.kmz.studentz.model.util;

import androidx.room.TypeConverter;
import org.threeten.bp.LocalDate;

//not using
public class DateConverters {

    @TypeConverter
    public static String toDateString (LocalDate date){
        return (date == null)?null:date.toString();
    }

    @TypeConverter
    public static LocalDate toDate (String dateString){
        return (dateString == null)?null:LocalDate.parse(dateString);
    }
}
