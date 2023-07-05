package org.osedu.flows.workflow;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.osedu.flows.work.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelFlowExecutorTest {

    @Test
    public void testExecute() {

        // configure
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        HelloWorldWork work1 = new HelloWorldWork("work1", WorkStatus.COMPLETED);
        HelloWorldWork work2 = new HelloWorldWork("work2", WorkStatus.FAILED);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        ParallelFlowExecutor parallelFlowExecutor = new ParallelFlowExecutor(executorService);

        // when
        List<WorkReport> workReports = parallelFlowExecutor.executeInParallel(Arrays.asList(work1, work2), workContext);
        executorService.shutdown();

        // then
        Assertions.assertThat(workReports).hasSize(2);
        Assertions.assertThat(work1.isExecuted()).isTrue();
        Assertions.assertThat(work2.isExecuted()).isTrue();

    }

    static class HelloWorldWork implements Work {

        private final String name;
        private final WorkStatus status;
        private boolean executed;

        HelloWorldWork(String name, WorkStatus status) {
            this.name = name;
            this.status = status;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public WorkReport execute(WorkContext workContext) {
            executed = true;
            return new DefaultWorkReport(status, workContext);
        }

        public boolean isExecuted() {
            return executed;
        }
    }
}
