package org.osedu.flows.engine;

import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReport;
import org.osedu.flows.workflow.WorkFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WorkFlowEngineImpl implements WorkFlowEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowEngineImpl.class);

    @Override
    public WorkReport run(WorkFlow workFlow, WorkContext workContext) {
        LOGGER.info("Running workflow ''{}''", workFlow.getName());
        return workFlow.execute(workContext);
    }
}
