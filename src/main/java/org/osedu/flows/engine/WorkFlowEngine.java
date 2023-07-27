package org.osedu.flows.engine;

import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReport;
import org.osedu.flows.workflow.WorkFlow;

/**
 * Interface for a workflow engine.
 */
public interface WorkFlowEngine {

    /**
     * Run the given workflow and return its report.
     * @param workFlow to run
     * @param workContext context in which the workflow will be run
     * @return workflow report
     */
    WorkReport run(WorkFlow workFlow, WorkContext workContext);
}
