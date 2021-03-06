package no.hiof.itf23019.parallel_file_search_executor.parallel.executor;

import java.io.File;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import no.hiof.itf23019.parallel_file_search_executor.Result;

public class ParallelGroupFileSearchExecutor {

    public static void searchFiles(File file, String fileName, Result parallelResult) {

        ConcurrentLinkedQueue<File> directories = new ConcurrentLinkedQueue<>();
        File[] contents = file.listFiles();

        for (File content : contents) {
            if (content.isDirectory()) {
                directories.add(content);
            }
        }

        // Create the executor
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // List to store the Future objects that control the execution of the task
        List<Future<Boolean>> futureTaskList = new ArrayList<>();

        //Number of task to create
        int numTasks = Runtime.getRuntime().availableProcessors();

        //Create the task and submit them to the executor
        for (int i = 0; i < numTasks; i++) {
            ParallelGroupFileTaskExecutor task = new ParallelGroupFileTaskExecutor(fileName, parallelResult,
                    directories);
            Future<Boolean> futureTask = executor.submit(task);
            futureTaskList.add(futureTask);
        }

        boolean finish = false;
        int numFinished = 0;
        while (!finish) {


            //Iterate over the task in futureTaskList and check if one of the tasks finds the file
            for (Future futureTask : futureTaskList) {
                // or all tasks are done
                if (futureTask.isDone() || numFinished == futureTaskList.size()) {
                    finish = true;
                    System.out.println("completed tasks: " + numFinished);
                    break;

                } else {
                    numFinished++;
                }
                System.out.printf("Main: Number of Completed Tasks: %d\n", executor.getCompletedTaskCount());
                //then break the loop.
                break;
            }

        }


        // Cancel the remaining task.
        if (executor.getCompletedTaskCount() != numTasks) {
            //TODO: cancel all the task in futureTaskList
            futureTaskList.get(1).cancel(true);

        }

        executor.shutdown();

    }
}

