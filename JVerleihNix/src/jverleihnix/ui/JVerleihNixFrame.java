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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import jverleihnix.app.Application;
import jverleihnix.app.ApplicationException;
import jverleihnix.ui.RentalEntryDialog.ButtonPressed;

/**
 * The main frame of the user interface of the rental list application.
 */
@SuppressWarnings("serial")
public class JVerleihNixFrame extends JFrame {

	/*
	 * Definition of the icons that are used in the tool bar of this UI.
	 */
	private final Icon openIcon = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/jverleihnix/ui/res/fileopen.png"))); //$NON-NLS-1$
	private final Icon saveIcon = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/jverleihnix/ui/res/filesaveas.png"))); //$NON-NLS-1$
	private final Icon newIcon = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/jverleihnix/ui/res/edit_add.png"))); //$NON-NLS-1$
	private final Icon editIcon = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/jverleihnix/ui/res/edit.png"))); //$NON-NLS-1$
	private final Icon returnedIcon = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/jverleihnix/ui/res/edit_remove.png"))); //$NON-NLS-1$

	/*
	 * Defintion of global components and models to be used in the UI.
	 */
	private final RentalEntryDialog rentalEntryDialog;
	private final RentalListModel rentalListModel;
	private final JFileChooser fileChooser;
	private final JList rentalList;

