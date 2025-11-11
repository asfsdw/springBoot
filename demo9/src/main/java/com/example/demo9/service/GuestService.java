package com.example.demo9.service;

import com.example.demo9.dto.GuestDTO;
import com.example.demo9.entity.Guest;
import com.example.demo9.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {
  private final GuestRepository guestRepository;

  public Page<Guest> getGuestList(int pag, int pageSize) {
    return guestRepository.findAll(PageRequest.of(pag, pageSize, Sort.by(Sort.Order.desc("id"))));
  }

  public void setGuest(GuestDTO dto) {
    guestRepository.save(Guest.dtoToEntity(dto));
  }

  public void setGuestDelete(Long id) {
    guestRepository.deleteById(id);
  }
}
