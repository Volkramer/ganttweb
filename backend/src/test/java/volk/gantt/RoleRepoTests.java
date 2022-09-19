package volk.gantt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import volk.gantt.model.Role;
import volk.gantt.repo.RoleRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepoTests {
    @Autowired
    private RoleRepo roleRepo;

    @Test
    public void testCreateRole() {
        Role newRole = new Role("ROLE_TEST");
        Role savedRole = roleRepo.save(newRole);

        assertThat(savedRole, notNullValue());
        assertThat(savedRole.getId().intValue(), greaterThan(0));
    }
}
