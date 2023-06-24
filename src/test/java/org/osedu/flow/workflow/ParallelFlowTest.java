package org.osedu.flow.workflow;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.osedu.flow.work.Work;
import org.osedu.flow.work.WorkContext;
import org.osedu.flow.work.WorkReport;

import java.util.Arrays;
import java.util.List;

public class ParallelFlowTest {

    @Test
    public void testExecute() {

        //  configure
        Work work1 = Mockito.mock(Work.class);
        Work work2 = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        ParallelFlowExecutor parallelFlowExecutor = Mockito.mock(ParallelFlowExecutor.class);
        List<Work> works = Arrays.asList(work1, work2);
        ParallelFlow parallelFlow = new ParallelFlow("pf", works, parallelFlowExecutor);

        // when
        WorkReport report = parallelFlow.execute(workContext);

        // then
        Assertions.assertThat(report).isNotNull();
        Mockito.verify(parallelFlowExecutor).executeInParallel(works, workContext);
    }
}
