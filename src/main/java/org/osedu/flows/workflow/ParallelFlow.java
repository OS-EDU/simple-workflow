package org.osedu.flows.workflow;

import org.osedu.flows.work.Work;
import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * A parallel flow executes a set of work units in parallel. A {@link ParallelFlow}
 * requires a {@link java.util.concurrent.ExecutorService} to execute work units in parallel using multiple
 * threads.
 * <strong>It is the responsibility of the caller to manage the lifecycle of the
 * executor service.</strong>
 * <p>
 * The status of a parallel flow execution is defined as:
 *
 * <ul>
 *     <li>{@link org.osedu.flows.work.WorkStatus#COMPLETED}: If all work units have successfully completed</li>
 *     <li>{@link org.osedu.flows.work.WorkStatus#FAILED}: If one of the work units has failed</li>
 * </ul>
 */
public class ParallelFlow extends AbstractWorkFlow {

    private final List<Work> workUnits = new ArrayList<>();
    private final ParallelFlowExecutor workExecutor;

    ParallelFlow(String name, List<Work> workUnits, ParallelFlowExecutor parallelFlowExecutor) {
        super(name);
        this.workUnits.addAll(workUnits);
        this.workExecutor = parallelFlowExecutor;
    }

    /**
     * {@inheritDoc}
     */
    public ParallelFlowReport execute(WorkContext workContext) {
        ParallelFlowReport workFlowReport = new ParallelFlowReport();
        List<WorkReport> workReports = workExecutor.executeInParallel(workUnits, workContext);
        workFlowReport.addAll(workReports);
        return workFlowReport;
    }

    public static class Builder {

        private Builder() {
            // force usage of method aNewParallelFlow
        }

        public static NameStep aNewParallelFlow() {
            return new BuildSteps();
        }

        public interface NameStep extends ExecuteStep {
            ExecuteStep named(String name);
        }

        public interface ExecuteStep {
            WithStep execute(Work... workUnits);
        }

        public interface WithStep {
            /**
             * A {@link ParallelFlow} requires an {@link ExecutorService} to
             * execute work units in parallel using multiple threads.
             *
             * <strong>It is the responsibility of the caller to manage the lifecycle
             * of the executor service.</strong>
             *
             * @param executorService to use to execute work units in parallel
             * @return the builder instance
             */
            BuildStep with(ExecutorService executorService);
        }

        public interface BuildStep {
            ParallelFlow build();
        }

        private static class BuildSteps implements NameStep, ExecuteStep, WithStep, BuildStep {

            private String name;
            private final List<Work> works;
            private ExecutorService executorService;

            public BuildSteps() {
                this.name = UUID.randomUUID().toString();
                this.works = new ArrayList<>();
            }

            @Override
            public ExecuteStep named(String name) {
                this.name = name;
                return this;
            }

            @Override
            public WithStep execute(Work... workUnits) {
                this.works.addAll(Arrays.asList(workUnits));
                return this;
            }

            @Override
            public BuildStep with(ExecutorService executorService) {
                this.executorService = executorService;
                return this;
            }

            @Override
            public ParallelFlow build() {
                return new ParallelFlow(
                        this.name, this.works,
                        new ParallelFlowExecutor(this.executorService));
            }
        }

    }
}