	/**
	 * Initializes the main frame of the rental list application. The components
	 * are initialized and place on the UI and the UI itself is centered on the
	 * screen.
	 */
	public JVerleihNixFrame() {
		/*
		 * Initialize global components and models
		 */
		rentalListModel = new RentalListModel();
		rentalList = new JList(rentalListModel);
		fileChooser = new JFileChooser();
		rentalEntryDialog = new RentalEntryDialog(this);

		/*
		 * Build the UI
		 */
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		JToolBar toolBar = createToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		JScrollPane rentalListPane = createRentalListPane();
		contentPane.add(rentalListPane, BorderLayout.CENTER);

		/*
		 * Initialize the frame itself
		 */
		setTitle("JVerleihNix - vielfach kopiert, nie erreicht!"); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Set the correct size for the frame
		 */
		pack();
		setSize(getWidth(), getHeight() * 2);

		/*
		 * Center the frame on the screen
		 */
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2,
				(dim.height - getHeight()) / 2);
	}

	/**
	 * @return The toolbar of the UI
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.add(createLoadButton());
		toolBar.add(createSaveButton());
		toolBar.add(createNewButton());
		toolBar.add(createEditButton());
		toolBar.add(createReturnedButton());
		return toolBar;
	}

	/**
	 * Creates the load button that can be used to load a file. The event
	 * handler is initialized too. It shows a file chooser first in which the
	 * file is selected to load.
	 * 
	 * @return The button on the toolbar to load a file.
	 */
	private JButton createLoadButton() {
		JButton loadButton = new JButton(Messages.getString("JVerleihNixFrame.6"), openIcon); //$NON-NLS-1$
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Show the file chooser and if a file is selected load it using
				 * the application facade.
				 */
				if (fileChooser.showOpenDialog(JVerleihNixFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						Application.instance.load(file.getAbsolutePath());
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								rentalList.updateUI();							
							}
						});
					} catch (ApplicationException exc) {
						JOptionPane.showMessageDialog(JVerleihNixFrame.this,
								Messages.getString("JVerleihNixFrame.0") + exc.getMessage() //$NON-NLS-1$
										+ "</p></html>", Messages.getString("JVerleihNixFrame.9"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE);
					} catch (IOException exc) {
						JOptionPane.showMessageDialog(JVerleihNixFrame.this,
								Messages.getString("JVerleihNixFrame.10") + exc.getMessage() //$NON-NLS-1$
										+ "</p></html>", Messages.getString("JVerleihNixFrame.1"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		return loadButton;
	}
	/**
	 * Creates and Error Message
	 * 
	 */
	public static void errorMessage(String errorLog){
		JOptionPane.showMessageDialog(null,
				Messages.getString("JVerleihNixFrame.13") + errorLog //$NON-NLS-1$
				+ "</p></html>", Messages.getString("JVerleihNixFrame.15"), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.INFORMATION_MESSAGE);
	}
	

	/**
	 * Creates and initializes the save button that is displayed on the tool bar
	 * of the UI. Before the rental entries are stored a file chooser is
	 * displayed to select the file to write to.
	 * 
	 * @return Save button
	 */
	private JButton createSaveButton() {
		JButton saveButton = new JButton(Messages.getString("JVerleihNixFrame.16"), saveIcon); //$NON-NLS-1$
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(JVerleihNixFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						Application.instance.store(file.getAbsolutePath());
					} catch (ApplicationException exc) {
						JOptionPane.showMessageDialog(JVerleihNixFrame.this,
								Messages.getString("JVerleihNixFrame.17") + exc.getMessage() //$NON-NLS-1$
										+ "</p></html>", Messages.getString("JVerleihNixFrame.19"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE);
					} catch (IOException exc) {
						JOptionPane.showMessageDialog(JVerleihNixFrame.this,
								Messages.getString("JVerleihNixFrame.20") + exc.getMessage() //$NON-NLS-1$
										+ "</p></html>", Messages.getString("JVerleihNixFrame.22"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		return saveButton;
	}

	/**
	 * @return The scroll pane on which the rental list is displayed.
	 */
	private JScrollPane createRentalListPane() {
		initializeRentalList();
		JScrollPane rentalListPane = new JScrollPane(rentalList);
		rentalListPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rentalListPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		return rentalListPane;
	}

	/**
	 * Initializes the rental list component.
	 */
	private void initializeRentalList() {
		rentalList.setCellRenderer(new RentalEntryRenderer());
		rentalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * Creates and initialized the returned button with which a rental entry can be
	 * marked as finished.
	 * 
	 * @return Return button
	 */
	private JButton createReturnedButton() {
		JButton returnButton = new JButton(Messages.getString("JVerleihNixFrame.23"), returnedIcon); //$NON-NLS-1$
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] indices = rentalList.getSelectedIndices();
				if (indices.length == 1) {
					rentalListModel.finishEntry(indices[0]);
					rentalList.clearSelection();
				}
			}
		});
		return returnButton;
	}

	/**
	 * Creates and initializes the edit button with which a rental entry can be
	 * modified. To modify an entry it must be selected in the list and the edit
	 * button must be clicked. Then the edit/add dialog for rental entries is
	 * shown.
	 * 
	 * @return Edit button
	 */
	private JButton createEditButton() {
		JButton editButton = new JButton(Messages.getString("JVerleihNixFrame.24"), editIcon); //$NON-NLS-1$
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] indices = rentalList.getSelectedIndices();
				if (indices.length == 1) {
					int index = indices[0];
					IUIRentalEntry entry = (IUIRentalEntry) rentalListModel
							.getElementAt(index);
					rentalEntryDialog.setEntry(entry);
					rentalEntryDialog.setVisible(true);
					if (rentalEntryDialog.getButtonPressed() == ButtonPressed.OK) {
						rentalListModel.modifyEntry(index, rentalEntryDialog
								.getEntry());
					}
				}
			}
		});
		return editButton;
	}

	/**
	 * Creates and initializes the new button that is used to create a new rental
	 * entry. To create a new rental entry the add/edit dialog is shown.
	 * 
	 * @return New button
	 */
	private JButton createNewButton() {
		JButton newButton = new JButton(Messages.getString("JVerleihNixFrame.25"), newIcon); //$NON-NLS-1$
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rentalEntryDialog.setEntry(null);
				rentalEntryDialog.setVisible(true);
				if (rentalEntryDialog.getButtonPressed() == ButtonPressed.OK) {
					rentalListModel.addElement(rentalEntryDialog.getEntry());
				}
			}
		});
		return newButton;
	}

}
