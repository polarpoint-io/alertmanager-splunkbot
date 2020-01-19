package io.polarpoint.invoice.repository;

import io.polarpoint.invoice.domain.Basket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Basket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

}
