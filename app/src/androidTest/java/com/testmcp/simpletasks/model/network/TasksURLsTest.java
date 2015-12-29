package com.testmcp.simpletasks.model.network;

import junit.framework.TestCase;

import static com.testmcp.simpletasks.model.network.TasksURLs.getURL_task;

/**
 * Created by mario on 26/12/2015.
 */
public class TasksURLsTest extends TestCase {

    public void testGetURL_list_tasks() throws Exception {

    }

    public void testGetURL_task() throws Exception {
        getURL_task(1);
    }
}