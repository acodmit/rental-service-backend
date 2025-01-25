package org.unibl.etf.ip.rentalservice.exceptions;

import org.springframework.http.HttpStatus;
// Exception for HTTP 404 (Not Found)
public class NotFoundException extends HttpException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, null);
    }

    public NotFoundException(Object data) {
        super(HttpStatus.NOT_FOUND, data);
    }
}