package com.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.stealth.game.Sprites.Player;

import java.io.IOException;
import java.util.HashSet;

public class StealthServer {
    private Server server;
    HashSet<Player> loggedIn = new HashSet<Player>();

    public StealthServer(){
        server = new Server(){
            protected Connection newConnection(){
                return new PlayerConnection();
            }
        };

        registerPackets();
        server.addListener(new ServerNetworkListener(this, server));
        try {
            server.bind(54555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();

    }

    private void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet.Packet2Message.class);
        kryo.register(Packet.Packet3AddPlayer.class);
        kryo.register(Packet.Packet4RemovePlayer.class);
        kryo.register(Packet.Packet5UpdatePlayer.class);
        kryo.register(Packet.Packet6MovePlayer.class);
    }

    public static void main(String[] args){
        new StealthServer();
        Log.set(Log.LEVEL_DEBUG);
    }

    public static class PlayerConnection extends Connection{
        public Player player;
    }

    public void LogIn(PlayerConnection c, Player player){
        c.player = player;

        for(Player other : loggedIn){
            Packet.Packet3AddPlayer addPlayer = new Packet.Packet3AddPlayer();
            addPlayer.player = other;
            c.sendTCP(addPlayer);
        }

        loggedIn.add(player);

        Packet.Packet3AddPlayer addPlayer = new Packet.Packet3AddPlayer();
        addPlayer.player = player;
        server.sendToAllTCP(addPlayer);
    }
}