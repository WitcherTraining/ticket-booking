package com.epam.ticket.dao.impl;

import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.TicketsContainer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class XmlUnmarshaller {
    private static final Logger LOGGER = Logger.getLogger(XmlUnmarshaller.class);

    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Autowired
    public XmlUnmarshaller(final Unmarshaller unmarshaller, final Marshaller marshaller) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
    }

    public void saveTickets(String pathToFile) throws IOException {
        final TicketsContainer tickets = new TicketsContainer();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(pathToFile);
            this.marshaller.marshal(tickets, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public List<TicketImpl> preLoadTickets(String pathToFile) throws IOException {
        TicketsContainer tickets = new TicketsContainer();
        try (final FileInputStream is = new FileInputStream(pathToFile)) {
            tickets = (TicketsContainer)this.unmarshaller.unmarshal(new StreamSource(is));
        }
        tickets.getTickets().forEach(ticket ->  LOGGER.info("Another unmarshalled ticket: " + ticket));
        return tickets.getTickets();
    }
}
