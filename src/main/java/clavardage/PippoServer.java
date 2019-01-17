package clavardage;

import ro.pippo.core.Application;

import java.io.File;

public class PippoServer extends Application implements Runnable{

    @Override
    protected void onInit() {
        // send 'Hello World' as response
        GET("/", routeContext -> routeContext.send("Hello World"));

    }

    public void run(){
        System.out.println("Pippo server running");
        while(true);
    }

}

