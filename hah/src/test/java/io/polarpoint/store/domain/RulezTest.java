package io.polarpoint.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.store.web.rest.TestUtil;

public class RulezTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rulez.class);
        Rulez rulez1 = new Rulez();
        rulez1.setId(1L);
        Rulez rulez2 = new Rulez();
        rulez2.setId(rulez1.getId());
        assertThat(rulez1).isEqualTo(rulez2);
        rulez2.setId(2L);
        assertThat(rulez1).isNotEqualTo(rulez2);
        rulez1.setId(null);
        assertThat(rulez1).isNotEqualTo(rulez2);
    }
}
