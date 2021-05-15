import browser.Applet;
import awt.Graphics;

class SimpleClick extends Applet {

    StringBuffer buffer = new StringBuffer();

    public void init() {
	resize(500, 20);
        addItem("initializing... ");
    }

    public void start() {
        addItem("starting... ");
    }

    public void stop() {
        addItem("stopping... ");
    }

    public void destroy() {
	addItem("preparing for unloading...");
    }

    public void addItem(String newWord) {
	System.out.println(newWord);
	buffer.append(newWord);
	repaint();
    }

    public void paint(Graphics g) {
	g.clearRect(0, 0, width, height);
	g.drawRect(0, 0, width - 1, height - 1);
	g.drawString(buffer.toString(), 5, 15);
    }

    public void mouseDown(int x, int y) {
	addItem("click!... ");
    }
}
