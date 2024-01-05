package tr.com.provera.pameraapi.service;

import tr.com.provera.pameraapi.dto.NoteDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NoteService {

    Mono<NoteDto> createNote(NoteDto noteDto);

    Mono<NoteDto> getNoteById(String id);

    Flux<NoteDto> getAllNotes();

    Mono<NoteDto> updateNote(String id, NoteDto noteDto);

    Mono<Void> deleteNote(String id);
}
