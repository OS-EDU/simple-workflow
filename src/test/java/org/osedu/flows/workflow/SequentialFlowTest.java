package org.osedu.flows.workflow;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.osedu.flows.work.Work;
import org.osedu.flows.work.WorkContext;

import java.util.Arrays;
import java.util.List;

public class SequentialFlowTest {
    @Test
    public void testExecute() {
        // given
        Work work1 = Mockito.mock(Work.class);
        Work work2 = Mockito.mock(Work.class);
        Work work3 = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        SequentialFlow sequentialFlow = SequentialFlow.Builder.aNewSequentialFlow()
                .named("testFlow")
                .execute(work1)
                .then(work2)
                .then(work3)
                .build();

        // when
        sequentialFlow.execute(workContext);

        // then
        InOrder inOrder = Mockito.inOrder(work1, work2, work3);
        inOrder.verify(work1, Mockito.times(1)).execute(workContext);
        inOrder.verify(work2, Mockito.times(1)).execute(workContext);
        inOrder.verify(work3, Mockito.times(1)).execute(workContext);
    }

    @Test
    public void testPassingMultipleWorkUnitsAtOnce() {
        // given
        Work work1 = Mockito.mock(Work.class);
        Work work2 = Mockito.mock(Work.class);
        Work work3 = Mockito.mock(Work.class);
        Work work4 = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        List<Work> initialWorkUnits = Arrays.asList(work1, work2);
        List<Work> nextWorkUnits = Arrays.asList(work3, work4);
        SequentialFlow sequentialFlow = SequentialFlow.Builder.aNewSequentialFlow()
                .named("testFlow")
                .execute(initialWorkUnits)
                .then(nextWorkUnits)
                .build();

        // when
        sequentialFlow.execute(workContext);

        // then
        InOrder inOrder = Mockito.inOrder(work1, work2, work3, work4);
        inOrder.verify(work1, Mockito.times(1)).execute(workContext);
        inOrder.verify(work2, Mockito.times(1)).execute(workContext);
        inOrder.verify(work3, Mockito.times(1)).execute(workContext);
        inOrder.verify(work4, Mockito.times(1)).execute(workContext);
    }

}
