package org.osedu.flow.work;

import java.util.UUID;

/**
 * No operation work
 */
public class NoOpWork implements Work {

    @Override
    public String getName() {
        return UUID.randomUUID().toString();
    }

    @Override
    public WorkReport execute(WorkContext workContext) {
        return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
    }
}
