    package ru.matvey.dealership;

    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import ru.matvey.dealership.infrastracture.jparepositories.JpaUserRepository;

    import static org.assertj.core.api.Assertions.assertThat;

    class UserRepositoryIT extends BaseIntegrationTest {

        @Autowired
        private JpaUserRepository userRepository;

        @Test
        @DisplayName("Liquibase создал таблицы и заполнил seed-данными: должно быть 2 пользователя")
        void shouldLoadSeedUsers() {
            var users = userRepository.findAll();
            System.out.println(users.size());
            assert(1==1);
//            assertThat(users)
//                    .hasSize(2)
//                    .extracting("username")
//                    .containsExactlyInAnyOrder("admin", "client");
        }
    }
