package edu.suams.registration;

import java.time.LocalDate;
import java.util.Objects;

/** Rule R3: the dates, inclusive, when the department accepts registrations. */
public record RegistrationWindow(LocalDate open, LocalDate close) {

    public RegistrationWindow {
        Objects.requireNonNull(open, "open");
        Objects.requireNonNull(close, "close");
        if (close.isBefore(open)) {
            throw new IllegalArgumentException("window closes before it opens");
        }
    }

    public boolean isOpen(LocalDate day) {
        return !day.isBefore(open) && !day.isAfter(close);
    }
}
