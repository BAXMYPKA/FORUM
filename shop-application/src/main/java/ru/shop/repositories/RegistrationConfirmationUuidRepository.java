package ru.shop.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import ru.shop.entities.RegistrationConfirmationUuid;

import java.util.Optional;

@Repository
public interface RegistrationConfirmationUuidRepository extends EntityRepository<RegistrationConfirmationUuid> {
	
	@EntityGraph(value = "uuid-with-user", type = EntityGraph.EntityGraphType.FETCH)
	Optional<RegistrationConfirmationUuid> findByUuid(String uuid);
}
