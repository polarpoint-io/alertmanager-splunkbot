package io.polarpoint.store.repository;

import io.polarpoint.store.domain.SubscriptionGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubscriptionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionGroupRepository extends JpaRepository<SubscriptionGroup, Long> {

}
