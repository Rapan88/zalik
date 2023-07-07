package com.example.zalik_rsd.service;

import com.example.zalik_rsd.modal.EmployeeAccess;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.time.LocalDate;
import java.util.List;


public class GeneratePDF {

    public GeneratePDF() {

    }

    private static final int BUFFER_SIZE = 4096;

    private String filePathPDF = "PDFFile.pdf";


    public void doDownload(HttpServletRequest request, HttpServletResponse response, java.util.List<EmployeeAccess> allEmployee) throws IOException, FileNotFoundException {

        createPDFFile(filePathPDF, allEmployee);

        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");    // construct the complete absolute path of the file
        String fullPath = filePathPDF;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);   // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {       // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);    // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());    // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);    // get output stream of the response
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;    // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outStream.close();
    }


    private void createPDFFile(String filePath, List<EmployeeAccess> allEmployee) throws IOException {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            String text = "Pryshchepa Anton   " + LocalDate.now() + " \n\n";

            Paragraph paragraph = new Paragraph(text);
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL));
            doc.add(paragraph);

            PdfPTable table = new PdfPTable(4);
            PdfPCell cell2 = new PdfPCell(new Phrase("Name"));
            PdfPCell cell1 = new PdfPCell(new Phrase("Entry time"));
            PdfPCell cell3 = new PdfPCell(new Phrase("Exit time"));
            PdfPCell cell4 = new PdfPCell(new Phrase("Room Number"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            for (EmployeeAccess employeeAccess : allEmployee) {
                PdfPCell enterpriseName = new PdfPCell(new Phrase(employeeAccess.getName()));
                PdfPCell cadastreNumber = new PdfPCell(new Phrase(String.valueOf((employeeAccess.getEntryTime()))));
                PdfPCell annualIncome = new PdfPCell(new Phrase(employeeAccess.getExitTime() + " "));
                PdfPCell landArea = new PdfPCell(new Phrase(employeeAccess.getRoomNumber() + " "));


                table.addCell(enterpriseName);
                table.addCell(cadastreNumber);
                table.addCell(annualIncome);
                table.addCell(landArea);

            }
            doc.add(table);
        } catch (DocumentException |
                 FileNotFoundException e) {
            e.printStackTrace();

        } finally {
            doc.close();
        }
    }


}
