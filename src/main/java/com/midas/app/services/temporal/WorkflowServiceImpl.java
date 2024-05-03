package com.midas.app.services.temporal;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowClient workflowClient;

    @Override
    public <T> T createWorkflowStub(Class<T> workflowInterface, @Nullable WorkflowOptions workflowOptions) {
        return workflowClient.newWorkflowStub(workflowInterface,
                workflowOptions != null ? workflowOptions : WorkflowOptions.getDefaultInstance()
        );
    }
}
