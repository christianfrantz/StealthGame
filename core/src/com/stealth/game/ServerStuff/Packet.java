package com.stealth.game.ServerStuff;

/**
 * Created by imont_000 on 11/17/2016.
 */

public class Packet {
    public static class Packet0LoginRequest{}
    public static class Packet1LoginAnswer{boolean accepted;}
    public static class Packet2Message{String message;}
}
