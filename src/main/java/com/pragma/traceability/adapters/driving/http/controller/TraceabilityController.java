package com.pragma.traceability.adapters.driving.http.controller;

import com.pragma.traceability.adapters.driving.http.dto.request.LogsOrderRequestDto;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.adapters.driving.http.dto.response.TimeOrdersEmployeeResponseDto;
import com.pragma.traceability.adapters.driving.http.handler.ILogsHandler;
import com.pragma.traceability.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/traceability/")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class TraceabilityController {

    private final ILogsHandler logsHandler;
    @Operation(summary = "Add a new Log",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Log created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Log already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("logs/")
    public ResponseEntity<Map<String, String>> createLogs(@Valid @RequestBody LogsOrderRequestDto logsOrderRequestDto){
        logsHandler.createLogs(logsOrderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.LOGS_CREATED));
    }

    @Operation(summary = "Logs List Client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Logs success",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Error N@",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PreAuthorize("hasAuthority('CLIENT_ROLE')")
    @GetMapping("logs/record/")
    public ResponseEntity<List<LogsOrderResponseDto>> getLogsOrder(){
        return ResponseEntity.ok(logsHandler.getLogsOrderByClient());
    };

    @Operation(summary = "Order time",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful time",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Error time",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PreAuthorize("hasAuthority('PROVIDER_ROLE')")
    @GetMapping("logs/time/{idOrder}")
    public ResponseEntity<Long> getTimeOrder(@PathVariable("idOrder") Long idOrder){
        return ResponseEntity.ok(logsHandler.getTimeOrder(idOrder));
    }

    @Operation(summary = "Times employees ranked",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful Ranked",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Error Ranked",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PreAuthorize("hasAuthority('PROVIDER_ROLE')")
    @GetMapping("logs/time/employee/ranked/{idEmployee}")
    public ResponseEntity<List<TimeOrdersEmployeeResponseDto>> getTimeEmployeeRanked(@PathVariable("idEmployee") Long idEmployee){
        return ResponseEntity.ok(logsHandler.getTimeEmployeeRanked(idEmployee));
    }



}
