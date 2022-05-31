//package com.otto15;
//
//import com.otto15.client.ConnectionHandler;
//import com.otto15.client.listeners.ClientNetworkListener;
//import com.otto15.common.commands.HelpCommand;
//import com.otto15.common.controllers.CommandManager;
//import com.otto15.common.entities.User;
//import com.otto15.common.network.Request;
//import com.otto15.common.network.Response;
//import com.otto15.common.state.PerformanceState;
//
//import java.io.IOException;
//
///**
// * Class for client-server testing
// */
//public class Test implements Runnable {
//
//    private ClientNetworkListener clientNetworkListener;
//    private CommandManager commandManager;
//    private int count = 0;
//
//    public Test(ClientNetworkListener clientNetworkListener, CommandManager commandManager) {
//        this.commandManager = commandManager;
//        this.clientNetworkListener = clientNetworkListener;
//    }
//
//    @Override
//    public void run() {
//
//        for (int i = 0; i < 100; ++i) {
//            try {
//                if (test() < 0) {
//                    System.out.println(count);
//                    break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//    public int test() throws IOException {
//        count++;
//        Response response = clientNetworkListener.listen(new Request(new HelpCommand(commandManager), new Object[]{new User()}));
//        if (response == null) {
//            return -1;
//        }
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//
//        }
//        return 1;
//    }
//
//    public static void main(String[] args) throws IOException {
//        PerformanceState performanceState = PerformanceState.getInstance();
//        for (int i = 0; i < 1000; ++i) {
//            ConnectionHandler connectionHandler = new ConnectionHandler(performanceState);
//            ClientNetworkListener clientNetworkListener = new ClientNetworkListener(connectionHandler);
//            CommandManager commandManager = new CommandManager(clientNetworkListener);
//            connectionHandler.openConnection("localhost", 1);
//            new Thread(new Test(clientNetworkListener, commandManager)) {
//            }.start();
//        }
//    }
//
//}
