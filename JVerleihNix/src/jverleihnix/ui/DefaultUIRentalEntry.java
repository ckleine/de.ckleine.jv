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


public class DefaultUIRentalEntry implements IUIRentalEntry {

	private final String dueDate;
	private final String description;
	private final MediaType mediaType;
	
	public DefaultUIRentalEntry(String dueDate, String description, MediaType mediaType) {
		this.dueDate = dueDate;
		this.description = description;
		this.mediaType = mediaType;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getDueDate() {
		return dueDate;
	}

	@Override
	public MediaType getMediaType() {
		return mediaType;
	}

}
