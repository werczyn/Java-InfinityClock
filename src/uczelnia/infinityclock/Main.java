package uczelnia.infinityclock;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main extends JPanel {

    private static final int ROZMIAR = 512;
    private int a = ROZMIAR / 2;
    private int b = a;
    private int r = 4 * ROZMIAR / 5;
    private int liczbaPunktow;
    private List<Ellipse2D> listaPunktow = new ArrayList<>();

    public Main(int liczbaPunktow) {
        super(true);
        this.setPreferredSize(new Dimension(ROZMIAR, ROZMIAR));
        this.liczbaPunktow = liczbaPunktow;
        getPoints();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        listaPunktow.forEach(x->{
            g2d.fill(x);
            g2d.draw(x);
        });
        LocalTime czas = LocalTime.now();
        int godzina = czas.getHour();
        int minuta = czas.getMinute();
        int sekunda = czas.getSecond();
        if(godzina>12){
            godzina = godzina-12;
        }
        if(godzina==12){
            godzina=0;
        }

        g2d.setColor(new Color(208, 212, 255));
        for (int i = 0; i<12; i++){
            drawPoint(g2d,i*5);
        }

        for (int i = 0; i<5; i++){
            g2d.setColor(new Color(255 - i * 25,0,0));
            drawPoint(g2d,(godzina*5)+i);
        }

        g2d.setColor(Color.BLUE);
        drawPoint(g2d,minuta);
        g2d.setColor(new Color(0,0,127));
        drawPoint(g2d,minuta-1);
        drawPoint(g2d,minuta+1);

        for (int i=0; i<3; i++){
            g2d.setColor(new Color(0,255-(i*42),0));
            drawPoint(g2d, sekunda - i);
        }

        repaint();

    }

    private void drawPoint(Graphics2D g2d, int point){
        if (point<0){
            g2d.fill(listaPunktow.get(liczbaPunktow+point));
            g2d.draw(listaPunktow.get(liczbaPunktow+point));
        }else{
            g2d.fill(listaPunktow.get(point));
            g2d.draw(listaPunktow.get(point));
        }
    }

    private void getPoints(){
        int m = Math.min(a, b);
        r = 4 * m / 5;
        int r2 = Math.abs(m - r) / 6;
        for (int i = -15; i < liczbaPunktow-15; i++) {
            double t = 2 * Math.PI * i / liczbaPunktow;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
            Ellipse2D circle = new Ellipse2D.Double(x - r2, y - r2, 2 * r2, 2 * r2);
            listaPunktow.add(circle);
        }
    }

    private static void create() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Main(60));
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> create());
    }
}