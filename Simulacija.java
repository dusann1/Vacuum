package simulacijaUsisivac;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulacija extends Frame {
    private Scena scene;

    private void addComponents() throws Greska {
        setLayout(new BorderLayout());
        scene = new Scena();
        add(scene, BorderLayout.CENTER);
    }

    private void terminate() {
        scene.interrupt();
        dispose();
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                terminate();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    terminate();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (scene.getWorks()) {
                        scene.pause();
                    } else {
                        scene.proceed();
                    }
                }
            }
        });
    }

    public Simulacija() {
        setTitle("Simulacija");
        try {
            addComponents();
        } catch (Greska e) {
            e.printStackTrace();
        }
        addListeners();
        setLocation(500, 200);
        pack();
        setResizable(false);
        setVisible(true);
        scene.proceed();
    }

    public static void main(String[] args) {
        new Simulacija();
    }
}
