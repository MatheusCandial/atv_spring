package br.com.Estaleiro.repositories;

import br.com.Estaleiro.model.NavioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavioRepository extends JpaRepository<NavioModel, Integer> {
    public Page<NavioModel> findAll(Pageable pageable);

    public Page<NavioModel> findByNameStartsWithIgnoreCase(String name, Pageable pageable);
}
