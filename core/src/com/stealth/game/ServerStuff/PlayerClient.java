package com.stealth.game.ServerStuff;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.stealth.game.Sprites.Player;

import java.io.IOException;

/**
 * Created by imont_000 on 11/17/2016.
 */

public class PlayerClient {
    public Client client;
    private String ipAddress = "127.0.0.1";
    public static Player player;
    public static boolean hasConnection = false;

    public PlayerClient(){
        client = new Client();
        register();

        NetworkListener networkListener = new NetworkListener();
        networkListener.init(client);
        client.addListener(networkListener);

        new Thread(client).start();

        try{
            client.connect(10000, ipAddress, 54555);
        }catch(IOException e){
            e.printStackTrace();
            client.stop();
        }

        hasConnection = true;
    }

    private void register(){
        Kryo kryo = client.getKryo();
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet.Packet2Message.class);
        kryo.register(Packet.Packet3AddPlayer.class);
        kryo.register(Packet.Packet4RemovePlayer.class);
        kryo.register(Packet.Packet5UpdatePlayer.class);
        kryo.register(Packet.Packet6MovePlayer.class);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }
}
