package com.example.hospital_management.web;

import com.example.hospital_management.entities.Patient;
import com.example.hospital_management.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class Patientcontroller {
    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "3") int size,
                        @RequestParam(name = "kw", defaultValue = "") String kw){
        Page<Patient> patientPage = patientRepository.findByNameContains(kw,PageRequest.of(page,size));
        model.addAttribute("ListPatients", patientPage.getContent());
        model.addAttribute("pages", new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(Long id){
        patientRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index";
    }

    @PostMapping("/editPatient")
    public String editPatient(Model model, Long id){
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient Not Found");
        model.addAttribute("patient", patient);
        return "editPatient";
    }


}
