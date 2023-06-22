package org.osedu.flow.work;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A predicate interface on work report.
 */
public interface WorkReportPredicate {

    /**
     * Apply the predicate on the given work report.
     *
     * @param workReport workReport on which the predicate should be applied
     * @return true if the predicate applies on the given report, false otherwise
     */
    boolean apply(WorkReport workReport);

    WorkReportPredicate ALWAYS_TRUE = workReport -> true;
    WorkReportPredicate ALWAYS_FALSE = workReport -> false;
    WorkReportPredicate COMPLETED = workReport -> workReport.getStatus().equals(WorkStatus.COMPLETED);
    WorkReportPredicate RUNNING = workReport -> workReport.getStatus().equals(WorkStatus.RUNNING);
    WorkReportPredicate FAILED = workReport -> workReport.getStatus().equals(WorkStatus.FAILED);

    /**
     * A predicate that returns true after a given number of items.
     */
    class TimePredicate implements WorkReportPredicate {

        private final int times;

        private final AtomicInteger counter = new AtomicInteger();

        public TimePredicate(int times) {
            this.times = times;
        }

        @Override
        public boolean apply(WorkReport workReport) {
            return counter.incrementAndGet() != times;
        }

        public static TimePredicate times(int times) {
            return new TimePredicate(times);
        }
    }


}
