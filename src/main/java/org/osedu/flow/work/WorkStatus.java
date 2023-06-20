package org.osedu.flow.work;

/**
 * Work execution status enumeration.
 */
public enum WorkStatus {

    /**
     * the unit of work has failed.
     */
    FAILED,

    /**
     * the unit of work is running now.
     */
    RUNNING,

    /**
     * the unit of work has completed successfully.
     */
    COMPLETED
}
