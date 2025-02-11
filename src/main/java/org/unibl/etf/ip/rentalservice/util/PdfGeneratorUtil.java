package org.unibl.etf.ip.rentalservice.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import org.unibl.etf.ip.rentalservice.model.requests.RentalRequest;

@Component
public class PdfGeneratorUtil {

    public static byte[] generateInvoicePdf(int id, RentalRequest rentalRequest) {
        try {
            //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //PdfWriter writer = new PdfWriter(outputStream);
            //PdfDocument pdf = new PdfDocument(writer);
            //Document document = new Document(pdf);

            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add invoice details
            document.add(new Paragraph("Rental Invoice"));
            document.add(new Paragraph("Invoice ID: " + id));
            document.add(new Paragraph("Client ID: " + rentalRequest.getClientId()));
            document.add(new Paragraph("Vehicle ID: " + rentalRequest.getVehicleId()));
            document.add(new Paragraph("Start Date: " + rentalRequest.getStartDate()));
            document.add(new Paragraph("End Date: " + rentalRequest.getEndDate()));
            document.add(new Paragraph("Total Duration: " + rentalRequest.getTotalDurationMinutes() + " minutes"));
            //document.add(new Paragraph("Total Price: $" + rentalRequest.getTotalPrice()));

            document.close(); // Close the document to write content to the stream

            return outputStream.toByteArray(); // Return the generated PDF as a byte array
        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice PDF", e);
        }
    }
}
