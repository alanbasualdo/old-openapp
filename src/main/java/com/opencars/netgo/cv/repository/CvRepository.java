package com.opencars.netgo.cv.repository;

import com.opencars.netgo.cv.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer> {
    @Query("SELECT CASE WHEN (COUNT(r) > 0) then true else false end from Cv r where r.colaborator.id = :colaborator")
    boolean existsByColaborator(int colaborator);

    @Query("SELECT r FROM Cv r where r.colaborator.id = :colaborator")
    Optional<Cv> findByColaborator(int colaborator);

    String findQuery = "(SELECT c.id, c.colaborator_id FROM cv c" +
            " INNER JOIN cv_education e ON c.id = e.cv_id" +
            " INNER JOIN education ed ON e.education_id = ed.id" +
            " WHERE ed.title LIKE CONCAT('%', :coincidence, '%'))" +
            " UNION" +
            " (SELECT c.id, c.colaborator_id FROM cv c" +
            " INNER JOIN cv_certifications cert ON c.id = cert.cv_id" +
            " INNER JOIN certifications certif ON cert.certification_id = certif.id" +
            " WHERE certif.course LIKE CONCAT('%', :coincidence, '%'))" +
            " UNION" +
            " (SELECT c.id, c.colaborator_id FROM cv c" +
            " INNER JOIN cv_experience exp ON c.id = exp.cv_id" +
            " INNER JOIN experience exper ON exp.experience_id = exper.id" +
            " WHERE exper.position LIKE CONCAT('%', :coincidence, '%'))" +
            " UNION" +
            " (SELECT c.id, c.colaborator_id FROM cv c" +
            " INNER JOIN cv_hobbies h ON c.id = h.cv_id" +
            " INNER JOIN hobbies hob ON h.hobbies_id = hob.id" +
            " WHERE hob.hobbie LIKE CONCAT('%', :coincidence, '%'))";
    @Query(value = findQuery, nativeQuery = true)
    List<Cv> findByCoincidence(String coincidence);

    String countCreatedEnables = "SELECT COUNT(c.id) FROM cv c" +
            " INNER JOIN users u ON c.colaborator_id = u.id" +
            " WHERE u.enable = 1";
    @Query(value = countCreatedEnables, nativeQuery = true)
    int countCVsCreatedsWithUsersEnableds();

}
