package com.midas.app.services.temporal;

import com.midas.app.configurations.TemporalConfiguration;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowClient workflowClient;
    private final TemporalConfiguration temporalConfiguration;

    @Override
    public <T> T createWorkflowStub(Class<T> workflowInterface, @Nullable WorkflowOptions workflowOptions) {
        return workflowClient.newWorkflowStub(workflowInterface,
                workflowOptions != null ? workflowOptions : WorkflowOptions.newBuilder()
                        .setTaskQueue(temporalConfiguration.getTaskQueue())
                        .setWorkflowExecutionTimeout(Duration.ofSeconds(temporalConfiguration.getWorkflowTimeout()))
                        .build()
        );
    }

    @Override
    public <T> T findWorkflowStubById(Class<T> workflowInterface, String workflowId) {
        return workflowClient.newWorkflowStub(workflowInterface, workflowId);
    }
}
