package ru.matvey.dealership;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.contracts.services.DetailService;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.infrastracture.jparepositories.JpaUserRepository;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SomeTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void user_ShouldBeCreatedAndDeleted() {
        UUID id = userService.addUser("testUser", "password", "John Doe", "CLIENT");

        var usersAfterAdd = userRepository.findAll();
        assertThat(usersAfterAdd).anyMatch(user -> user.getId().equals(id));

        userService.deleteUser(id);

        var usersAfterDelete = userRepository.findById(id);
        assertThat(usersAfterDelete).isEmpty();
    }

    @Test
    void detail_ShouldBeCreatedAndFetched() {
        String targetModel = "Camry";

        UUID detailId = detailService.addDetail(
                new DetailOperation.Request("body", "Sedan", 1500.0, Set.of(targetModel))
        );

        var detail = detailService.getDetail(detailId);

        assertThat(detail).isNotNull();
        assertThat(detail.cost()).isEqualTo(1500.0);
        assertThat(detail.detailInfo()).isEqualTo("Sedan");
        assertThat(detail.detailType()).isEqualTo("Body");
    }
}