package io.polarpoint.product.repository;

import io.polarpoint.product.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Page entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

}
