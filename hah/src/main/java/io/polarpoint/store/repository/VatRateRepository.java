package io.polarpoint.store.repository;

import io.polarpoint.store.domain.VatRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VatRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VatRateRepository extends JpaRepository<VatRate, Long> {

}
