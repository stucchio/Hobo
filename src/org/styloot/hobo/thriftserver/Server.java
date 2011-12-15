package org.styloot.hobo.thriftserver;


import java.io.IOException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import org.styloot.hobo.thriftserver.*;
import org.styloot.hobo.gen.*;

public class Server {
   private void start(){
      try {
         TServerSocket serverTransport = new TServerSocket(7911);
         Hobo.Processor processor = new Hobo.Processor(new HoboServerImpl());
         Factory protFactory = new TBinaryProtocol.Factory(true, true);
         TServer server = new TThreadPoolServer(processor, serverTransport, protFactory);
         System.out.println("Starting server on port 7911 ...");
         server.serve();
      } catch (TTransportException e) {
         e.printStackTrace();
      }
   }
   public static void main(String args[]){
      Server srv = new Server();
      srv.start();
   }
}


