package com.projeto_integrador.projeto_integrador.modules.schedule.export;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;

import jakarta.servlet.http.HttpServletResponse;

public class ExportToPDF {

    public void generate(List<ScheduleEntity> scheduleList, HttpServletResponse response) throws DocumentException, IOException {
        // Creating the Object of Document
        Document document = new Document(PageSize.A4);
        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, response.getOutputStream());
        // Opening the created document to change it
        document.open();
        // Creating font
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        // Creating paragraph
        Paragraph paragraph1 = new Paragraph("List of the Students", fontTitle);
        // Aligning the paragraph in the document
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        // Adding the created paragraph in the document
        document.add(paragraph1);
        // Creating a table with 5 columns
        PdfPTable table = new PdfPTable(5);
        // Setting width of the table, its columns, and spacing
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 3, 3, 3, 3});
        table.setSpacingBefore(5);
        // Create Table Cells for the table header
        PdfPCell cell = new PdfPCell();
        // Setting the background color and padding of the table cell
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);
        // Creating font for header
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);
        // Adding headings in the created table cell or header
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Teacher Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Subject", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Time", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Room", font));
        table.addCell(cell);
        // Iterating the list of ScheduleEntity
        for (ScheduleEntity schedule : scheduleList) {
            table.addCell(String.valueOf(schedule.getScheduleId()));
            table.addCell(String.valueOf(schedule.getTeacher()));
            table.addCell(String.valueOf(schedule.getSubject()));
            table.addCell(String.valueOf(schedule.getTime()));
            table.addCell(String.valueOf(schedule.getRoom()));
        }
        // Adding the created table to the document
        document.add(table);
        // Closing the document
        document.close();
    }
}
