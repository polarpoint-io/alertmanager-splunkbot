package io.polarpoint.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.store.web.rest.TestUtil;

public class BranchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Branch.class);
        Branch branch1 = new Branch();
        branch1.setId(1L);
        Branch branch2 = new Branch();
        branch2.setId(branch1.getId());
        assertThat(branch1).isEqualTo(branch2);
        branch2.setId(2L);
        assertThat(branch1).isNotEqualTo(branch2);
        branch1.setId(null);
        assertThat(branch1).isNotEqualTo(branch2);
    }
}
