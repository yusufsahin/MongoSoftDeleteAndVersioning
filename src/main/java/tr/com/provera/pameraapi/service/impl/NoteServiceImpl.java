package tr.com.provera.pameraapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.com.provera.pameraapi.dto.NoteDto;

import tr.com.provera.pameraapi.model.Note;
import tr.com.provera.pameraapi.repository.NoteRepository;
import tr.com.provera.pameraapi.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<NoteDto> createNote(NoteDto noteDto) {
        Note note = convertToEntity(noteDto);
        return noteRepository.save(note)
                .map(this::convertToDto);
    }

    @Override
    public Mono<NoteDto> getNoteById(String id) {
        return noteRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public Flux<NoteDto> getAllNotes() {
        return noteRepository.findAll()
                .map(this::convertToDto);
    }

    @Override
    public Mono<NoteDto> updateNote(String id, NoteDto noteDto) {
        return noteRepository.findById(id)
                .flatMap(existingNote -> {
                    modelMapper.map(noteDto, existingNote);
                    existingNote.setId(id); // Ensure the correct ID is maintained
                    return noteRepository.save(existingNote);
                })
                .map(this::convertToDto);
    }

    @Override
    public Mono<Void> deleteNote(String id) {
        return noteRepository.deleteById(id);
    }

    // Utility methods for mapping
    public NoteDto convertToDto(Note note) {
        return modelMapper.map(note, NoteDto.class);
    }

    public Note convertToEntity(NoteDto noteDto) {
        return modelMapper.map(noteDto, Note.class);
    }
}
