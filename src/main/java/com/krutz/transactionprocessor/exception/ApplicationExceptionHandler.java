package com.krutz.transactionprocessor.exception;

import com.krutz.transactionprocessor.dto.response.ErrorResponse;
import com.krutz.transactionprocessor.dto.response.TransactionErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ApplicationExceptionHandler {


	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<TransactionErrorResponse> handleValidationException(final ValidationException exception) {
		return new ResponseEntity(exception.getErrorResponse(),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TransactionNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleNotFoundException(final TransactionNotFoundException exception) {
		return new ResponseEntity(getErrorResponse(exception),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleArgumentMismatchException(final MethodArgumentTypeMismatchException exception) {
		return new ResponseEntity(getErrorResponse(exception),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleMessageNotReadableException(final HttpMessageNotReadableException exception) {
		return new ResponseEntity(getErrorResponse(exception),HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity handelException(final Exception e) {
		log.error("handelException",e);
		ErrorResponse errorResponse = getErrorResponse(e);
		return new ResponseEntity(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponse getErrorResponse(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setRemarks(e.getMessage());
		return errorResponse;
	}
}
