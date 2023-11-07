package br.com.Estaleiro.service;

import br.com.Estaleiro.dto.EstaleiroDTO;
import br.com.Estaleiro.mapper.CustomModelMapper;
import br.com.Estaleiro.model.EstaleiroModel;
import br.com.Estaleiro.repositories.EstaleiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class EstaleiroService {
    @Autowired
    private EstaleiroRepository repository;

    public EstaleiroDTO create(EstaleiroDTO dto){
        EstaleiroModel model = CustomModelMapper.parseObject(dto, EstaleiroModel.class);
        return CustomModelMapper.parseObject(repository.save(model), EstaleiroDTO.class);
    }

    public EstaleiroDTO findById(int id){
        EstaleiroModel model = repository.findById(id).orElseThrow(
                ()-> new br.com.Estaleiro.exception.ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, EstaleiroDTO.class);
    }

    public Page<EstaleiroDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, EstaleiroDTO.class));
    }

    public EstaleiroDTO update(EstaleiroDTO dto){
        EstaleiroModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new br.com.Estaleiro.exception.ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, EstaleiroModel.class);
        return CustomModelMapper.parseObject(repository.save(model), EstaleiroDTO.class);
    }

    public void delete(EstaleiroDTO dto){
        EstaleiroModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new br.com.Estaleiro.exception.ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<EstaleiroDTO> findByName(String name, Pageable pageable){
        var page = repository.findByNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, EstaleiroDTO.class));
    }
}
