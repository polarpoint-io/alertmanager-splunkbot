package io.polarpoint.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.store.web.rest.TestUtil;

public class SubscriptionGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionGroup.class);
        SubscriptionGroup subscriptionGroup1 = new SubscriptionGroup();
        subscriptionGroup1.setId(1L);
        SubscriptionGroup subscriptionGroup2 = new SubscriptionGroup();
        subscriptionGroup2.setId(subscriptionGroup1.getId());
        assertThat(subscriptionGroup1).isEqualTo(subscriptionGroup2);
        subscriptionGroup2.setId(2L);
        assertThat(subscriptionGroup1).isNotEqualTo(subscriptionGroup2);
        subscriptionGroup1.setId(null);
        assertThat(subscriptionGroup1).isNotEqualTo(subscriptionGroup2);
    }
}
