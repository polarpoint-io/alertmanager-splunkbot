package io.polarpoint.store.repository;

import io.polarpoint.store.domain.Token;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Token entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

}
