package com.epam.ticket.controller;

import com.epam.ticket.facade.api.BookingFacade;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TicketSpecificController {

    public static final String TICKETS_PDF = "myTickets.pdf";
    public static final String APPLICATION_PDF = "application/pdf";
    public static final String OUTPUT_NAME = "tickets.pdf";
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String INLINE_FILENAME = "inline;filename=";
    public static final String CACHE_CONTROL_OPTIONS = "must-revalidate, post-check=0, pre-check=0";
    private BookingFacade bookingFacade;

    public TicketSpecificController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/ticketsByEvent/{eventId}")
    public ResponseEntity<byte[]> getBookedTicketsByEvent(@RequestParam int eventId, Model model) throws DocumentException, IOException {
        final Event event = this.bookingFacade.getEventById(eventId);
        final List<Ticket> tickets = this.bookingFacade.getBookedTickets(event, 1, 1);
        model.addAttribute("tickets", tickets);
        constructPDF(tickets);
        return getResponseEntity();
    }

    @GetMapping("/ticketsByUser/{userId}")
    public ResponseEntity<byte[]> getBookedTicketsByUser(@PathVariable int userId, Model model) throws IOException, DocumentException {
        final User user = this.bookingFacade.getUserById(userId);
        final List<Ticket> tickets = this.bookingFacade.getBookedTickets(user, 1, 1);
        model.addAttribute("tickets", tickets);
        constructPDF(tickets);
        return getResponseEntity();
    }

    private void constructPDF(final List<Ticket> tickets) throws DocumentException, FileNotFoundException {
        final Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(TICKETS_PDF));
        document.open();
        final Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        tickets.forEach(ticket -> {
            final Chunk chunk = new Chunk(ticket.toString(), font);
            try {
                document.add(chunk);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        document.close();
    }

    private ResponseEntity<byte[]> getResponseEntity() throws IOException {
        final Path pdfPath = Paths.get(TICKETS_PDF);
        final byte[] outputPDF = Files.readAllBytes(pdfPath);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(APPLICATION_PDF));
        final String outputFilename = OUTPUT_NAME;
        headers.add(CONTENT_DISPOSITION, INLINE_FILENAME + outputFilename);

        headers.setContentDispositionFormData(outputFilename, outputFilename);
        headers.setCacheControl(CACHE_CONTROL_OPTIONS);
        final ResponseEntity<byte[]> response = new ResponseEntity<>(outputPDF, headers, HttpStatus.OK);
        return response;
    }

}
