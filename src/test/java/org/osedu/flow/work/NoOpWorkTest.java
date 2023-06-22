package org.osedu.flow.work;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class NoOpWorkTest {

    private final NoOpWork noOpWork = new NoOpWork();

    @Test
    public void testGetName() {
        Assertions.assertThat(noOpWork.getName()).isNotNull();
    }

    @Test
    public void testExecute() {
        WorkReport workReport = noOpWork.execute(new WorkContext());
        Assert.assertNotNull(workReport);
        Assertions.assertThat(workReport.getStatus()).isEqualTo(WorkStatus.COMPLETED);
    }
}
