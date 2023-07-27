package org.osedu.flows.workflow;

import org.junit.Test;
import org.mockito.Mockito;
import org.osedu.flows.work.Work;
import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReportPredicate;

public class RepeatFlowTest {

    @Test
    public void testRepeatUntil() {
        // given
        Work work = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_FALSE;
        RepeatFlow repeatFlow = RepeatFlow.Builder.aNewRepeatFlow()
                .repeat(work)
                .until(predicate)
                .build();

        // when
        repeatFlow.execute(workContext);

        // then
        Mockito.verify(work, Mockito.times(1)).execute(workContext);
    }

    @Test
    public void testRepeatTimes() {
        // given
        Work work = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        RepeatFlow repeatFlow = RepeatFlow.Builder.aNewRepeatFlow()
                .repeat(work)
                .times(3)
                .build();

        // when
        repeatFlow.execute(workContext);

        // then
        Mockito.verify(work, Mockito.times(3)).execute(workContext);
    }

}
