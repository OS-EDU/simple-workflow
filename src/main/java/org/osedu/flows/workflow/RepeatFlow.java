package org.osedu.flows.workflow;

import org.osedu.flows.work.NoOpWork;
import org.osedu.flows.work.Work;
import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReportPredicate;
import org.osedu.flows.work.WorkReport;

import java.util.UUID;

/**
 * A repeat flow executes a work repeatedly until its report satisfies a given predicate.
 */
public class RepeatFlow extends AbstractWorkFlow {

    private final Work work;
    private final WorkReportPredicate predicate;

    RepeatFlow(String name, Work work, WorkReportPredicate predicate) {
        super(name);
        this.work = work;
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     */
    public WorkReport execute(WorkContext workContext) {
        WorkReport workReport;
        do {
            workReport = work.execute(workContext);
        } while (predicate.apply(workReport));
        return workReport;
    }

    public static class Builder {

        private Builder() {
            // force usage of static method aNewRepeatFlow
        }

        public static NameStep aNewRepeatFlow() {
            return new BuildSteps();
        }

        public interface NameStep extends RepeatStep {
            RepeatStep named(String name);
        }

        public interface RepeatStep {
            UntilStep repeat(Work work);
        }

        public interface UntilStep {
            BuildStep until(WorkReportPredicate predicate);

            BuildStep times(int times);
        }

        public interface BuildStep {
            RepeatFlow build();
        }

        private static class BuildSteps implements NameStep, RepeatStep, UntilStep, BuildStep {

            private String name;
            private Work work;
            private WorkReportPredicate predicate;

            BuildSteps() {
                this.name = UUID.randomUUID().toString();
                this.work = new NoOpWork();
                this.predicate = WorkReportPredicate.ALWAYS_FALSE;
            }

            @Override
            public RepeatStep named(String name) {
                this.name = name;
                return this;
            }

            @Override
            public UntilStep repeat(Work work) {
                this.work = work;
                return this;
            }

            @Override
            public BuildStep until(WorkReportPredicate predicate) {
                this.predicate = predicate;
                return this;
            }

            @Override
            public BuildStep times(int times) {
                until(WorkReportPredicate.TimesPredicate.times(times));
                return this;
            }

            @Override
            public RepeatFlow build() {
                return new RepeatFlow(name, work, predicate);
            }
        }

    }
}
