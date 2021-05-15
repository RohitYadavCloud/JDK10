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

public class FlowWindow extends Frame {
    private boolean inAnApplet = true;
     
    public FlowWindow() {
	setLayout(new FlowLayout());
        setFont(new Font("Helvetica", Font.PLAIN, 14));
   
	add(new Button("Button 1"));
	add(new Button("2"));
	add(new Button("Button 3"));
	add(new Button("Long-Named Button 4"));
	add(new Button("Button 5"));
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
	FlowWindow window = new FlowWindow();
        window.inAnApplet = false;

	window.setTitle("FlowWindow Application");
	window.pack();
	window.show();
    }
}
