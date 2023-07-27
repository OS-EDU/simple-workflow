package org.osedu.flows.work;

/**
 * Execution report of a unit work.
 */
public interface WorkReport {

    /**
     * Get work execution status.
     *
     * @return execution status
     */
    WorkStatus getStatus();

    /**
     * Get error if any. Might be {@code null}, but usually not null when
     * the status is {@link WorkStatus#FAILED}. Typically, the exception includes
     * the exit code that might be used to drive the flow execution accordingly.
     *
     * @return error
     */
    Throwable getError();

    /**
     * Get the last work context of the flow.
     *
     * @return last work context of the flow
     */
    WorkContext getWorkContext();
}
