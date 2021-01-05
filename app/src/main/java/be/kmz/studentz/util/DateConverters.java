package be.kmz.studentz.util;

import androidx.room.TypeConverter;

import org.threeten.bp.LocalDate;

public class DateConverters {

    @TypeConverter
    public static String toDateString (LocalDate date) {
        return (date == null) ? null:date.toString();
    }

    @TypeConverter
    public static org.threeten.bp.LocalDate toDate (String dateString) {
        return (dateString == null) ? null: org.threeten.bp.LocalDate.parse(dateString);
    }
}
