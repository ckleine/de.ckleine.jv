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
package jverleihnix.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Custom renderer that is used to visualize the rental entries in the rental
 * list.
 */
@SuppressWarnings("serial")
public class RentalEntryRenderer extends JLabel implements ListCellRenderer {

	/*
	 * String builder to create the text for a rental entry.
	 */
	private final StringBuilder bldr = new StringBuilder();

	/*
	 * The icons that are used to visualize the media type of an entry.
	 */
	private final Icon blueray = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(
					getClass().getResource(
							"/jverleihnix/ui/res/award_star_gold_2.png")));
	private final Icon dvd = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(
					getClass().getResource(
							"/jverleihnix/ui/res/award_star_silver_2.png")));
	private final Icon cd = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(
					getClass().getResource(
							"/jverleihnix/ui/res/award_star_bronze_2.png")));
	private final Icon other = new ImageIcon(
			Toolkit.getDefaultToolkit()
					.getImage(
							getClass().getResource(
									"/jverleihnix/ui/res/asterisk_yellow.png")));

	public RentalEntryRenderer() {
		setOpaque(true);
	}

	/**
	 * Visualizes this entry
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		IUIRentalEntry entry = (IUIRentalEntry) value;

		/*
		 * Build the text that describes the rental entry
		 */
		bldr.delete(0, bldr.length());
		bldr.append("<html><b>");
		bldr.append(entry.getDueDate());
		bldr.append("</b><br/>");
		bldr.append(entry.getDescription());
		bldr.append("</html>");
		setText(bldr.toString());

		/*
		 * Set the icon and the borderthat visualizes the media type of the entry
		 */
		switch (entry.getMediaType()) {
		case BLUERAY:
			setIcon(blueray);
			setBorder(BorderFactory.createLineBorder(Color.RED));
			break;
		case DVD:
			setIcon(dvd);
			setBorder(BorderFactory.createLineBorder(Color.ORANGE));
			break;
		case CD:
			setIcon(cd);
			setBorder(BorderFactory.createLineBorder(Color.GREEN));
			break;
		default:
			setIcon(other);
			setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}

		/*
		 * Set the background/foreground if selected or not
		 */
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}

}
