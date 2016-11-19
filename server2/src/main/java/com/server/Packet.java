package com.server;

import com.badlogic.gdx.math.Vector2;
import com.stealth.game.Sprites.Player;

/**
 * Created by imont_000 on 11/18/2016.
 */

public class Packet {
    public static class Packet0LoginRequest{}
    public static class Packet1LoginAnswer{boolean accepted;}
    public static class Packet2Message{String message;}
    public static class Packet3AddPlayer{Player player;}
    public static class Packet4RemovePlayer{}
    public static class Packet5UpdatePlayer{String name; int x, y;}
    public static class Packet6MovePlayer{int x, y;}
}
