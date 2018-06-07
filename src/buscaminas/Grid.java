/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.util.Random;

/**
 *
 * @author user
 */
public class Grid {

    private static final int MAX_ACTIVADAS = 5;
    private int contActivadas;
    private final int tamanoX, tamanoY, numMinas;
    private final Celda[][] celdas;

    public Grid(int tamanoX, int tamanoY, int numMinas) {
        this.contActivadas = 0;
        this.numMinas = numMinas;
        this.tamanoX = tamanoX;
        this.tamanoY = tamanoY;
        this.celdas = new Celda[tamanoY][tamanoX];
        int[][] posiciones = calculoPosicionesMinas();
        for (int i = 0; i < tamanoY; i++) { //ini celdas
            for (int j = 0; j < tamanoX; j++) {
                this.celdas[i][j] = new Celda(isOnPos(j, i, posiciones));
            }
        }
        for (int i = 0; i < tamanoY; i++) { //calculo minas cerca
            for (int j = 0; j < tamanoX; j++) {
                if (!this.celdas[i][j].isBomba()) {
                    int m = 0;
                    if (i > 0) {
                        if (celdas[i - 1][j].isBomba()) {
                            m++;
                        }
                        if (j < tamanoX - 1) {
                            if (celdas[i - 1][j + 1].isBomba()) {
                                m++;
                            }
                        }
                        if (j > 0) {
                            if (celdas[i - 1][j - 1].isBomba()) {
                                m++;
                            }
                        }
                    }
                    if (celdas[i][j].isBomba()) {
                        m++;
                    }
                    if (j < tamanoX - 1) {
                        if (celdas[i][j + 1].isBomba()) {
                            m++;
                        }
                    }
                    if (j > 0) {
                        if (celdas[i][j - 1].isBomba()) {
                            m++;
                        }
                    }
                    if (i < tamanoY - 1) {
                        if (celdas[i + 1][j].isBomba()) {
                            m++;
                        }
                        if (j < tamanoX - 1) {
                            if (celdas[i + 1][j + 1].isBomba()) {
                                m++;
                            }
                        }
                        if (j > 0) {
                            if (celdas[i + 1][j - 1].isBomba()) {
                                m++;
                            }
                        }
                    }
                    this.celdas[i][j].setMinasCerca(m);
                }
            }
        }
    }

    public int getContActivadas() {
        return contActivadas;
    }

    public boolean activarCelda(int x, int y) throws IndicesFueraDeRangoException, ActivarBanderaException { //false-->mina
        if (!(x >= 0 && x < tamanoX && y >= 0 && y < tamanoY)) {
            throw new IndicesFueraDeRangoException("Intenta activar con valores entre 0<=x<" + this.tamanoX + ", 0<=y<" + this.tamanoY + "\n");
        }
        if (celdas[y][x].isBandera()) {
            throw new ActivarBanderaException("");
        }
        if (celdas[y][x].isActiva()) {
            return true;
        }
        celdas[y][x].setActiva();
        if (celdas[y][x].isBomba()) {
            return false;
        }
        this.contActivadas++;
        if (celdas[y][x].getMinasCercaInt() == 0) {
            if (x > 0) { //O
                if (!celdas[y][x - 1].isActiva()) {
                    activarCelda(x - 1, y);
                }
                if (y < tamanoY - 1 && !celdas[y + 1][x - 1].isActiva()) { //SO
                    activarCelda(x - 1, y + 1);
                }
                if (y > 0 && !celdas[y - 1][x - 1].isActiva()) { //NO
                    activarCelda(x - 1, y - 1);
                }
            }
            if (y < tamanoY - 1 && !celdas[y + 1][x].isActiva()) { //S
                activarCelda(x, y + 1);
            }
            if (y > 0 && !celdas[y - 1][x].isActiva()) { //N
                activarCelda(x, y - 1);
            }
            if (x < tamanoX - 1) { //E
                if (!celdas[y][x + 1].isActiva()) {
                    activarCelda(x + 1, y);
                }
                if (y < tamanoY - 1 && !celdas[y + 1][x + 1].isActiva()) { //SE
                    activarCelda(x + 1, y + 1);
                }
                if (y > 0 && !celdas[y - 1][x + 1].isActiva()) { //NE
                    activarCelda(x + 1, y - 1);
                }
            }
        }

        return true;
    }

    public void toggleBandera(int x, int y) throws IndicesFueraDeRangoException {
        if (!(x >= 0 && x < tamanoX && y >= 0 && y < tamanoY)) {
            throw new IndicesFueraDeRangoException("Intenta abanderar con valores entre 0<=x<" + this.tamanoX + ", 0<=y<" + this.tamanoY + "\n");
        }
        celdas[y][x].setBandera(!celdas[y][x].isBandera());
    }

    @Override
    public String toString() {
        String grid = "    ";
        for (int i = 0; i < this.tamanoX; i++) {
            grid = grid + "(" + i + ") ";
        }
        grid = grid + "\n";
        for (int y = 0; y <= 2 * this.tamanoY; y++) {
            grid = grid + ((y % 2 == 0) ? "    " : ("(" + y / 2 + ")" + "|")); //print #fila y lineas vert
            for (int x = 0; x < this.tamanoX; x++) {
                if (y % 2 == 0) {
                    grid = grid + "- - ";
                } else if (celdas[y / 2][x].isBandera()) {
                    grid = grid + " P |";
                } else {
                    String a = (celdas[y / 2][x].isActiva()) ? celdas[y / 2][x].getMinasCercaString() : "   ";
                    grid = grid + a + "|";
                }
            }
            if (y != 2 * this.tamanoY) {
                grid = grid + "\n";
            }
        }
        return grid;
    }

    private int[][] calculoPosicionesMinas() {
        Random r = new Random();
        int[][] posiciones = new int[numMinas][2]; //fila para cada mina, en cada fila vector con posicion Y y X de la mina
        for (int i = 0; i < numMinas; i++) {
            posiciones[i][0] = r.nextInt(tamanoY);
            posiciones[i][1] = r.nextInt(tamanoX);
        }
        return posiciones;
    }

    private boolean isOnPos(int x, int y, int[][] posiciones) { //return true si hay bomba en (y,x)
        for (int i = 0; i < numMinas; i++) {
            if (posiciones[i][0] == y && posiciones[i][1] == x) {
                return true;
            }
        }
        return false;
    }
}
