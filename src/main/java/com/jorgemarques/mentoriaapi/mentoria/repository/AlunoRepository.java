package com.jorgemarques.mentoriaapi.mentoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jorgemarques.mentoriaapi.mentoria.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}