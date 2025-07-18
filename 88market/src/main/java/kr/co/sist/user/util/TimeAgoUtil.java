package kr.co.sist.user.util;

import java.sql.Date;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class TimeAgoUtil {

    public static String format(Date inputDate) {
        if (inputDate == null) return "";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime input = inputDate.toLocalDate().atStartOfDay();
        long minutes = ChronoUnit.MINUTES.between(input, now);
        long hours = ChronoUnit.HOURS.between(input, now);
        long days = ChronoUnit.DAYS.between(input, now);

        if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 7) {
            return days + "일 전";
        } else if (days < 30) {
            return (days / 7) + "주 전";
        } else {
            return input.toLocalDate().toString(); // "yyyy-MM-dd"
        }
    }
}

