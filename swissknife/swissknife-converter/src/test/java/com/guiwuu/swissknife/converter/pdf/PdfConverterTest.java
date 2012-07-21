package com.guiwuu.swissknife.converter.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

/**
 * PdfConverterTest
 *
 * @author daijun
 * @sine 2012-07-22
 */
public class PdfConverterTest {

    @Test
    public void testConvert() throws Exception {
        String vm = this.getClass().getResource("/pdf.vm").getFile();
        VelocityContext context = new VelocityContext();
        context.put("title", "title");
        context.put("content", "内容");
        String html = PdfConverter.buildHtmlByVelocity(context, new FileReader(vm));
        byte[] bytes = PdfConverter.buildPdfFromHtml(html);
        FileUtils.writeByteArrayToFile(new File("d:/tmp/pdf/out.pdf"), bytes);
    }

    @Test
    public void testHtmlToPdf() throws Exception {
        File sample = new File(this.getClass().getResource("/sample.html").getFile());
        String html = FileUtils.readFileToString(sample);

        StyleSheet styles = new StyleSheet();
        HashMap<String, Object> providers = new HashMap<String, Object>();
        providers.put(HTMLWorker.FONT_PROVIDER, new FontFactoryImp() {
            @Override
            public Font getFont(String fontName, String encoding, boolean embedded,
                                float size, int style, BaseColor color, boolean cached) {
                return super.getFont("STSong-Light", "UniGB-UCS2-H", embedded, size, style, color, cached);
            }
        });
        List<Element> objects = HTMLWorker.parseToList(new StringReader(html), styles, providers);

        // 初始化pdfwriter
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        FileOutputStream fos = new FileOutputStream("d:/tmp/pdf/sample.pdf");
        PdfWriter pdf = PdfWriter.getInstance(document, fos);

        // 输出pdf文件
        document.open();
        for (Element element : objects) {
            document.add(element);
        }
        document.close();
        pdf.close();
        fos.close();
    }
}
