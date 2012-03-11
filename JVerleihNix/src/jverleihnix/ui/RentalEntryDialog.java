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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jverleihnix.app.Application;
import jverleihnix.internationalisation.Messages;

/**
 * <p>
 * The dialog to add or edit e rental entry.
 * </p>
 * <p>
 * To use the dialog for adding a new entry create an instance, set the entry to
 * <code>null</code>, set the dialog to visible, evaluate the button that was
 * clicked, and then read the new entry from the dialog. If an entry is to be
 * edited set the entry to the entry's values that is to be edited.
 * </p>
 */
@SuppressWarnings("serial")
public class RentalEntryDialog extends JDialog {

	/**
	 * Constants that describe the button that was clicked when the dialog was
	 * closed.
	 */
	public static enum ButtonPressed {
		OK, // Ok button
		CANCEL, // Cancel button
		NONE, // Window close button
	};

	/*
	 * The entry that is added or modified
	 */
	private IUIRentalEntry entry;

	/*
	 * The button that was clicked when the dialog was closed.
	 */
	private ButtonPressed buttonPressed;

	/*
	 * Radio buttons for the media type
	 */
	private JRadioButton other;
	private JRadioButton cd;
	private JRadioButton blueray;
	private JRadioButton dvd;

	/*
	 * Text fields for the rental entry's description and due date.
	 */
	private JTextField description;
	private JTextField dueDate;

