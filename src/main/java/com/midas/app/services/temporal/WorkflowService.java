package com.midas.app.services.temporal;

import io.temporal.client.WorkflowOptions;

import javax.annotation.Nullable;

public interface WorkflowService {
    /**
     * createWorkflowStub create new workflow with given workflow interface and workflow options
     *
     * @param workflowInterface is the interface of your workflow
     * @param workflowOptions   is for define your custom workflow options\
     * @return T
     */
    public <T> T createWorkflowStub(Class<T> workflowInterface, @Nullable WorkflowOptions workflowOptions);

    /**
     * findWorkflowStubById find workflow existed instance by workflowId
     *
     * @param workflowInterface is the interface of your workflow
     * @param workflowId        is the existed workflowId
     * @return T
     */
    public <T> T findWorkflowStubById(Class<T> workflowInterface, String workflowId);
}
