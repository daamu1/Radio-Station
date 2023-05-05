package com.saurabh.controller;

import com.saurabh.dto.ProgramDto;
import com.saurabh.entity.Program;
import com.saurabh.entity.RadioJockey;
import com.saurabh.service.serviceimp.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("{stationId}/jockeys/{jockeyId}/programs")
    public void addNewProgram(@PathVariable Long stationId, @PathVariable Long jockeyId, @RequestBody Program program) {
        programService.addNewProgramOnJockey(stationId, jockeyId, program);
    }

    @PutMapping("{stationId}/jockeys/{jockeyId}/programs/{programId}")
    public void updateProgram(@PathVariable Long stationId, @PathVariable Long programId, @PathVariable Long jockeyId, @RequestBody Program program) {
        programService.updateProgram(stationId, jockeyId, programId, program);
    }

    @DeleteMapping("/programs/{programId}")
    public void deleteProgram(@PathVariable Long programId) {
        programService.deleteProgram(programId);
    }

    //Radio jockeys attached to Program.
    @GetMapping("/programs/{programId}/hostBy")
    public List<RadioJockey> fetchAllJockey(@PathVariable Long programId) {
        return programService.fetchAllJockey(programId);
    }

    //program played on particular station on perticular date
    @GetMapping("/program")
    public ResponseEntity<List<Program>> getProgramByStationAndDate(@RequestParam("stationId") Long stationId, @RequestParam("playDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate playDate) {

        List<Program> programs = programService.findProgramByStationAndDate(stationId, playDate);

        return ResponseEntity.ok(programs);
    }

    //make filters for getting data on behalf of date, stationId, programId
    @GetMapping("/filterprogram")
    public ResponseEntity<List<Program>> findProgramsByStationIdAndDateAndproductId(@RequestParam("stationId") Long stationId, @RequestParam("playDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate playDate, @RequestParam("programId") Long programId) {
        List<Program> programs = programService.findProgramsByStationIdAndDateAndproductId(stationId, playDate, programId);
        return ResponseEntity.ok(programs);
    }

    //which Radio Jockey will play program on which station and when details
    @GetMapping("/program/{programId}/details")
    public Optional<Program> findProgramsDetailsProgramId(@PathVariable("programId") Long programId) {
        return programService.findRadioJockeyAndStationByProgramId(programId);
    }

}