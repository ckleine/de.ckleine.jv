/*
 * Copyright 2012 Ivan Bogicevic, Markus Knauß, Daniel Kulesz, Holger Röder, Matthias Wetzel, Jan-Peter Ostberg, Daniel Schleicher
 * 
 * This file is part of JVerleihNix.
 * 
 * JVerleihNix is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JVerleihNix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JVerleihNix.  If not, see <http://www.gnu.org/licenses/>.
 */
package jverleihnix;

import javax.swing.SwingUtilities;

import jverleihnix.ui.JVerleihNixFrame;

/**
 * JVerleihNix main class that is used to initialize the application
 * and to start up the UI.
 */
public class JVerleihNix {
	
	private static JVerleihNixFrame ui;
	
	/**
	 * Starts this application
	 * @param args No runtime arguments are evaluated
	 */
	public static void main(String[] args) {
		Runnable uiThread = new Runnable() {
			@Override
			public void run() {
				ui = new JVerleihNixFrame();
				ui.setVisible(true);
			}

		};
		SwingUtilities.invokeLater(uiThread);
	}
	
	/**
	 * Restarts the ui
	 */
	public static void refresh(){
		ui.setVisible(false);
		ui = new JVerleihNixFrame();
		ui.setVisible(true);
	}
}
