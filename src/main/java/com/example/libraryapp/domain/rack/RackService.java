package com.example.libraryapp.domain.rack;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.config.assembler.BookItemModelAssembler;
import com.example.libraryapp.domain.config.assembler.RackModelAssembler;
import com.example.libraryapp.domain.exception.rack.RackException;
import com.example.libraryapp.domain.exception.rack.RackNotFoundException;
import com.example.libraryapp.management.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RackService {
    private final RackRepository rackRepository;
    private final RackModelAssembler rackModelAssembler;
    private final PagedResourcesAssembler<Rack> rackPagedResourcesAssembler;
    private final BookItemModelAssembler bookItemModelAssembler;
    private final PagedResourcesAssembler<BookItem> bookItemPagedResourcesAssembler;

    public PagedModel<RackDto> findAllRacks(Pageable pageable) {
        Page<Rack> racksPage =  rackRepository.findAll(pageable);
        return rackPagedResourcesAssembler.toModel(racksPage, rackModelAssembler);
    }

    public PagedModel<RackDto> findRacksByLocation(String location, Pageable pageable) {
        Page<Rack> pageOfRacksByLocation = rackRepository.findAllByLocationIdentifierContainsIgnoreCase(location, pageable);
        return rackPagedResourcesAssembler.toModel(pageOfRacksByLocation, rackModelAssembler);
    }

    public PagedModel<BookItemDto> findAllRackBookItems(Long id, Pageable pageable) {
        Rack rack = findRack(id);
        List<BookItem> bookItems = rack.getBookItems();
        Page<BookItem> bookItemPage = new PageImpl<>(bookItems, pageable, bookItems.size());
        return bookItemPagedResourcesAssembler.toModel(bookItemPage, bookItemModelAssembler);
    }

    public RackDto findRackById(Long rackId) {
        Rack rack = findRack(rackId);
        return rackModelAssembler.toModel(rack);
    }

    @Transactional
    public RackDto replaceRack(Long rackId, RackToUpdateDto rackToUpdate) {
        Rack rack = findRack(rackId);
        rack.setLocationIdentifier(rackToUpdate.getLocationIdentifier());
        return rackModelAssembler.toModel(rack);
    }

    @Transactional
    public RackDto updateRack(Long rackId, RackToUpdateDto rackToUpdate) {
        Rack rack = findRack(rackId);

        if (rackToUpdate.getLocationIdentifier() != null) {
            rack.setLocationIdentifier(rackToUpdate.getLocationIdentifier());
        }

        return rackModelAssembler.toModel(rack);
    }

    public RackDto addRack(RackToSaveDto rack) {
        boolean rackLocationIsUnique = findRacksByLocation(rack.getLocationIdentifier(), null)
                .getMetadata()
                .getTotalElements() == 0;
        if (rackLocationIsUnique) {
            Rack rackToSave = RackMapper.map(rack);
            Rack savedRack = rackRepository.save(rackToSave);
            return rackModelAssembler.toModel(savedRack);
        } else throw new RackException(Message.RACK_LOCATION_ALREADY_EXISTS, rack.getLocationIdentifier());
    }

    public void deleteRackById(Long id) {
        Rack rack = findRack(id);
        if (rack.getBookItems().size() == 0) {
            rackRepository.deleteById(id);
        } else throw new RackException(Message.RACK_CANNOT_BE_DELETED, rack.getLocationIdentifier());
    }

    private Rack findRack(Long id) {
        return rackRepository.findById(id)
                .orElseThrow(() -> new RackNotFoundException(id));
    }
}
