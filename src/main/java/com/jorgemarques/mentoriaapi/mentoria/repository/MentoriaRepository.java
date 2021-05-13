package com.jorgemarques.mentoriaapi.mentoria.repository;

import com.jorgemarques.mentoriaapi.mentoria.model.Mentoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {
}