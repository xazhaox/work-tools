package com.xazhao.excel.interceptor;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.xazhao.core.exception.BusinessErrorCode;
import com.xazhao.core.exception.BusinessException;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description Created on 2024/12/12.
 * @Author Zhao.An
 */

public class CustomWaterMarkHandler implements SheetWriteHandler {

    /**
     * 水印内容
     */
    public static final String WATERMARK_CONTENT = "MySQL生成内容";

    public static ByteArrayOutputStream createWaterMark(String content) throws IOException {
        int width = 400;
        int height = 200;
        // 获取bufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        String fontType = "微软雅黑";
        int fontStyle = Font.BOLD;
        int fontSize = 18;
        Font font = new Font(fontType, fontStyle, fontSize);
        // 获取Graphics2d对象
        Graphics2D g2d = image.createGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        // 设置字体颜色和透明度，最后一个参数为透明度
        g2d.setColor(new Color(0, 0, 0, 12));
        // 设置字体
        g2d.setStroke(new BasicStroke(1));
        // 设置字体类型  加粗 大小
        g2d.setFont(font);
        // 设置倾斜度
        g2d.rotate(-0.3, (double) image.getWidth() / 2, (double) image.getHeight() / 2);
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        // 写入水印文字原定高度过小，所以累计写水印，增加高度
        g2d.drawString(content, (int) x, (int) baseY);
        // 设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // 释放对象
        g2d.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return os;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (!(writeSheetHolder.getSheet() instanceof XSSFSheet)) {
            throw new BusinessException(BusinessErrorCode.TABLE_MUST_BE_AN_XSSF_SHEET);
        }
        try (ByteArrayOutputStream waterMark = createWaterMark(WATERMARK_CONTENT)) {
            XSSFSheet sheet = (XSSFSheet) writeSheetHolder.getSheet();
            XSSFWorkbook workbook = sheet.getWorkbook();
            int watermarkIndex = workbook.addPicture(waterMark.toByteArray(), Workbook.PICTURE_TYPE_PNG);
            String relationId = sheet.addRelation(null,
                    XSSFRelation.IMAGES, workbook.getAllPictures().get(watermarkIndex)).getRelationship().getId();
            sheet.getCTWorksheet().addNewPicture().setId(relationId);
        } catch (IOException e) {
            throw new BusinessException(BusinessErrorCode.WATERMARK_CREATION_FAILURE);
        }
    }

}
