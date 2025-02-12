package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.ReturnFiliereDto;
import com.pfe.projetpfe.Dto.ReturnModuleDto;
import com.pfe.projetpfe.repository.ModuleRepositorie;
import org.springframework.stereotype.Service;

import com.pfe.projetpfe.entity.Module;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfesseurServiceImp implements ProfesseurService {
    ModuleRepositorie moduleRepositorie;

    public ProfesseurServiceImp(ModuleRepositorie moduleRepositorie) {
        this.moduleRepositorie = moduleRepositorie;
    }

    @Override
    public List<ReturnModuleDto> findByProfesseurId(Long professeurId) {
        List<Module> modules=moduleRepositorie.findByProfesseurId(professeurId);
        List<ReturnModuleDto> moduleListDto=new ArrayList<>();
        for (Module module : modules) {
            ReturnModuleDto returnModuleDto=new ReturnModuleDto();
            returnModuleDto.setId(module.getModuleId());
            returnModuleDto.setName(module.getModuleName());
            returnModuleDto.setSemestre(module.getSemestre());
            returnModuleDto.setFiliereName(module.getFiliere().getNomFiliere());
            moduleListDto.add(returnModuleDto);
        }
        return moduleListDto;
    }

}
