package org.osedu.flows.workflow;

import org.osedu.flows.work.NoOpWork;
import org.osedu.flows.work.Work;
import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReport;
import org.osedu.flows.work.WorkReportPredicate;

import java.util.UUID;

/**
 * A conditional flow is defined by 4 artifacts:
 *
 * <ul>
 *     <li>The work to execute first</li>
 *     <li>A predicate for the conditional logic</li>
 *     <li>The work to execute if the predicate is satisfied</li>
 *     <li>The work to execute if the predicate is not satisfied (optional)</li>
 * </ul>
 *
 * @see ConditionalFlow.Builder;
 */
public class ConditionalFlow extends AbstractWorkFlow {

    private final Work initialWorkUnit, nextOnPredicateSuccess, nextOnPredicateFailure;
    private final WorkReportPredicate predicate;

    ConditionalFlow(String name, Work initialWorkUnit, Work nextOnPredicateSuccess, Work nextOnPredicateFailure, WorkReportPredicate predicate) {
        super(name);
        this.initialWorkUnit = initialWorkUnit;
        this.nextOnPredicateSuccess = nextOnPredicateSuccess;
        this.nextOnPredicateFailure = nextOnPredicateFailure;
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     */
    public WorkReport execute(WorkContext workContext) {
        WorkReport jobReport = initialWorkUnit.execute(workContext);
        if (predicate.apply(jobReport)) {
            jobReport = nextOnPredicateSuccess.execute(workContext);
        } else {
            if (nextOnPredicateFailure != null && !(nextOnPredicateFailure instanceof NoOpWork)) { // else is optional
                jobReport = nextOnPredicateFailure.execute(workContext);
            }
        }
        return jobReport;
    }

    public static class Builder {

        private Builder() {
            // force usage of static method aNewConditionalFlow
        }

        public static NameStep aNewConditionalFlow() {
            return new BuildSteps();
        }

        public interface NameStep extends ExecuteStep {
            ExecuteStep named(String name);
        }

        public interface ExecuteStep {
            WhenStep execute(Work initialWorkUnit);
        }

        public interface WhenStep {
            ThenStep when(WorkReportPredicate predicate);
        }

        public interface ThenStep {
            OtherwiseStep then(Work work);
        }

        public interface OtherwiseStep extends BuildStep {
            BuildStep otherwise(Work work);
        }

        public interface BuildStep {
            ConditionalFlow build();
        }

        private static class BuildSteps implements NameStep, ExecuteStep, WhenStep, ThenStep, OtherwiseStep, BuildStep {

            private String name;
            private Work initialWorkUnit, nextOnPredicateSuccess, nextOnPredicateFailure;
            private WorkReportPredicate predicate;

            BuildSteps() {
                this.name = UUID.randomUUID().toString();
                this.initialWorkUnit = new NoOpWork();
                this.nextOnPredicateSuccess = new NoOpWork();
                this.nextOnPredicateFailure = new NoOpWork();
                this.predicate = WorkReportPredicate.ALWAYS_FALSE;
            }

            @Override
            public ExecuteStep named(String name) {
                this.name = name;
                return this;
            }

            @Override
            public WhenStep execute(Work initialWorkUnit) {
                this.initialWorkUnit = initialWorkUnit;
                return this;
            }

            @Override
            public ThenStep when(WorkReportPredicate predicate) {
                this.predicate = predicate;
                return this;
            }

            @Override
            public OtherwiseStep then(Work work) {
                this.nextOnPredicateSuccess = work;
                return this;
            }

            @Override
            public BuildStep otherwise(Work work) {
                this.nextOnPredicateFailure = work;
                return this;
            }

            @Override
            public ConditionalFlow build() {
                return new ConditionalFlow(this.name, this.initialWorkUnit,
                        this.nextOnPredicateSuccess, this.nextOnPredicateFailure,
                        this.predicate);
            }
        }
    }
}
