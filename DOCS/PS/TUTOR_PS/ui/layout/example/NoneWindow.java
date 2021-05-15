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

public class NoneWindow extends Frame {
    private boolean inAnApplet = true;
    private boolean laidOut = false;
    private Button b1, b2, b3;

    public NoneWindow() {
	super();
	setLayout(null);
	setFont(new Font("Helvetica", Font.PLAIN, 14));

	b1 = new Button("one");
	add(b1);
	b2 = new Button("two");
	add(b2);
	b3 = new Button("three");
	add(b3);
    }

    public void paint(Graphics g) {
	if (!laidOut) {
	    Insets insets = insets();
	    //We're guaranteed that insets() will return a valid Insets
	    //if called from paint() -- it isn't valid when called from 
	    //the constructor.
	    insets = insets(); // We could perhaps cache this in an ivar, but
				  // insets can change, and when they
				  // do, the AWT creates a whole new Insets
				  // object; the old one is invalid.
				  // BUT WILL IT REALLY CHANGE???
	    b1.reshape(50 + insets.left, 5 + insets.top, 50, 20);
	    b2.reshape(70 + insets.left, 35 + insets.top, 50, 20);
	    b3.reshape(130 + insets.left, 15 + insets.top, 50, 30);

	    laidOut = true;
	}
    }

    public synchronized boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_ICONIFY) {//DOESN'T seem to be necessary
            hide(); 
        }
        if (e.id == Event.WINDOW_DESTROY) {
            if (inAnApplet) {
                dispose();
                return false;
            } else {
                System.exit(0);
            }
        }   
        return super.handleEvent(e);
    }

    public static void main(String args[]) {
	NoneWindow window = new NoneWindow();
	Insets insets = window.insets();
	//How do we know insets is valid here?
        window.inAnApplet = false;

	window.setTitle("NoneWindow Application");
	window.resize(250 + insets.left + insets.right,
		      90 + insets.top + insets.bottom);
        window.show();
    }
}
