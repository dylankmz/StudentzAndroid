package be.kmz.studentz.model.util;

import androidx.room.TypeConverter;
import org.threeten.bp.LocalDate;

public class DateConverters {

    @TypeConverter
    public static String toDateString (LocalDate date){
        return (date == null)?null:date.toString();
    }

    //for backwards compatibility use a library like ThreeTen
    //https://github.com/JakeWharton/ThreeTenABP
    @TypeConverter
    public static LocalDate toDate (String dateString){
        return (dateString == null)?null:LocalDate.parse(dateString);
    }
}
