package io.polarpoint.product.service;

import io.polarpoint.product.domain.Branch;
import io.polarpoint.product.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
@Transactional
public class BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchService.class);

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    /**
     * Save a branch.
     *
     * @param branch the entity to save.
     * @return the persisted entity.
     */
    public Branch save(Branch branch) {
        log.debug("Request to save Branch : {}", branch);
        return branchRepository.save(branch);
    }

    /**
     * Get all the branches.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Branch> findAll() {
        log.debug("Request to get all Branches");
        return branchRepository.findAll();
    }


    /**
     * Get one branch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Branch> findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findById(id);
    }

    /**
     * Delete the branch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.deleteById(id);
    }
}
