package io.polarpoint.product.repository;

import io.polarpoint.product.domain.VatRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VatRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VatRateRepository extends JpaRepository<VatRate, Long> {

}
