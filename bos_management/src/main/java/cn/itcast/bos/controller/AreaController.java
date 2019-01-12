package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import cn.itcast.bos.test.PinYinDuoYinUtils;
import cn.itcast.bos.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/areaController")
public class AreaController {

    @Autowired
    private AreaService areaService;


    /**
     * 批量导入区域数据
     *
     * @param file
     */
    @RequestMapping("/batchImport")
    public void batchImport(MultipartFile file) throws IOException {
        List areas = new ArrayList();
        //编写解析代码逻辑
        //基于.xls格式解析HSSF
        //1.加载Excel文件对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
        //2.读取一个sheet
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //3.读取sheet中每一行
        for (Row row : sheet) {
            //一行数据,对应一个区域对象
            if (row.getRowNum() == 0) {
                //第一行 跳过
                continue;
            }
            if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                //跳过空行
                continue;
            }
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());//区域编码
            area.setProvince(row.getCell(1).getStringCellValue());//省份
            area.setCity(row.getCell(2).getStringCellValue());//城市
            area.setDistrict(row.getCell(3).getStringCellValue());//区域
            area.setPostcode(row.getCell(4).getStringCellValue());//邮编

            //基于拼音4j生成城市编码和简码
            String province = area.getProvince();//省份
            String city = area.getCity();//城市
            String district = area.getDistrict();//区域
            //简码需要去掉最后一个字(省,市,区)
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            //简码
            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer buffer = new StringBuffer();
            for (String s : headArray) {
                buffer.append(s);
            }
            String shortcode = buffer.toString();//简码
            area.setShortcode(shortcode);

            //城市编码  第二个参数:表示用什么分离
            //String citycode = PinYin4jUtils.hanziToPinyin(city, "");//不能解决多音字问题
            String citycode = PinYinDuoYinUtils.convertChineseToPinyin(city, false);//解决了多音字问题
            area.setCitycode(citycode);

            areas.add(area);
        }
        areaService.savaBatch(areas);
    }

    /**
     * 分页查询+条件查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/pageQuery")
    public Map pageQuery(int page, int rows, Area area) {
        //构架分页查询条件
        Pageable pageable = PageRequest.of(page - 1, rows);

        //构建查询条件
        Specification<Area> spec = new Specification<Area>() {
            @Override
            //cb:构建查询，添加查询方式   like：模糊匹配
            //root：从实体Customer对象中按照custName属性进行查询
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> province = root.get("province");//省份
                Path<Object> city = root.get("city");//市
                Path<Object> district = root.get("district");//区域

                List<Predicate> list = new ArrayList();//储存查询条件
                //构造查询
                if (StringUtils.isNotBlank(area.getProvince())) {
                    //省份
                    Predicate p1 = cb.like(province.as(String.class), "%" + area.getProvince() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(area.getCity())) {
                    //市
                    Predicate p2 = cb.like(city.as(String.class), "%" + area.getCity() + "%");
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(area.getDistrict())) {
                    //区域
                    Predicate p3 = cb.like(district.as(String.class), "%" + area.getDistrict() + "%");
                    list.add(p3);
                }
                //将查询条件组合到一起
                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Page<Area> pageData = areaService.findPageData(spec, pageable);
        //返回客户端数据  需要total和rows
        HashMap<String, Object> result = new HashMap();
        result.put("total", pageData.getTotalElements());//总条数
        result.put("rows", pageData.getContent());
        return result;
    }
}
