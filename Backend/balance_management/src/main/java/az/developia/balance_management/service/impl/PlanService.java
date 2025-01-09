package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.PlanAddRequest;
import az.developia.balance_management.dto.request.PlanUpdateRequest;
import az.developia.balance_management.dto.response.PlanResponse;
import az.developia.balance_management.dto.response.PlanSingleResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.model.PlanEntity;
import az.developia.balance_management.repository.PlanRepository;
import az.developia.balance_management.service.inter.PlanInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService implements PlanInter {

    @Autowired
    private PlanRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void add(PlanAddRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PlanEntity plan = new PlanEntity();
        if (request.getBeginDate().isBefore(request.getEndDate())) {
            if (checkDates(request.getBeginDate(), request.getEndDate(), username) == null) {
                mapper.map(request, plan);
                plan.setUsername(username);
                repository.save(plan);
            } else {
                throw new MyException("There is a plan between dates! You can have only one plan per a period!");
            }
        } else {
            throw new MyException("BeginDate must be before from EndDate");
        }
    }

    @Override
    public PlanResponse findPlans() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PlanEntity> entities = repository.findByUsername(username);
        return converter(entities);
    }

    @Override
    public void update(PlanUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<PlanEntity> optional = repository.findById(request.getId());

        if(optional.isPresent()) {
            PlanEntity plan = optional.get();
            if (plan.getUsername().equals(username)) {
                if (request.getBeginDate().isBefore(request.getEndDate())) {
                    PlanEntity checkedPlan = checkDates(request.getBeginDate(), request.getEndDate(), username);
                    if (checkedPlan == null) {
                        mapper.map(request, plan);
                        repository.save(plan);
                    } else if (checkedPlan.getId().equals(request.getId())) {
                        mapper.map(request, plan);
                        repository.save(plan);
                    } else {
                        throw new MyException("There is a plan between dates! You can have only one plan per a period!");
                    }
                } else {
                    throw new MyException("BeginDate must be before from EndDate");
                }
            } else {
                throw new MyException("The plan wasn't found!");
            }
        } else {
            throw new MyException("The plan wasn't found!");
        }
    }

    @Override
    public void delete(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<PlanEntity> optional = repository.findById(id);

        if (optional.isPresent()) {
            PlanEntity plan = optional.get();
            if (plan.getUsername().equals(username)) {
                repository.delete(plan);
            } else {
                throw new MyException("The plan wasn't found!");
            }
        } else {
            throw new MyException("The plan wasn't found!");
        }
    }

    @Override
    public PlanResponse findPlan(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<PlanEntity> optional = repository.findById(id);
        List<PlanEntity> entities = new ArrayList<>();

        if (optional.isPresent()) {
            PlanEntity plan = optional.get();
            if (plan.getUsername().equals(username)) {
                entities.add(plan);
            } else {
                throw new MyException("The plan wasn't found!");
            }
        } else {
            throw new MyException("The plan wasn't found!");
        }

        return converter(entities);
    }

    private PlanResponse converter(List<PlanEntity> entities) {
        PlanResponse response = new PlanResponse();
        List<PlanSingleResponse> responseList = new ArrayList<>();

        for (PlanEntity e : entities) {
            PlanSingleResponse singleResponse = new PlanSingleResponse();
            mapper.map(e, singleResponse);
            responseList.add(singleResponse);
        }

        response.setPlans(responseList);
        return response;
    }

    private PlanEntity checkDates(LocalDate beginDate, LocalDate endDate, String username) {
        return repository.findByDates(beginDate, endDate, username);
    }

}
