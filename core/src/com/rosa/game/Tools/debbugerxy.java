package com.rosa.game.Tools;

import com.rosa.game.Application;

import java.util.Scanner;

public class Debbugerxy {


    public Debbugerxy() {
        Scanner scan = new Scanner(System.in);
//        for (int i = 0; i < 233; i++) {
            System.out.println("\nEnter the number to be squared: ");
            Application.debbugerx = (int) scan.nextDouble();
            Application.debbugery = (int) scan.nextDouble();

            System.out.println(Application.debbugerx + " " + Application.debbugery);
//        }
    }


}
