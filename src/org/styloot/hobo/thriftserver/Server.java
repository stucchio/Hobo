package org.styloot.hobo.thriftserver;

import java.io.IOException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.*;
import java.io.*;

import org.styloot.hobo.thriftserver.*;
import org.styloot.hobo.gen.*;
import org.styloot.hobo.*;


public class Server {
    public Server(HoboIndex i, int pt, int ps) {
	index = i;
	port = pt;
	pageSize = ps;
    }

    private HoboIndex index;
    private int port;
    private int pageSize;

    private void start(){
        try {
            TServerSocket serverTransport = new TServerSocket(port);
            Hobo.Processor processor = new Hobo.Processor(new HoboServerImpl(index, pageSize));
            Factory protFactory = new TBinaryProtocol.Factory(true, true);
            TServer server = new TThreadPoolServer(processor, serverTransport, protFactory);
            System.out.println("Starting server on port " + port);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
	Collection<Item> items = itemsFromInputStream(new BufferedReader(new FileReader(args[0])));
        HoboIndex index = new HoboIndex(items);

        Server srv = new Server(index, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        srv.start();
    }

    private static Collection<Item> itemsFromInputStream(BufferedReader reader) throws IOException {
        String line;
        Vector<Item> result = new Vector<Item>();
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(";");
            String[] features = tokens[3].split(",");
            result.add(new Item(tokens[0], tokens[1], features, Integer.parseInt(tokens[2])));
        }
        return result;
    }
}


