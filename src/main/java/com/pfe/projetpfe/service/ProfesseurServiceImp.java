package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.*;
import com.pfe.projetpfe.entity.*;
import com.pfe.projetpfe.entity.Module;
import com.pfe.projetpfe.repository.FiliereRepository;
import com.pfe.projetpfe.repository.ModuleRepositorie;
import com.pfe.projetpfe.repository.ProfesseurRepository;
import com.pfe.projetpfe.repository.ResourcesRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesseurServiceImp implements ProfesseurService {
    ModuleRepositorie moduleRepositorie;
    FiliereRepository filiereRepository;
    ProfesseurRepository professeurRepository;
    ResourcesRepository resourcesRepository;


    public ProfesseurServiceImp(ModuleRepositorie moduleRepositorie, FiliereRepository filiereRepository,ProfesseurRepository professeurRepository,ResourcesRepository resourcesRepository) {
        this.moduleRepositorie = moduleRepositorie;
        this.filiereRepository = filiereRepository;
        this.professeurRepository = professeurRepository;
        this.resourcesRepository = resourcesRepository;
    }

    @Override
    public List<ReturnModuleDto> findModuleByProfesseurId(Long professeurId) {
        List<Module> modules=moduleRepositorie.findByProfesseurId(professeurId);
        List<ReturnModuleDto> moduleListDto=new ArrayList<>();
        for (Module module : modules) {
            ReturnModuleDto returnModuleDto=new ReturnModuleDto();
            returnModuleDto.setId(module.getModuleId());
            returnModuleDto.setName(module.getModuleName());
            returnModuleDto.setSemestre(module.getSemestre());
            returnModuleDto.setDescription(module.getDescription());
            returnModuleDto.setFiliereName(module.getFiliere().getNomFiliere());
            moduleListDto.add(returnModuleDto);
        }
        return moduleListDto;
    }
    /*
    Gestion des modules
    */
    @Override
    public ReturnModuleDto addModule(AddModuleDto moduleAjouterDto) {
        Optional<Filiere> filiere=filiereRepository.findByNomFiliere(moduleAjouterDto.getFiliereName());

        if(!filiere.isPresent()) {
            throw new RuntimeException("Filiere non trouvable");
        }
        Optional<Professeur> professeur=professeurRepository.findById(moduleAjouterDto.getIdProfesseur());
        Module module=new Module();
        module.setModuleName(moduleAjouterDto.getName());
        module.setSemestre(moduleAjouterDto.getSemestre());
        module.setFiliere(filiere.get());
        module.setDescription(moduleAjouterDto.getDescription());
        module.setProfesseur(professeur.get());

        Module savedModule=moduleRepositorie.save(module);

        ReturnModuleDto returnModuleDto=new ReturnModuleDto(savedModule.getModuleId(),savedModule.getModuleName(),savedModule.getSemestre(),savedModule.getFiliere().getNomFiliere(),savedModule.getDescription());
        return returnModuleDto;
    }

    @Override
    public ReturnModuleDto updateModule(ReturnModuleDto returnModuleDto) {
        Optional<Module> module =moduleRepositorie.findById(returnModuleDto.getId());
        if(!module.isPresent()) {
            new RuntimeException("Module non trouvable");
        }

        Optional<Filiere> filiere=filiereRepository.findByNomFiliere(returnModuleDto.getFiliereName());
        if(!filiere.isPresent()) {
            new RuntimeException("Filiere non trouvable");
        }

        Module updateMudole=module.get();
        updateMudole.setModuleName(returnModuleDto.getName());
        updateMudole.setSemestre(returnModuleDto.getSemestre());
        updateMudole.setFiliere(filiere.get());

        updateMudole =moduleRepositorie.save(updateMudole);

        ReturnModuleDto moduleDto=new ReturnModuleDto(updateMudole.getModuleId(),updateMudole.getModuleName(),updateMudole.getSemestre(),updateMudole.getFiliere().getNomFiliere(),updateMudole.getDescription());

        return moduleDto;
    }
    /*
    Gestion Resources
    */
    @Override
    public ResourceReturnDto addResources(AddResourceDto ajouteResourceDto) throws Exception{
        //determiner ci le module laquelle appartinet ces Resources existe ou pas
       Optional<Module> module=moduleRepositorie.findById(ajouteResourceDto.getModuleId());
       if(!module.isPresent()) {
           new RuntimeException("Module non trouvable");
       }
       //determiner ci le Professeur qui ajouter ces Resources existe ou pas
        Optional<Professeur> professeur=professeurRepository.findById(ajouteResourceDto.getProfessorId());
       if(!professeur.isPresent()) {
           new RuntimeException("Professeur non trouvable");
       }

       //determiner le type de resources(soit Fichier(MultipartFile) soit un lien Vidoe)
        DataType dataType=ajouteResourceDto.getDataType();
       if (dataType.equals(DataType.VIDEO)){
           //save Video
           Resources resources=new Resources();
           resources.setNom(ajouteResourceDto.getNom());
           resources.setType(ajouteResourceDto.getType());
           resources.setDataType(ajouteResourceDto.getDataType());
           resources.setLien(ajouteResourceDto.getLien());
           resources.setModule(module.get());
           resources.setProfesseur(professeur.get());
           Resources resources1=resourcesRepository.save(resources);
            //retourner la vedio
           ResourceReturnDto resourceReturnDto=new ResourceReturnDto();
           resourceReturnDto.setId(resources1.getIdResource());
           resourceReturnDto.setNom(resources1.getNom());
           resourceReturnDto.setLien(resources1.getLien());
           resourceReturnDto.setType(resources1.getType());
           resourceReturnDto.setDataType(resources1.getDataType());
           resourceReturnDto.setModuleName(resources1.getModule().getModuleName());
           resourceReturnDto.setModuleId(resources1.getModule().getModuleId());
           resourceReturnDto.setProfessorId(professeur.get().getId());

           String filiereName = resources1.getModule().getFiliere().getNomFiliere();
           resourceReturnDto.setFiliereName(filiereName);
           return resourceReturnDto;
       }else{
           Path path = Paths.get("src/main/resources/static/fichiers/" + ajouteResourceDto.getData().getOriginalFilename());
           // Assurez-vous que le répertoire existe
           Files.createDirectories(path.getParent());
           try {
               Files.copy(ajouteResourceDto.getData().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
               String filePath="/fichiers/"+ajouteResourceDto.getData().getOriginalFilename();

               // Sauvegarder le chemin dans la base de données
               Resources resources=new Resources();
               resources.setNom(ajouteResourceDto.getNom());
               resources.setType(ajouteResourceDto.getType());
               resources.setDataType(ajouteResourceDto.getDataType());
               resources.setLien(filePath);// chemin relatif
               resources.setModule(module.get());
               resources.setProfesseur(professeur.get());
               Resources resources1=resourcesRepository.save(resources);

               ResourceReturnDto resourceReturnDto=new ResourceReturnDto();
               resourceReturnDto.setId(resources1.getIdResource());
               resourceReturnDto.setNom(resources1.getNom());


               resourceReturnDto.setLien(resources1.getLien());

               resourceReturnDto.setType(resources1.getType());
               resourceReturnDto.setDataType(resources1.getDataType());
               resourceReturnDto.setModuleName(resources1.getModule().getModuleName());
               resourceReturnDto.setModuleId(resources1.getModule().getModuleId());
               String filiereName = resources1.getModule().getFiliere().getNomFiliere();
               resourceReturnDto.setFiliereName(filiereName);
               resourceReturnDto.setProfessorId(resources1.getProfesseur().getId());
               return resourceReturnDto;


           }catch (IOException e){
               e.printStackTrace();
           }
       }
        return null;
    }

     @Override
     public ResourceReturnDto updateResources(UpdateResourceDto updateResourceDto)  throws Exception{

         //verifier si resource exist
        Optional<Resources> resources=resourcesRepository.findById(updateResourceDto.getId());
        if(!resources.isPresent()) {
            new RuntimeException("Resource non trouvable");
        }
        //verifier si le module exist
         Optional<Module> module=moduleRepositorie.findById(updateResourceDto.getModuleId());
         if(!module.isPresent()) {
             new RuntimeException("Module non trouvable");
         }
         //determiner ci le Professeur qui ajouter ces Resources existe ou pas
         Optional<Professeur> professeur=professeurRepository.findById(updateResourceDto.getProfessorId());
         if(!professeur.isPresent()) {
             new RuntimeException("Professeur non trouvable");
         }

         //ici le resourece qui recuperer dans DB
         Resources updateResources=resources.get();
         //mettre a jour a ce Resources par les nouveaux donnes de requet
          //check le type de nouvaux resources
         DataType dataType=updateResourceDto.getDataType();
       if (dataType.equals(DataType.VIDEO)){
           //update Video
           updateResources.setNom(updateResourceDto.getNom());
           updateResources.setType(updateResourceDto.getType());
           updateResources.setDataType(updateResourceDto.getDataType());
           updateResources.setLien(updateResourceDto.getLien());
           updateResources.setModule(module.get());
           updateResources.setProfesseur(professeur.get());
           Resources resources1=resourcesRepository.save(updateResources);
            //retourner la vedio
           ResourceReturnDto resourceReturnDto=new ResourceReturnDto();
           resourceReturnDto.setId(resources1.getIdResource());
           resourceReturnDto.setNom(resources1.getNom());
           resourceReturnDto.setLien(resources1.getLien());
           resourceReturnDto.setType(resources1.getType());
           resourceReturnDto.setDataType(resources1.getDataType());
           resourceReturnDto.setModuleName(resources1.getModule().getModuleName());
           resourceReturnDto.setModuleId(resources1.getModule().getModuleId());
           resourceReturnDto.setProfessorId(professeur.get().getId());

           String filiereName = resources1.getModule().getFiliere().getNomFiliere();
           resourceReturnDto.setFiliereName(filiereName);
           return resourceReturnDto;
       }else{
           Path path = Paths.get("src/main/resources/static/fichiers/" + updateResourceDto.getData().getOriginalFilename());
           // Assurez-vous que le répertoire existe
           Files.createDirectories(path.getParent());
           try {
               Files.copy(updateResourceDto.getData().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
               String filePath="/fichiers/"+updateResourceDto.getData().getOriginalFilename();


               //update fichier
               updateResources.setNom(updateResourceDto.getNom());
               updateResources.setType(updateResourceDto.getType());
               updateResources.setDataType(updateResourceDto.getDataType());
               updateResources.setLien(filePath);// chemin relatif
               updateResources.setModule(module.get());
               updateResources.setProfesseur(professeur.get());
               Resources resources1=resourcesRepository.save(updateResources);
               //retourner fichier
               ResourceReturnDto resourceReturnDto=new ResourceReturnDto();
               resourceReturnDto.setId(resources1.getIdResource());
               resourceReturnDto.setNom(resources1.getNom());
               resourceReturnDto.setLien(resources1.getLien());
               resourceReturnDto.setType(resources1.getType());
               resourceReturnDto.setDataType(resources1.getDataType());
               resourceReturnDto.setModuleName(resources1.getModule().getModuleName());
               resourceReturnDto.setModuleId(resources1.getModule().getModuleId());
               String filiereName = resources1.getModule().getFiliere().getNomFiliere();
               resourceReturnDto.setFiliereName(filiereName);
               resourceReturnDto.setProfessorId(resources1.getProfesseur().getId());
               return resourceReturnDto;
           }catch (IOException e){
               e.printStackTrace();
           }
       }
        return null;

     }

    @Override
    public List<ResourceReturnDto> getAllResources() throws Exception {
        List<Resources> resources=resourcesRepository.findAll();
        if (resources.isEmpty()){
            new RuntimeException("aucune ressource trouver au moment");
        }
        List<ResourceReturnDto> resourceReturnDtos=new ArrayList<>();
        for(Resources resource:resources){
            ResourceReturnDto resourceReturnDto=new ResourceReturnDto();
            resourceReturnDto.setId(resource.getIdResource());
            resourceReturnDto.setNom(resource.getNom());
            resourceReturnDto.setDataType(resource.getDataType());
            resourceReturnDto.setLien(resource.getLien());
            resourceReturnDto.setType(resource.getType());
            resourceReturnDto.setModuleName(resource.getModule().getModuleName());
            resourceReturnDto.setModuleId(resource.getModule().getModuleId());
            String filiereName = resource.getModule().getFiliere().getNomFiliere();
            resourceReturnDto.setFiliereName(filiereName);
            resourceReturnDto.setProfessorId(resource.getProfesseur().getId());
            resourceReturnDtos.add(resourceReturnDto);
        }
        return resourceReturnDtos;
    }

}
