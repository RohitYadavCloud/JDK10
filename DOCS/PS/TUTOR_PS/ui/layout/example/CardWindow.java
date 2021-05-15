/*
 * Copyright (c) 1994 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
import java.awt.*;

public class CardWindow extends Frame {
    private boolean inAnApplet = true;
     
    Panel cards;
    final static String BUTTONPANEL = "Panel with Buttons";
    final static String TEXTPANEL = "Panel with TextField";

    public CardWindow() {
	setLayout(new BorderLayout());
        setFont(new Font("Helvetica", Font.PLAIN, 14));

	//Put the Choice in a Panel to get a nicer look.
	Panel cp = new Panel();
	Choice c = new Choice();
	c.addItem(BUTTONPANEL);
	c.addItem(TEXTPANEL);
	cp.add(c);
	add("North", cp);

	cards = new Panel();
	cards.setLayout(new CardLayout());
   
	Panel p1 = new Panel();
	p1.add(new Button("Button 1"));
	p1.add(new Button("Button 2"));
	p1.add(new Button("Button 3"));

	Panel p2 = new Panel();
	p2.add(new TextField("TextField", 20));

	cards.add(BUTTONPANEL, p1);
	cards.add(TEXTPANEL, p2);
	add("Center", cards);
    }

    public boolean action(Event evt, Object arg) {
	if (evt.target instanceof Choice) {
	    ((CardLayout)cards.getLayout()).show(cards,(String)arg);
	    return true;
	}
	return false;
    }

    public synchronized boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_ICONIFY) {//DOESN'T seem to be necessary
            hide(); 
            return true;
        }
        if (e.id == Event.WINDOW_DESTROY) {
            if (inAnApplet) {
                dispose();
                return true;
            } else {
                System.exit(0);
            }
        }   
        return super.handleEvent(e);
    }

    public static void main(String args[]) {
	CardWindow window = new CardWindow();
        window.inAnApplet = false;

	window.setTitle("CardWindow Application");
	window.pack();
	window.show();
    }
}
