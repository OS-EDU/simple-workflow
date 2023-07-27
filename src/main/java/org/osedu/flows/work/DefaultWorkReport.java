package org.osedu.flows.work;

/**
 * Default implementation of {@link WorkReport}.
 */
public class DefaultWorkReport implements WorkReport {

    private final WorkStatus status;
    private final WorkContext context;
    private Throwable error;

    /**
     * Create a new {@link DefaultWorkReport}.
     *
     * @param status of work
     */
    public DefaultWorkReport(WorkStatus status, WorkContext context) {
        this.status = status;
        this.context = context;
    }

    /**
     * Create a new {@link DefaultWorkReport}.
     *
     * @param status of work
     * @param error if any
     */

    public DefaultWorkReport(WorkStatus status, WorkContext context, Throwable error) {
        this(status, context);
        this.error = error;
    }

    @Override
    public WorkStatus getStatus() {
        return status;
    }

    @Override
    public Throwable getError() {
        return error;
    }

    @Override
    public WorkContext getWorkContext() {
        return context;
    }

    @Override
    public String toString() {
        return "DefaultWorkReport{" +
                "status=" + status +
                ", context=" + context +
                ", error=" + error +
                '}';
    }
}
