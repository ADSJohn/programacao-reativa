package com.br.boracodardevs.programacaoreativa.exceptions;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class StandardError implements Serializable {

	@Serial
	private static final long serialversionUID = 1L;

	private LocalDateTime timestamp;
	private String path;
	private Integer status;
	private String error;
	private String message;
}
