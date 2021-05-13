package com.jorgemarques.mentoriaapi.mentoria.service;

import com.jorgemarques.mentoriaapi.mentoria.Aluno;
import com.jorgemarques.mentoriaapi.service.AlunoService;
import com.jorgemarques.mentoriaapi.exception.ResourceNotFoundException;
import com.jorgemarques.mentoriaapi.mentoria.adapter.MentoriaAdapter;
import com.jorgemarques.mentoriaapi.mentoria.model.Mentoria;
import com.jorgemarques.mentoriaapi.mentoria.model.MentoriaRequest;
import com.jorgemarques.mentoriaapi.mentoria.model.MentoriaResponse;
import com.jorgemarques.mentoriaapi.mentoria.repository.MentoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MentoriaService {
    private final MentoriaRepository mentoriaRepository;
    private final MentoriaAdapter mentoriaAdapter;
    private final AlunoService alunoService;

    public MentoriaService(MentoriaRepository mentoriaRepository,
                           MentoriaAdapter mentoriaAdapter,
                           AlunoService alunoService) {
        this.mentoriaRepository = mentoriaRepository;
        this.mentoriaAdapter = mentoriaAdapter;
        this.alunoService = alunoService;
    }

    @Transactional(readOnly = true)
    public List<MentoriaResponse> findAll(){
        List<Mentoria> mentorias = mentoriaRepository.findAll();
        return mentoriaAdapter.mentoriaToMentoriaResponse(mentorias);
    }

    @Transactional(readOnly = true)
    public MentoriaResponse findById(final Long id){
        return mentoriaRepository.findById(id)
                .map(mentoriaAdapter::mentoriaToMentoriaResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Mentoria não encontrada."));
    }

    @Transactional //ELe não salva(Commita) se der algum erro
    public Long create(final MentoriaRequest mentoriaRequest){
        Mentoria mentoria = mentoriaAdapter.mentoriaRequestToMentoria(mentoriaRequest);

        Aluno aluno = alunoService.findById(mentoriaRequest.getAlunoid());
        mentoria.setAluno(aluno);
        mentoria.setMentor(aluno.getMentor);

        mentoriaRepository.save(mentoria);
        return mentoria.getId();
    }

    @Transactional
    public void update(final MentoriaRequest mentoriaRequest, final Long id){
        Mentoria mentoria = mentoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentoria não encontrada."));

        if(mentoriaRequest.getAlunoid() != null){
            Aluno aluno = alunoService.findById(mentoriaRequest.getAlunoid());
            mentoria.setAluno(aluno);
            mentoria.setMentor(aluno.getMentor);
        }

        mentoriaAdapter.updateMentoriaByMentoriaRequest(mentoria, mentoriaRequest);
        mentoriaRepository.save(mentoria);
    }

    @Transactional
    public void deleteById(final Long id){
        mentoriaRepository.deleteById(id);
    }
}