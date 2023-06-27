package org.osedu.flow.engine;

import org.junit.Test;
import org.mockito.Mockito;
import org.osedu.flow.work.WorkContext;
import org.osedu.flow.workflow.WorkFlow;

public class WorkFlowEngineImplTest {

    private final WorkFlowEngine workFlowEngine = new WorkFlowEngineImpl();

    @Test
    public void run() {

        // given
        WorkFlow workFlow = Mockito.mock(WorkFlow.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);

        // when
        workFlowEngine.run(workFlow, workContext);

        // then
        Mockito.verify(workFlow).execute(workContext);
    }

}