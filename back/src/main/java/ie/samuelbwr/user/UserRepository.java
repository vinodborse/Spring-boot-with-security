package ie.samuelbwr.user;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User save( User user );

    User findByUsername( String username );

    void deleteAll();

}
