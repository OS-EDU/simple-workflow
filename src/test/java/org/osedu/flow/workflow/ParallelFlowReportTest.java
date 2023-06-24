package org.osedu.flow.workflow;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.osedu.flow.work.DefaultWorkReport;
import org.osedu.flow.work.WorkContext;
import org.osedu.flow.work.WorkStatus;

public class ParallelFlowReportTest {

    private Exception exception;
    private ParallelFlowReport parallelFlowReport;

    @Before
    public void setup() {
        exception = new Exception("test exception");
        WorkContext workContext = new WorkContext();
        parallelFlowReport = new ParallelFlowReport();
        parallelFlowReport.add(new DefaultWorkReport(WorkStatus.FAILED, workContext, exception));
        parallelFlowReport.add(new DefaultWorkReport(WorkStatus.COMPLETED, workContext));
    }

    @Test
    public void testGetStatus() {
        Assertions.assertThat(parallelFlowReport.getStatus()).isEqualTo(WorkStatus.FAILED);
    }

    @Test
    public void testGetError() {
        Assertions.assertThat(parallelFlowReport.getError()).isEqualTo(exception);
    }

    @Test
    public void testGetReports() {
        Assertions.assertThat(parallelFlowReport.getReports()).hasSize(2);
    }
}
