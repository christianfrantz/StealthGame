package com.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.server.Packet.*;
import com.stealth.game.MainGame;
import com.stealth.game.Sprites.Player;

/**
 * Created by imont_000 on 11/18/2016.
 */

public class ServerNetworkListener extends Listener {

    private StealthServer sServer;
    private Server server;
    public ServerNetworkListener(StealthServer sServer, Server server) {
        this.server = server;
        this.sServer = sServer;
    }

    public void connected(Connection connection) {
        Log.info("[server] someone is connected");
    }

    public void disconnected(Connection connection) {
        Log.info("[server] someone is disconnected");
    }

    public void received(Connection connection, Object object) {

        StealthServer.PlayerConnection playerConnection = (StealthServer.PlayerConnection)connection;
        Player player = playerConnection.player;

        if (object instanceof Packet0LoginRequest) {
            Packet1LoginAnswer loginAnswer = new Packet1LoginAnswer();
            loginAnswer.accepted = true;
            connection.sendTCP(loginAnswer);
            sServer.LogIn(playerConnection, player);
        }

        if (object instanceof Packet2Message) {
            String message = ((Packet2Message) object).message;
            Log.info(message);
        }

        if(object instanceof Packet6MovePlayer){

            if(player == null) return;

            Packet6MovePlayer msg = (Packet6MovePlayer)object;

            player.body.getPosition().x = msg.x;
            player.body.getPosition().y = msg.y;

            Packet5UpdatePlayer update = new Packet5UpdatePlayer();
            update.name = "2";
            update.x = (int)player.body.getPosition().x;
            update.y = (int)player.body.getPosition().y;
            Log.info(msg.x + " " + msg.y);
            server.sendToAllTCP(update);
        }
    }
}