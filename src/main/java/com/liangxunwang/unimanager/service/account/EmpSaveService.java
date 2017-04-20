package com.liangxunwang.unimanager.service.account;


import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpKuDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhl on 2015/6/11.
 */
@Service("empSaveService")
public class EmpSaveService implements SaveService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    public Object save(Object object) throws ServiceException {
        List<Emp> dataList = new ArrayList<Emp>();
        String filePath = (String) object;
        DataFormatter formatter = new DataFormatter();
        String account = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()){
                Emp data = new Emp();
                Row row = rowIterator.next();
                if (row.getRowNum()>0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                data.setNickname(cell.getStringCellValue().replace(" ", ""));
                                break;
                            case 1:
                                data.setMobile(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 2:
                                data.setCover(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 3:
                                data.setAge(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 4:
                                data.setSex(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 5:
                                data.setHeightl(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 6:
                                data.setEducation(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 7:
                                data.setMarriage(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 8:
                                data.setCompany(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 9:
                                data.setLikeids(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 10:
                                data.setState(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                            case 11:
                                data.setCardpic(formatter.formatCellValue(cell).replace(" ", ""));
                                break;
                        }
                    }
                    data.setEmpid(UUIDFactory.random());
                    data.setDateline(System.currentTimeMillis()+"");
                    data.setIs_use("1");
                    data.setRzstate1("0");
                    data.setRzstate2("0");
                    data.setRzstate3("0");
                    data.setProvinceid("370000");
                    data.setCityid("371600");
                    data.setPassword("96e79218965eb72c92a549dd5a330112");
                    dataList.add(data);
                }
            }
            empDao.saveList(dataList);
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            throw new ServiceException("SAVE_ERROR");
        }
        return null;
    }

}
