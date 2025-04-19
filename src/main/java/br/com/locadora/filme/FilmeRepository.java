package br.com.locadora.filme;

import java.util.Optional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
	@Query("SELECT f FROM Filme f JOIN FETCH f.atores WHERE f.id = :id")
    Optional<Filme> findByIdWithAtores(@Param("id") Long id);
	

}
