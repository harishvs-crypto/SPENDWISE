package com.spendwise.listener;

import com.spendwise.thread.BudgetAlertThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppStartListener implements ServletContextListener {
    private BudgetAlertThread alertThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        alertThread = new BudgetAlertThread();
        alertThread.start();
        System.out.println("SpendWise: BudgetAlertThread started successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (alertThread != null) {
            alertThread.terminate();
            alertThread.interrupt();
            System.out.println("SpendWise: BudgetAlertThread stopped.");
        }
    }
}
