package com.rosa.game.Tools;

import com.rosa.game.Application;

import java.util.Scanner;



class Debbugerxy {

    //Debug
    public static int debbugerx;
    public static int debbugery;


    public Debbugerxy() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter the number to be squared: ");
        debbugerx = (int) scan.nextDouble();
        debbugery = (int) scan.nextDouble();
        System.out.println(debbugerx + " " + debbugery);
    }
}
