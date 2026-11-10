package edu.suams.registration;

import java.util.Optional;

/** Read access to Student Records. Tests use an in-memory version. */
public interface StudentRepository {
    Optional<Student> findById(String studentId);
}
