package waguri.storage;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Main class for the DateParser.
 */
public class DateParser {
    /**
     * @param input
     * @return
     */
    public static LocalDateTime parse(String input) {
        LocalDateTime now = LocalDateTime.now();

        if (input.equals("today")) {
            return now.withHour(23).withMinute(59);
        }
        if (input.equals("tomorrow")) {
            return now.plusDays(1).withHour(23).withMinute(59);
        }
        if (input.equals("tonight")) {
            return now.withHour(23).withMinute(59);
        }
        if (input.equals("tn")) {
            return now.withHour(23).withMinute(59);
        }

        if (input.equals("monday") || input.equals("mon")) {
            return now.with(DayOfWeek.MONDAY).withHour(23).withMinute(59);
        }
        if (input.equals("tuesday") || input.equals("tues") || input.equals("tue")) {
            return now.with(DayOfWeek.TUESDAY).withHour(23).withMinute(59);
        }
        if (input.equals("wednesday") || input.equals("wed")) {
            return now.with(DayOfWeek.WEDNESDAY).withHour(23).withMinute(59);
        }
        if (input.equals("thursday") || input.equals("thurs") || input.equals("thur")) {
            return now.with(DayOfWeek.THURSDAY).withHour(23).withMinute(59);
        }
        if (input.equals("friday") || input.equals("fri")) {
            return now.with(DayOfWeek.FRIDAY).withHour(23).withMinute(59);
        }
        if (input.equals("saturday") || input.equals("sat")) {
            return now.with(DayOfWeek.SATURDAY).withHour(23).withMinute(59);
        }
        if (input.equals("sunday") || input.equals("sun")) {
            return now.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59);
        }

        if (input.equals("1am")) {
            return now.withHour(1).withMinute(0);
        }
        if (input.equals("2am")) {
            return now.withHour(2).withMinute(0);
        }
        if (input.equals("3am")) {
            return now.withHour(3).withMinute(0);
        }
        if (input.equals("4am")) {
            return now.withHour(4).withMinute(0);
        }
        if (input.equals("5am")) {
            return now.withHour(5).withMinute(0);
        }
        if (input.equals("6am")) {
            return now.withHour(6).withMinute(0);
        }

        if (input.equals("7am")) {
            return now.withHour(7).withMinute(0);
        }
        if (input.equals("8am")) {
            return now.withHour(8).withMinute(0);
        }
        if (input.equals("9am")) {
            return now.withHour(9).withMinute(0);
        }
        if (input.equals("10am")) {
            return now.withHour(10).withMinute(0);
        }
        if (input.equals("11am")) {
            return now.withHour(11).withMinute(0);
        }
        if (input.equals("1pm")) {
            return now.withHour(13).withMinute(0);
        }
        if (input.equals("2pm")) {
            return now.withHour(14).withMinute(0);
        }
        if (input.equals("3pm")) {
            return now.withHour(15).withMinute(0);
        }
        if (input.equals("4pm")) {
            return now.withHour(16).withMinute(0);
        }
        if (input.equals("5pm")) {
            return now.withHour(17).withMinute(0);
        }
        if (input.equals("6pm")) {
            return now.withHour(18).withMinute(0);
        }
        if (input.equals("7pm")) {
            return now.withHour(19).withMinute(0);
        }

        if (input.equals("8pm")) {
            return now.withHour(20).withMinute(0);
        }
        if (input.equals("9pm")) {
            return now.withHour(21).withMinute(0);
        }
        if (input.equals("10pm")) {
            return now.withHour(22).withMinute(0);
        }
        if (input.equals("11pm")) {
            return now.withHour(23).withMinute(0);
        }
        if (input.equals("12pm")) {
            return now.withHour(12).withMinute(0);
        }
        if (input.equals("12am")) {
            return now.withHour(0).withMinute(0);
        }

        for (DayOfWeek d : DayOfWeek.values()) {
            if (input.equals(d.toString().toLowerCase())) {
                LocalDateTime date = now;
                while (date.getDayOfWeek() != d) {
                    date = date.plusDays(1);
                }
                return date.withHour(23).withMinute(59);
            }
        }

        String[] patterns = {
            "MMM dd",
            "yyyy MMM dd HH:mm",
            "yyyy MMM dd",
            "yyyy MMM dd'D' HH:mm",
            "yyyy MM'm' dd'd' HH:mm",
            "yyyy MM dd HH:mm",
            "yyyy-MM-dd HHmm",
            "yyyy-MM-dd HH:mm",
            "dd/MM/yyyy HHmm",
            "dd/MM/yyyy HH:mm",
            "MM/dd/yyyy HHmm",
            "MM/dd/yyyy HH:mm",
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "MM/dd/yyyy",
            "yyyy/MM/dd",
            "yyyy MMM dd",
            "yyyy dd MMM",
            "MMM yyyy dd",
            "MMM dd yyyy",
            "MMM dd",
            "MM dd",
            "dd MM"
        };

        for (String p : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(p);

                if (p.contains("H")) {
                    return LocalDateTime.parse(input, f);
                } else {
                    LocalDate d = LocalDate.parse(input, f);
                    return LocalDateTime.of(d, LocalTime.MIDNIGHT);
                }
            } catch (Exception e) {
                continue;
            }
        }

        throw new IllegalArgumentException("Unrecognized date/time format: " + input);
    }
}

