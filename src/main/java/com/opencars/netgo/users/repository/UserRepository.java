package com.opencars.netgo.users.repository;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.users.dto.UserId;
import com.opencars.netgo.users.dto.UserNames;
import com.opencars.netgo.users.dto.UsersData;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();
    @Query("SELECT r FROM User r where r.enable = 1 and (month(r.birthDate) = :monthInit OR month(r.birthDate) = :monthEnd)")
    List<User> findAllByBirthdayMonthRange(int monthInit, int monthEnd);
    @Query("SELECT r FROM User r where r.enable = 1 and day(r.birthDate) = :day and month(r.birthDate) = :month")
    List<User> findAllByBirthdaysCurrents(int day, int month);
    Optional<User> findByUsername(String username);
    Optional<User> findByMail(String mail);
    Optional<User> findByCuil(String cuil);
    @Query("SELECT r FROM User r WHERE :position MEMBER OF r.positions")
    List<User> findUserByPosition(Position position);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
    boolean existsByCuil(String username);
    @Query("SELECT r FROM User r where r.name like %:name%")
    List<User> findUserByCoincidence(String name);
    String querySector = "SELECT * FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN sector a ON s.sector_id = a.id WHERE a.id = :sector.id ORDER BY r.name ASC";
    @Query(value = querySector, nativeQuery = true)
    List<User> findBySector(Sector sector);
    String queryGM = "SELECT * FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id where p.linea = 1";
    @Query(value = queryGM, nativeQuery = true)
    Optional<User> findGeneralManager();
    String queryLineAndSector = "SELECT * FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN sector a ON s.sector_id = a.id WHERE p.linea = :line and a.id = :sector and r.enable = 1 ORDER BY r.name ASC";
    @Query(value = queryLineAndSector, nativeQuery = true)
    List<User> findUsersByLineAndSector(int line, int sector);

    String queryIdsLineAndSector = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN sector a ON s.sector_id = a.id WHERE p.linea = :line and a.id = :sector and r.enable = 1 ORDER BY r.name ASC";
    @Query(value = queryIdsLineAndSector, nativeQuery = true)
    List<Integer> findUsersIdsByLineAndSector(int line, int sector);

    String queryLineAndSectorToAuthorizations = "SELECT * FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id and s.name != 'Transversal' INNER JOIN sector a ON s.sector_id = a.id WHERE p.linea = :line and a.id = :sector and r.enable = 1 ORDER BY r.name ASC";
    @Query(value = queryLineAndSectorToAuthorizations, nativeQuery = true)
    List<User> findUsersByLineAndSectorToAuthorizations(int line, int sector);

    String queryLine = "SELECT * FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id WHERE p.linea = :line and r.enable = 1 group by r.id ORDER BY r.name ASC";
    @Query(value = queryLine, nativeQuery = true)
    List<User> findUsersByLine(int line);

    /*String queryLineAndSectorAndProvince = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN sector a ON s.sector_id = a.id INNER JOIN position_branch b ON p.id = b.position_id INNER JOIN branches branch ON b.branch_id = branch.id WHERE p.linea = :line and a.id = :sector and branch.province = :province and r.enable = 1 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLineAndSectorAndProvince, nativeQuery = true)
    List<Integer> findUsersByLineAndSectorAndProvince(int line, int sector, String province);*/

    String queryLineAndSectorAndBranch = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN position_branch b ON p.id = b.position_id INNER JOIN branches branch ON b.branch_id = branch.id WHERE p.linea = :line and s.id = :subsector and branch.id = :branch and r.enable = 1 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLineAndSectorAndBranch, nativeQuery = true)
    List<Integer> findUsersByLineSubSectorAndBranch(int line, int subsector, int branch);

    String queryManagers = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id WHERE (p.linea = -1 or p.linea = 0 or p.linea = 2) GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryManagers, nativeQuery = true)
    List<Integer> findManagers();

    String queryLines2 = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id WHERE p.linea = 2 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLines2, nativeQuery = true)
    List<Integer> findLines2();

    String queryLines3 = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id WHERE p.linea = 3 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLines3, nativeQuery = true)
    List<Integer> findLines3();

    String queryLines4 = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id WHERE p.linea = 4 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLines4, nativeQuery = true)
    List<Integer> findLines4();

    String queryLineAndSubsector = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id WHERE p.linea = :line and s.id = :subsector and r.enable = 1 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLineAndSubsector, nativeQuery = true)
    List<Integer> findUsersByLineAndSubsector(int line, int subsector);

    String queryLineAndSubsectorAndProvince = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN sector a ON s.sector_id = a.id INNER JOIN position_branch b ON p.id = b.position_id INNER JOIN branches branch ON b.branch_id = branch.id INNER JOIN brandscompany bcomp ON branch.brands_company_id = bcomp.id INNER JOIN company comp ON bcomp.company_id = comp.id WHERE p.linea = :line and s.id = :subsector and comp.id = :company and r.enable = 1 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLineAndSubsectorAndProvince, nativeQuery = true)
    List<Integer> findUsersByLineAndSubsectorAndCompany(int line, int subsector, int company);

    String queryLineAndSubsectorAndCity = "SELECT r.id FROM users r INNER JOIN usuario_position rel ON r.id = rel.usuario_id INNER JOIN positions p ON rel.position_id = p.id INNER JOIN subsector s ON p.sub_sector_id = s.id INNER JOIN sector a ON s.sector_id = a.id INNER JOIN position_branch b ON p.id = b.position_id INNER JOIN branches branch ON b.branch_id = branch.id WHERE p.linea = :line and s.id = :subsector and branch.city = :city and r.enable = 1 GROUP BY r.id ORDER BY r.name ASC";
    @Query(value = queryLineAndSubsectorAndCity, nativeQuery = true)
    List<Integer> findUsersByLineAndSubsectorAndCity(int line, int subsector, String city);

    @Query("SELECT new com.opencars.netgo.users.dto.UserId(r.id, r.mail) FROM User r WHERE r.username = :username")
    Optional<UserId> findUserIdByUsername(String username);
    @Query("SELECT r FROM User r where r.name like %:name% and r.enable = 1")
    List<User> findUsersEnabledsByCoincidence(String name);
    @Query("SELECT new com.opencars.netgo.users.dto.UserNames(r.id, r.name, r.username) FROM User r where r.name like %:name% and r.enable = 1")
    List<UserNames> findUsersNamesEnabledsByCoincidence(String name);
    List<User> findByRolesContains(Rol role);
    @Query("SELECT r FROM User r where r.name like %:name% and :position MEMBER OF r.positions")
    List<User> findUserByPositionAndCoincidenceInName(String name, Position position);
    @Query("SELECT r FROM User r where r.enable = 1")
    List<User> usersEnables();
    @Query("SELECT r FROM User r where r.branch = :branch and r.enable = 1")
    List<User> findUsersEnablesByBranch(Branch branch);
    @Query("SELECT COUNT(r) FROM User r where r.branch = :branch and r.enable = 1")
    Integer countUsersEnablesByBranch(Branch branch);

    String query = "SELECT r.id, r.branch, r.payroll, r.cuil, r.enable, r.imgProfile, r.mail, r.name, a.name, p.position, r.gender FROM users r" +
            " INNER JOIN positions p ON r.position_id = p.id" +
            " INNER JOIN subsector s ON p.sub_sector_id = s.id" +
            " INNER JOIN sector a ON s.sector_id = a.id";
    @Query(value = query, nativeQuery = true)
    List<UsersData> findUsers();
    @Query("SELECT COUNT(r) FROM User r WHERE r.enable = 1")
    int countUsersEnableds();

}
