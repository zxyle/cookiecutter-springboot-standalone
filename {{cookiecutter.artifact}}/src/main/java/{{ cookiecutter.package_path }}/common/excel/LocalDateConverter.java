package {{ cookiecutter.basePackage }}.common.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.*;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * EasyExcel LocalDate类型转换器
 */
@Component
public class LocalDateConverter implements Converter<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) {
        return LocalDate.parse(cellData.getStringValue(), FORMATTER);
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDate localDate, ExcelContentProperty excelContentProperty
            , GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(localDate.format(FORMATTER));
    }
}