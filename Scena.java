package simulacijaUsisivac;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Scena extends Canvas implements Runnable {
    SkupFigura skup = new SkupFigura();
    private Usisivac usisivac;
    private Thread thread;
    private boolean works = false; // Inicijalno stanje niti je zaustavljeno

    public Scena() throws Greska {
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(500, 300));
        addListeners();
        usisivac = new Usisivac(getPreferredSize().width / 2, getPreferredSize().height / 2);
        addFigura(usisivac);
        thread = new Thread(this);
        thread.start();
        proceed(); // Pokrećemo nit odmah nakon inicijalizacije
    }

    @Override
    public void paint(Graphics g) {
        skup.makeFirstCurrent();
        try {
            while (true) {
                skup.getCurrent().draw(g);
                skup.next();
            }
        } catch (Greska e) {usisivac.draw(g);
        }
    }

    private void addListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                getParent().requestFocus();
                if (e.getButton() == MouseEvent.BUTTON1) {
                	if(((e.getX()+5)>(Scena.this.getWidth()))|| ((e.getY()+5)>(Scena.this.getHeight()))) {
                		return;
                	}
                    try {
                    	
                        addFigura(new Kamencic(e.getX(), e.getY()));
                        repaint();
                        if (!works) {
                            proceed(); // Osiguravamo da nit radi odmah nakon dodavanja prvog kamencica
                        }
                    } catch (Greska e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private synchronized void addFigura(Figura f) throws Greska {
    	
        for (skup.makeFirstCurrent(); skup.isNext(); skup.next()) {
            if (f.isOverlapped(skup.getCurrent())||f.isContained(skup.getCurrent())) {
                return;
            }
        }
        if (skup.getCurrent() != null && f.isOverlapped(skup.getCurrent())) {
            return;
        }
        skup.add(f);
    }

    private void moveUsisivac() throws Greska {
        if (skup.numberOfFigures() == 1) {
            return;
        }
        Figura minDistFig = null;
        int minDist = -1;
        skup.makeFirstCurrent();
        try {
            while (true) {
                if (skup.getCurrent() == usisivac) {
                    skup.next();
                    continue;
                }
                int dist = usisivac.getDistance(skup.getCurrent());
                if (dist < minDist || minDist == -1) {
                    minDist = dist;
                    minDistFig = skup.getCurrent();
                }
                skup.next();
            }
        } catch (Greska e) {}

        if (minDistFig != null) {
            int dx = minDistFig.getX() - usisivac.getX();
            int dy = minDistFig.getY() - usisivac.getY();
            if (Math.abs(dx) > usisivac.getOffset()) {
                usisivac.setX(usisivac.getX() + (dx > 0 ? usisivac.getOffset() : -usisivac.getOffset()));
            } else if (Math.abs(dy) > usisivac.getOffset()) {
                usisivac.setY(usisivac.getY() + (dy > 0 ? usisivac.getOffset() : -usisivac.getOffset()));
            } else {
                if (usisivac.isOverlapped(minDistFig)||usisivac.isContained(minDistFig)) {
                    skup.remove(minDistFig);
                }
            }
        }
        repaint();
    }

    @Override
    public void run() {
        try {
            while (!thread.isInterrupted()) {
                synchronized (this) {
                    while (!works) {
                        wait();
                    }
                }
                Thread.sleep(50);
                moveUsisivac();
            }
        } catch (InterruptedException e) {
        } catch (Greska e) {
            e.printStackTrace();
        }
    }

    public synchronized void proceed() {
        works = true;
        notify();
    }

    public synchronized void pause() {
        works = false;
    }

    public synchronized boolean getWorks() {
        return works;
    }

    public synchronized void interrupt() {
        thread.interrupt();
    }
}
