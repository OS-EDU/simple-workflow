package org.osedu.flows.workflow;

import org.junit.Test;
import org.mockito.Mockito;
import org.osedu.flows.work.Work;
import org.osedu.flows.work.WorkContext;
import org.osedu.flows.work.WorkReportPredicate;

public class ConditionalFlowTest {

    @Test
    public void callOnPredicateSuccess() {
        // given
        Work toExecute = Mockito.mock(Work.class);
        Work nextOnPredicateSuccess = Mockito.mock(Work.class);
        Work nextOnPredicateFailure = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_TRUE;
        ConditionalFlow conditionalFlow = ConditionalFlow.Builder.aNewConditionalFlow()
                .named("testFlow")
                .execute(toExecute)
                .when(predicate)
                .then(nextOnPredicateSuccess)
                .otherwise(nextOnPredicateFailure)
                .build();

        // when
        conditionalFlow.execute(workContext);

        // then
        Mockito.verify(toExecute, Mockito.times(1)).execute(workContext);
        Mockito.verify(nextOnPredicateSuccess, Mockito.times(1)).execute(workContext);
        Mockito.verify(nextOnPredicateFailure, Mockito.never()).execute(workContext);
    }

    @Test
    public void callOnPredicateFailure() {
        // given
        Work toExecute = Mockito.mock(Work.class);
        Work nextOnPredicateSuccess = Mockito.mock(Work.class);
        Work nextOnPredicateFailure = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_FALSE;
        ConditionalFlow conditionalFlow = ConditionalFlow.Builder.aNewConditionalFlow()
                .named("anotherTestFlow")
                .execute(toExecute)
                .when(predicate)
                .then(nextOnPredicateSuccess)
                .otherwise(nextOnPredicateFailure)
                .build();

        // when
        conditionalFlow.execute(workContext);

        // then
        Mockito.verify(toExecute, Mockito.times(1)).execute(workContext);
        Mockito.verify(nextOnPredicateFailure, Mockito.times(1)).execute(workContext);
        Mockito.verify(nextOnPredicateSuccess, Mockito.never()).execute(workContext);
    }
}
