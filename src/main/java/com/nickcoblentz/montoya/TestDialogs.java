package com.nickcoblentz.montoya;


import java.io.File;


public class TestDialogs {
    public static void main(String[] args)
    {
        /*UiBooster booster = new UiBooster();

        AtomicReference<String> opinion= new AtomicReference<>("");
        Thread t = Thread.ofVirtual().start(() -> {
            opinion.set(booster.showTextInputDialog("What do you think?"));
            System.out.println(opinion.get());
        });

        //Example of custom form
        //https://github.com/Milchreis/UiBooster/blob/master/src/test/java/de/milchreis/uibooster/DataBindingTest.java#L44
        //https://github.com/Milchreis/UiBooster/blob/master/src/test/java/de/milchreis/uibooster/FormBuilderTest.java#L115
        
        System.out.println("After!");

        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */


        //File[] result = (new UIHelper()).showFileMultiSelectionDialog();







        /*CompletableFuture<String> future = new CompletableFuture<>();

        VirtualThread virtualThread = VirtualThread.of(() -> {
            // Perform some asynchronous operation
            String result = "Hello, World!";
            future.complete(result);
        });

        virtualThread.start();

        future.thenApply(result -> {
            // Transform the result
            return result.toUpperCase();
        }).join();*/
    }
}