	/**
	 * Initializes the dialog and builds the UI.
	 */
	public RentalEntryDialog(JFrame parent) {
		super(parent, true);
		setTitle(Messages.getString("rentalEntry")); //$NON-NLS-1$

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel entryPanel = createEntryPanel();
		contentPane.add(entryPanel, BorderLayout.CENTER);

		JPanel buttonPanel = createButtonPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		pack();

		/*
		 * Handles the click on the window close button
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				buttonPressed = ButtonPressed.NONE;
				entry = null;
				super.windowClosing(e);
			}
		});
	}

	/**
	 * @return The panel with the buttons displayed on the footer of the dialog.
	 */
	private JPanel createButtonPanel() {
		JPanel innerButtonPanel = new JPanel();
		innerButtonPanel.setLayout(new GridLayout(1, 0));

		JButton okButton = createOkButton();
		innerButtonPanel.add(okButton);

		JButton cancelButton = createCancelButton();
		innerButtonPanel.add(cancelButton);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(innerButtonPanel);
		return buttonPanel;
	}

	/**
	 * Creates and initializes the cancel button.
	 * 
	 * @return Cancel button
	 */
	private JButton createCancelButton() {
		JButton cancelButton = new JButton(Messages.getString("cancel")); //$NON-NLS-1$
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				entry = null;
				setVisible(false);
				buttonPressed = ButtonPressed.CANCEL;
			}
		});
		return cancelButton;
	}

	/*
	 * Builder that is used to build the validation error message.
	 */
	private final StringBuilder bldr = new StringBuilder();

	/**
	 * Initializes and creates the OK button. When the OK button is clicked the
	 * values entered are validated. If the validation fails an error dialog is
	 * shown and the user has the chance to correct the entry's values.
	 * 
	 * @return OK button
	 */
	private JButton createOkButton() {
		JButton okButton = new JButton(Messages.getString("ok")); //$NON-NLS-1$
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MediaType mediaType = MediaType.OTHER;
				if (cd.isSelected()) {
					mediaType = MediaType.CD;
				} else if (dvd.isSelected()) {
					mediaType = MediaType.DVD;
				} else if (blueray.isSelected()) {
					mediaType = MediaType.BLUERAY;
				}

				/*
				 * Initializes this dialog's entry
				 */
				entry = new DefaultUIRentalEntry(dueDate.getText().trim(),
						description.getText().trim(), mediaType);

				/*
				 * Validate the entry's values and, if validation fails, an
				 * error dialog is shown.
				 */
				String[] errorMsgs = Application.instance.validateEntry(entry);
				if ((errorMsgs != null) && (errorMsgs.length > 0)) {
					bldr.delete(0, bldr.length());
					bldr.append("<html><b>"+ Messages.getString("ErrorInEntry") +"</b><ul>"); //$NON-NLS-1$
					for (String msg : errorMsgs) {
						bldr.append("<li>"); //$NON-NLS-1$
						bldr.append(msg);
						bldr.append("</li>"); //$NON-NLS-1$
					}
					bldr.append("</ul></html>"); //$NON-NLS-1$
					JOptionPane.showMessageDialog(RentalEntryDialog.this, bldr
							.toString(), "Errors", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				} else {
					buttonPressed = ButtonPressed.OK;
					setVisible(false);
				}
			}
		});
		return okButton;
	}

	/**
	 * Creates and initializes the panel with the entry fields and radio
	 * buttons.
	 * 
	 * @return Entry panel
	 */
	private JPanel createEntryPanel() {
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0, 5, 5, 5);

		JLabel dueDateLabel = new JLabel(Messages.getString("dueDate")); //$NON-NLS-1$
		gc.gridwidth = 1;
		gc.weightx = 0;
		entryPanel.add(dueDateLabel, gc);

		dueDate = new JTextField(20);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0;
		entryPanel.add(dueDate, gc);

		JLabel descriptionLabel = new JLabel(Messages.getString("description")); //$NON-NLS-1$
		gc.gridwidth = 1;
		gc.weightx = 0;
		entryPanel.add(descriptionLabel, gc);

		description = new JTextField(20);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0;
		entryPanel.add(description, gc);

		JLabel severitiesLabel = new JLabel(Messages.getString("mediaType")); //$NON-NLS-1$
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.gridwidth = 1;
		gc.gridheight = 4;
		gc.weightx = 0;
		entryPanel.add(severitiesLabel, gc);

		ButtonGroup severities = new ButtonGroup();
		other = new JRadioButton("Other"); //$NON-NLS-1$
		other.setSelected(true);
		severities.add(other);
		gc.anchor = GridBagConstraints.WEST;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = 1;
		gc.weightx = 1.0;
		entryPanel.add(other, gc);

		cd = new JRadioButton("CD"); //$NON-NLS-1$
		severities.add(cd);
		entryPanel.add(cd, gc);

		dvd = new JRadioButton("DVD"); //$NON-NLS-1$
		severities.add(dvd);
		entryPanel.add(dvd, gc);

		blueray = new JRadioButton("BlueRay"); //$NON-NLS-1$
		severities.add(blueray);
		entryPanel.add(blueray, gc);
		return entryPanel;
	}

	/**
	 * Sets the entry to be edited. If the given entry is null a new entry is
	 * created.
	 * 
	 * @param entry
	 *            to edit
	 */
	public void setEntry(IUIRentalEntry entry) {
		this.entry = entry;
	}

	/**
	 * Returns the entry of the dialog set the values that were entered on the
	 * dialog.
	 * 
	 * @return Entry
	 */
	public IUIRentalEntry getEntry() {
		return entry;
	}

	/**
	 * @see JDialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			description.setText(entry != null ? entry.getDescription() : ""); //$NON-NLS-1$
			dueDate.setText(entry != null ? entry.getDueDate() : ""); //$NON-NLS-1$
			dueDate.requestFocusInWindow();
			if (entry != null) {
				switch (entry.getMediaType()) {
				case CD:
					cd.setSelected(true);
					break;
				case DVD:
					dvd.setSelected(true);
					break;
				case BLUERAY:
					blueray.setSelected(true);
					break;
				default:
					other.setSelected(true);
				}
			} else {
				dvd.setSelected(true);
			}
		}

		centerDialogAboveFrame();

		super.setVisible(visible);
	}

	/**
	 * Centers this dialog above its parent's frame
	 */
	private void centerDialogAboveFrame() {
		if (getParent() != null) {
			Component frame = getParent();
			int parWidth = frame.getWidth();
			int parHeight = frame.getHeight();
			int parX = frame.getLocation().x;
			int parY = frame.getLocation().y;
			Point loc = new Point(((parWidth - getWidth()) / 2) + parX,
					((parHeight - getHeight()) / 2) + parY);
			setLocation(loc);
		}
	}

	/**
	 * @return The button clicked when the dialog was closed.
	 */
	public ButtonPressed getButtonPressed() {
		return buttonPressed;
	}

}
