package org.ivanina.course.sca.cinema.model.pdf;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.ivanina.course.sca.cinema.domain.User;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;


public class UsersPDFView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(
            Map<String, Object> map,
            Document document,
            PdfWriter pdfWriter,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Set<User> users = (Set<User>) map.get("data");

        document.add(new Paragraph(new Chunk("Cinema APP. List of Users.", FontFactory.getFont(FontFactory.HELVETICA, 20))));

        Table table = new Table(4);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Email");
        table.addCell("birthday");

        users.forEach(user -> {
            try {
                table.addCell(user.getId().toString());
                table.addCell(user.getFirstName() + " " + user.getLastName());
                table.addCell(user.getEmail());
                table.addCell(user.getBirthday().toString());
            } catch (BadElementException e) {
                e.printStackTrace();
            }
        });
        document.add(table);
    }
}
