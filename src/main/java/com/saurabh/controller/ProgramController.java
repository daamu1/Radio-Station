package com.saurabh.controller;

import com.saurabh.dto.ProgramDto;
import com.saurabh.entity.Advertisement;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.repository.ProgramRepository;
import com.saurabh.service.ProgrameImplement;
import com.saurabh.service.serviceimp.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @GetMapping("/programs")
    public List<ProgramDto> fetchAllPrograms() {

        return programService.fetchAllPrograms();
    }

    @GetMapping("/programs/{programId}")
    public ProgramDto fetchProgrambyId(@PathVariable Long programId) {
        return programService.fetchProgrambyId(programId);
    }

    @PostMapping("{stationId}/programs")
    public void addNewProgram(@PathVariable Long stationId, @RequestBody Program program) {
        programService.addNewProgramOnJockey(stationId, program);
    }

    @PutMapping("/programs/{programId}")
    public void updateProgram(@PathVariable Long programId, @RequestBody Program program) {
        programService.updateProgram(programId, program);
    }

    @DeleteMapping("/programs/{programId}")
    public void deleteProgram(@PathVariable Long programId) {
        programService.deleteProgram(programId);
    }

    @GetMapping("/programs/{programId}/hostBy")
    public List<RadioJockey> fetchAllJockey(@PathVariable Long programId) {
        return programService.fetchAllJockey(programId);
    }

    @GetMapping("/program")
    public ResponseEntity<List<Program>> getProgramByStationAndDate(@RequestParam("stationId") Long stationId, @RequestParam("playDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate playDate) {

        List<Program> programs = programService.findProgramByStationAndDate(stationId, playDate);

        return ResponseEntity.ok(programs);
    }

    @GetMapping("/filterprogram")
    public ResponseEntity<List<Program>> findProgramsByStationIdAndDateAndproductId(@RequestParam("stationId") Long stationId, @RequestParam("playDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate playDate, @RequestParam("programId") Long programId) {

        List<Program> programs = programService.findProgramsByStationIdAndDateAndproductId(stationId, playDate, programId);

        return ResponseEntity.ok(programs);
    }

    @GetMapping("/stationdata")
    public ResponseEntity<List<Program>> findProgramsByStationIdAndDateAndproductId(@RequestParam("stationId") Long stationId) {
        List<Program> programs = programService.fetchAllStationData(stationId);
        return ResponseEntity.ok(programs);
    }

}