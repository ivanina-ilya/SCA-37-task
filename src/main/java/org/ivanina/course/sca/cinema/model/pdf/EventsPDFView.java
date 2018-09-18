package org.ivanina.course.sca.cinema.model.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import org.ivanina.course.sca.cinema.domain.Event;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class EventsPDFView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(
            Map<String, Object> map,
            Document document,
            PdfWriter pdfWriter,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Set<Event> events = (Set<Event>) map.get("data");

        document.add(new Paragraph(new Chunk("Cinema APP. List of Events.", FontFactory.getFont(FontFactory.HELVETICA, 20))));
        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);

        events.forEach(event -> {
            try {
                document.add(new Paragraph(String.format("ID: %10d", event.getId())));
                document.add(new Paragraph(String.format("Name: %10s", event.getName())));
                document.add(new Paragraph(String.format("Price: %10s", event.getPrice())));
                document.add(new Paragraph(String.format("Duration: %2d", event.getDuration())));
                document.add(linebreak);
            } catch (BadElementException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });

    }
}
