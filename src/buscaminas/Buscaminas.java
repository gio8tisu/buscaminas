/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Buscaminas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int X, Y, N;
        if (args.length == 0) {
            X = 10;
            Y = 5;
            N = 10;
        } else {
            X = Integer.parseInt(args[0]);
            Y = Integer.parseInt(args[1]);
            N = Integer.parseInt(args[2]);
        }

        Grid grid = new Grid(X, Y, N);
        try (Scanner s = new Scanner(System.in).useDelimiter("\\s+")) {
            boolean continuar = true;
            while (continuar && grid.getContActivadas() < (X * Y - N)) {
                System.out.println(grid);
                System.out.println("accion x y:");
                try {
                    String str = s.next();
                    if (str.equalsIgnoreCase("b") || str.equalsIgnoreCase("bandera")) {
                        grid.toggleBandera(s.nextInt(), s.nextInt());
                    } else if (str.equalsIgnoreCase("a") || str.equalsIgnoreCase("activar")) {
                        if (!grid.activarCelda(s.nextInt(), s.nextInt())) {
                            continuar = false;
                        }
                    } else if (str.equalsIgnoreCase("f") || str.equalsIgnoreCase("fin")) {
                        continuar = false;
                    } else {
                        while (s.hasNext()) {
                            s.next();
                        }
                    }
                } catch (ActivarBanderaException | IndicesFueraDeRangoException e) {
                    System.out.print(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Sigue las normas");
                }
            }
            s.close();
            System.out.println("Game Over");
            System.out.println(grid);
            if (grid.getContActivadas() == (X * Y - N)) {
                System.out.println("Has ganado");
            } else {
                System.out.println("Has perdido");
            }
        }
    }

}
