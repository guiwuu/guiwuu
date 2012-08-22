package com.guiwuu.swissknife.converter.pdf;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactoryImp;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Converter to pdf file
 *
 * @author daijun
 * @sine 2012-03-21
 */
public class PdfConverter {

    private static final Logger log = LoggerFactory.getLogger(PdfConverter.class);

    private static final int PDF_MARGIN_LEFT = 25;
    private static final int PDF_MARGIN_RIGHT = 25;
    private static final int PDF_MARGIN_TOP = 50;
    private static final int PDF_MARGIN_BOTTOM = 50;

    /**
     * 用velocity生成html内容
     *
     * @param context
     * @param reader
     * @return
     */
    public static String buildHtmlByVelocity(VelocityContext context, Reader reader) {
        StringWriter writer = new StringWriter();
        try {
            Velocity.evaluate(context, writer, "", reader);
        } catch (Exception e) {
            log.error("生成html时候发生异常", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("关闭html输出流时发生异常", e);
            }
        }
        return writer.toString();
    }

    /**
     * 从html内容生成pdf文件
     *
     * @param html
     * @return
     */
    public static byte[] buildPdfFromHtml(String html) {
        Document document = new Document(PageSize.A4.rotate(), PDF_MARGIN_LEFT, PDF_MARGIN_RIGHT, PDF_MARGIN_TOP, PDF_MARGIN_BOTTOM);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter pdf = null;
        try {
            // 解析html
            StyleSheet styles = new StyleSheet();
            FontFactoryImp fontFactory = new FontFactoryImp() {
                @Override
                public Font getFont(String fontName, String encoding, boolean embedded, float size, int style, BaseColor color, boolean cached) {
                    return super.getFont("STSong-Light", "UniGB-UCS2-H", embedded, size, style, color, cached);
                }
            };
            HashMap<String, Object> providers = new HashMap<String, Object>();
            providers.put(HTMLWorker.FONT_PROVIDER, fontFactory);
            List<Element> elements = HTMLWorker.parseToList(new StringReader(html), styles, providers);

            // 输出pdf文件
            pdf = PdfWriter.getInstance(document, bos);
            document.open();
            for (Element element : elements) {
                document.add(element);
            }
        } catch (Exception e) {
            log.error("生成pdf文件时候发生异常", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            if (pdf != null) {
                pdf.close();
            }
            try {
                bos.close();
            } catch (IOException e) {
                log.error("关闭pdf文件时候发生异常", e);
            }
        }
        return bos.toByteArray();
    }

}
