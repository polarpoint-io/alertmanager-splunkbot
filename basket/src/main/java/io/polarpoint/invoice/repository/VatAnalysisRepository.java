package io.polarpoint.invoice.repository;

import io.polarpoint.invoice.domain.VatAnalysis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VatAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VatAnalysisRepository extends JpaRepository<VatAnalysis, Long> {

}
