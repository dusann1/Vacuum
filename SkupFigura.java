package simulacijaUsisivac;

import java.util.ArrayList;

public class SkupFigura {
    private ArrayList<Figura> figure;
    private Figura current;

    public SkupFigura() {
        figure = new ArrayList<Figura>();
    }

    public void add(Figura f) {
        if (!figure.contains(f)) {
            figure.add(f);
            if (current == null) {
                current = f;
            }
        }
    }

    public void next() throws Greska {
        if (current == null || figure.isEmpty()) {
            return;
        }
        int index = figure.indexOf(current);
        if (index != -1 && index < figure.size() - 1) {
            current = figure.get(index + 1);
        } else {
            throw new Greska();
        }
    }

    public boolean isNext() {
        if (current == null || figure.isEmpty()) {
            return false;
        }
        int index = figure.indexOf(current);
        return index != -1 && index < figure.size() - 1;
    }

    public void makeFirstCurrent() {
        if (!figure.isEmpty()) {
            current = figure.get(0);
        } else {
            current = null;
        }
    }

    public Figura getCurrent() {
        return current;
    }

    public boolean contain(Figura f) {
        return figure.contains(f);
    }

    public void clear() {
        figure.clear();
        current = null;
    }

    public void remove(Figura f) {
        if (figure.remove(f) && f == current) {
            makeFirstCurrent();
        }
    }

    public int numberOfFigures() {
        return figure.size();
    }
}
