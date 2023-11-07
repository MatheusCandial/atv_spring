package br.com.Estaleiro.controllers;

import br.com.Estaleiro.dto.EstaleiroDTO;
import br.com.Estaleiro.service.EstaleiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estaleiro")
@Tag(name = "Estaleiro", description = "This endpoint manages Estaleiro")
public class EstaleiroController
{
    @Autowired
    private EstaleiroService service;

    @PostMapping
    @Operation(summary = "Persists a new Estaleiro in database", tags = {"Estaleiros"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstaleiroDTO.class))
            })
    })
    public EstaleiroDTO create(@RequestBody EstaleiroDTO dto){
        return service.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Estaleiro using the ID", tags = {"Estaleiros"}, responses = {
            @ApiResponse(description = "Success!", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = EstaleiroDTO.class)
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public EstaleiroDTO findById(@PathVariable("id") int id){
        EstaleiroDTO dto = service.findById(id);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EstaleiroDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<EstaleiroDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<EstaleiroDTO> Estaleiros = service.findAll(pageable);

        for (EstaleiroDTO Estaleiro:Estaleiros){
            buildEntityLink(Estaleiro);
        }
        return new ResponseEntity(assembler.toModel(Estaleiros), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<PagedModel<EstaleiroDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<EstaleiroDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<EstaleiroDTO> Estaleiros = service.findByName(name, pageable);

        for (EstaleiroDTO Estaleiro:Estaleiros){
            buildEntityLink(Estaleiro);
        }
        return new ResponseEntity(assembler.toModel(Estaleiros), HttpStatus.OK);
    }

    @PutMapping
    public EstaleiroDTO update(@RequestBody EstaleiroDTO dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        EstaleiroDTO dto = service.findById(id);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public void buildEntityLink(EstaleiroDTO estaleiro){
        //..self link
        estaleiro.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(estaleiro.getId())
                ).withSelfRel()
        );
    }
}
