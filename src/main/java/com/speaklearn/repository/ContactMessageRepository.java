package com.speaklearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.speaklearn.domain.ContactMessage;

//@Repository //if class extending jparepository , no need to add this annotation
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

}
