package io.polarpoint.product.repository;

import io.polarpoint.product.domain.Branch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Branch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

}
