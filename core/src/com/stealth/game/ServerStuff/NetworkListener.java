package com.stealth.game.ServerStuff;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.stealth.game.ServerStuff.Packet.*;
/**
 * Created by imont_000 on 11/17/2016.
 */

public class NetworkListener extends Listener{

    private Client client;

    public NetworkListener(){}

    @Override
    public void connected(Connection connection) {
        Log.info("[client] client connected");
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("[client] client disconnected");
    }

    @Override
    public void received(Connection connection, Object o) {
        if(o instanceof Packet1LoginAnswer){
            boolean answer = ((Packet1LoginAnswer)o).accepted;

            if(answer){
                Log.info("client connected successfully");
            }
        }else{
            connection.close();
        }
    }

    public void init(Client client){
        this.client = client;
    }
}
