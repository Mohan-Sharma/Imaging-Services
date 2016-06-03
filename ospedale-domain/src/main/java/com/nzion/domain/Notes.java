/*
 * header file
 */
package com.nzion.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nzion.domain.base.IdGeneratingBaseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Notes.
 */
@Entity
@Table(name = "NOTES")
public class Notes extends IdGeneratingBaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9050581606672978231L;

	/** The note type. */
	private Enumeration noteType;

	/** The note content. */
	private String noteContent;

	private Date noteEntry = new Date();

	/**
	 * Gets the note content.
	 * 
	 * @return the note content
	 */
	@Column(name = "NOTE_CONTENT", nullable = true, columnDefinition = "longtext")
	public String getNoteContent() {
	return noteContent;
	}

	/**
	 * Sets the note content.
	 * 
	 * @param noteContent
	 *            the new note content
	 */
	public void setNoteContent(String noteContent) {
	this.noteContent = noteContent;
	}

	@ManyToOne
	@JoinColumn(name = "NOTE_TYPE_ID")
	public Enumeration getNoteType() {
	return noteType;
	}

	public Enumeration setNoteType(Enumeration noteType) {
	return this.noteType = noteType;
	}

	@Column(name = "NOTE_ENTRY_DATE", nullable = false)
	public Date getNoteEntry() {
	return noteEntry;
	}

	public void setNoteEntry(Date noteEntry) {
	this.noteEntry = noteEntry;
	}

}
