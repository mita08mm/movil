package com.swalisoft.payer.note.data

import com.swalisoft.payer.core.domain.DataError
import com.swalisoft.payer.note.data.dto.CreateNoteDto
import com.swalisoft.payer.note.data.remote.NoteHttpDataSource
import com.swalisoft.payer.note.domain.Note
import com.swalisoft.payer.note.domain.NoteRepository

class NoteRepositoryImpl (
    private val remote: NoteHttpDataSource,
) : NoteRepository {
    override suspend fun createNote(dto: CreateNoteDto) : Note {
        return remote.createNote(dto)
    }
    override suspend fun getNotes(): List<Note> {
        return remote.getAllNotes()
    }
}