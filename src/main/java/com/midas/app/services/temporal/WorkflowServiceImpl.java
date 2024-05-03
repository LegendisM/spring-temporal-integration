package com.midas.app.services.temporal;

import com.midas.app.configurations.TemporalConfiguration;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowClient workflowClient;
    private final TemporalConfiguration temporalConfiguration;

    @Override
    public <T> T createWorkflowStub(Class<T> workflowInterface, WorkflowOptions workflowOption) {
        return workflowClient.newWorkflowStub(workflowInterface, workflowOption);
    }

    @Override
    public <T> T createWorkflowStub(Class<T> workflowInterface, String taskQueue, @Nullable String workflowId) {
        return workflowClient.newWorkflowStub(workflowInterface, this.createWorkflowOption(taskQueue, workflowId));
    }

    @Override
    public <T> T findWorkflowStubById(Class<T> workflowInterface, String workflowId) {
        return workflowClient.newWorkflowStub(workflowInterface, workflowId);
    }

    public WorkflowOptions createWorkflowOption(String taskQueue, @Nullable String workflowId) {
        val option = WorkflowOptions.newBuilder()
                .setTaskQueue(taskQueue)
                .setWorkflowExecutionTimeout(Duration.ofSeconds(temporalConfiguration.getWorkflowTimeout()));

        if (workflowId != null) {
            option.setWorkflowId(workflowId);
        }

        return option.build();
    }
}
