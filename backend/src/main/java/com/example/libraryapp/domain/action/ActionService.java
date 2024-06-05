package com.example.libraryapp.domain.action;

import com.example.libraryapp.domain.config.assembler.ActionModelAssembler;
import com.example.libraryapp.domain.exception.action.ActionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;
    private final ActionModelAssembler actionModelAssembler;
    private final PagedResourcesAssembler<Action> pagedResourcesAssembler;

    public PagedModel<ActionDto> findActions(Long memberId, String type, Pageable pageable) {
        Page<Action> actionPage = actionRepository.findAllByParams(memberId, type, pageable);
        return pagedResourcesAssembler.toModel(actionPage, actionModelAssembler);
    }

    public ActionDto findActionById(Long id) {
        Action action = findAction(id);
        return actionModelAssembler.toModel(action);
    }

    private Action findAction(Long id) {
        return actionRepository.findById(id)
                .orElseThrow(() -> new ActionNotFoundException(id));
    }
}
