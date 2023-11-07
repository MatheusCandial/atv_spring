package br.com.Estaleiro.repositories;

import br.com.Estaleiro.model.EstaleiroModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstaleiroRepository extends JpaRepository<EstaleiroModel, Integer> {
    public Page<EstaleiroModel> findAll(Pageable pageable);

    public Page<EstaleiroModel> findByNameStartsWithIgnoreCase(String name, Pageable pageable);
}
