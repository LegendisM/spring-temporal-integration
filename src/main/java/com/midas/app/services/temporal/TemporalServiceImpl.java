package com.midas.app.services.temporal;

import com.midas.app.activities.account.AccountActivityImpl;
import com.midas.app.activities.payment.PaymentCustomerActivityImpl;
import com.midas.app.workflows.account.CreateAccountWorkflow;
import com.midas.app.workflows.account.CreateAccountWorkflowImpl;
import com.midas.app.workflows.account.UpdateAccountWorkflow;
import com.midas.app.workflows.account.UpdateAccountWorkflowImpl;
import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import javax.annotation.Nullable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TemporalServiceImpl implements TemporalService {
  private final WorkflowClient workflowClient;
  private final AccountActivityImpl accountActivityImpl;
  private final PaymentCustomerActivityImpl paymentCustomerActivityImpl;

  public TemporalServiceImpl(
      WorkflowClient client,
      @Lazy AccountActivityImpl accountActivityImpl,
      @Lazy PaymentCustomerActivityImpl paymentCustomerActivityImpl) {
    workflowClient = client;
    this.accountActivityImpl = accountActivityImpl;
    this.paymentCustomerActivityImpl = paymentCustomerActivityImpl;
  }

  @PostConstruct
  public void initialize() {
    // Connect to Temporal service
    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    WorkflowClient client = WorkflowClient.newInstance(service);
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Create a worker that listens on the create account task queue
    Worker createAccountWorker = factory.newWorker(CreateAccountWorkflow.QUEUE_NAME);
    createAccountWorker.registerWorkflowImplementationTypes(CreateAccountWorkflowImpl.class);
    createAccountWorker.registerActivitiesImplementations(
        accountActivityImpl, paymentCustomerActivityImpl);

    // Create a worker that listens on the update account task queue
    Worker updateAccountWorker = factory.newWorker(UpdateAccountWorkflow.QUEUE_NAME);
    updateAccountWorker.registerWorkflowImplementationTypes(UpdateAccountWorkflowImpl.class);
    updateAccountWorker.registerActivitiesImplementations(
        accountActivityImpl, paymentCustomerActivityImpl);

    // Start all the workers created by this factory
    factory.start();
  }

  @Override
  public <T> T createWorkflowStub(Class<T> workflowInterface, WorkflowOptions workflowOption) {
    return workflowClient.newWorkflowStub(workflowInterface, workflowOption);
  }

  @Override
  public <T> T createWorkflowStub(
      Class<T> workflowInterface, String taskQueue, @Nullable String workflowId) {
    return workflowClient.newWorkflowStub(
        workflowInterface, createWorkflowOption(taskQueue, workflowId));
  }

  @Override
  public <T> T findWorkflowStubById(Class<T> workflowInterface, String workflowId) {
    return workflowClient.newWorkflowStub(workflowInterface, workflowId);
  }

  public static WorkflowOptions createWorkflowOption(
      String taskQueue, @Nullable String workflowId) {
    var option =
        WorkflowOptions.newBuilder()
            .setTaskQueue(taskQueue)
            .setWorkflowExecutionTimeout(Duration.ofMinutes(15))
            .setWorkflowRunTimeout(Duration.ofMinutes(15))
            .setWorkflowTaskTimeout(Duration.ofSeconds(10));

    if (workflowId != null) {
      option.setWorkflowId(workflowId);
    }

    return option.build();
  }

  public static ActivityOptions createActivityOptions() {
    return ActivityOptions.newBuilder()
        .setStartToCloseTimeout(Duration.ofMinutes(10))
        .setScheduleToCloseTimeout(Duration.ofMinutes(1))
        .build();
  }
}
