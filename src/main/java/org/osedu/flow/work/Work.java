package org.osedu.flow.work;

import java.util.UUID;

/**
 * This interface represents a unit of work. Implementations of this interface must:
 *
 * <ul>
 *     <li>catch any checked or unchecked exceptions and return a {@link WorkReport}
 *     instance with a status of {@link WorkStatus#FAILED} and a reference to the exception</li>
 *     <li>make sure the work is finished in a finite amount of time</li>
 * </ul>
 *
 * Work name must be unique within a workflow definition.
 *
 */
public interface Work {

    /**
     * The name of the unit of work. The name must be unique within a workflow definition.
     *
     * @return name of the unit of work.
     */
    default String getName() {
        return UUID.randomUUID().toString();
    }

    /**
     * Execute the unit of work and return its report. Implementations are required
     * to catch any checked or unchecked exceptions and return a {@link WorkReport} instance
     * with a status of {@link WorkStatus#FAILED} and a reference to the exception.
     *
     * @param workContext context in which this unit of work is being executed
     * @return the execution report
     */
    WorkReport execute(WorkContext workContext);
}
