package ru.shop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.User;
import ru.shop.repositories.RegistrationConfirmationUuidRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RegistrationConfirmationUuidService extends AbstractEntityService
	<RegistrationConfirmationUuid, RegistrationConfirmationUuidRepository> {
	
	public RegistrationConfirmationUuidService(RegistrationConfirmationUuidRepository repository) {
		super(repository);
		this.entityClass = RegistrationConfirmationUuid.class;
	}
	
	/**
	 * @param uuid
	 * @throws IllegalArgumentException
	 * @throws EntityNotFoundException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void confirmUuid(String uuid) throws IllegalArgumentException, EntityNotFoundException {
		if (uuid == null || uuid.isBlank())
			throw new IllegalArgumentException("No UUID could by found for null or empty uuid!");

		RegistrationConfirmationUuid confirmedUuid = repository.findByUuid(uuid).orElseThrow(() ->
			new IllegalArgumentException("No Registration Confirmation found for this uuid!" +
				" It may have been expired within last 24h or this link has already been used to confirm a User account!"));
		
		
		User confirmedUser = confirmedUuid.getUser();
		confirmedUser.setRegistrationConfirmationUuid(null);
		confirmedUser.setEnabled(true);
		repository.delete(confirmedUuid);
	}
	
	/**
	 * @return {@link RegistrationConfirmationUuid} with an eagerly loaded {@link User}
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Optional<RegistrationConfirmationUuid> findOneByUuid(String uuid) {
		if (uuid == null || uuid.isBlank())
			throw new IllegalArgumentException("No UUID could by found for null or empty uuid!");
		return repository.findByUuid(uuid);
	}
}
