package br.com.Estaleiro.service;

import br.com.Estaleiro.dto.EstaleiroDTO;
import br.com.Estaleiro.dto.NavioDTO;
import br.com.Estaleiro.mapper.CustomModelMapper;
import br.com.Estaleiro.model.EstaleiroModel;
import br.com.Estaleiro.model.NavioModel;
import br.com.Estaleiro.repositories.EstaleiroRepository;
import br.com.Estaleiro.repositories.NavioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NavioService {
    @Autowired
    private NavioRepository repository;

    public NavioDTO create(NavioDTO dto){
        NavioModel model = CustomModelMapper.parseObject(dto, NavioModel.class);
        return CustomModelMapper.parseObject(repository.save(model), NavioDTO.class);
    }

    public NavioDTO findById(int id){
        NavioModel model = repository.findById(id).orElseThrow(
                ()-> new br.com.Estaleiro.exception.ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, NavioDTO.class);
    }

    public Page<NavioDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, NavioDTO.class));
    }

    public NavioDTO update(NavioDTO dto){
        NavioModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new br.com.Estaleiro.exception.ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, NavioModel.class);
        return CustomModelMapper.parseObject(repository.save(model), NavioDTO.class);
    }

    public void delete(NavioDTO dto){
        NavioModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new br.com.Estaleiro.exception.ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<NavioDTO> findByName(String name, Pageable pageable){
        var page = repository.findByNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, NavioDTO.class));
    }
}