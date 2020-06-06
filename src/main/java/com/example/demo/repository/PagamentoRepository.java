package com.example.demo.repository;

import com.example.demo.entities.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Repository
public class PagamentoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    
    private final int minPageSize = 10;
    private final int maxPageSize = 20;

    private final int maxPageNumber = 5;

    private final String[] enumEntries = {
        "ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX", "YZA", "BCD"
    };
    private final int enumEntriesQty = enumEntries.length;



    public Collection<Pagamento> insertAll(Collection<Pagamento> newPagamentos){
        return mongoTemplate.insertAll(newPagamentos);
    }

    
    public List<Pagamento> getAll(){
        long timeStart = System.currentTimeMillis();
        List<Pagamento> list = mongoTemplate.findAll(Pagamento.class);
        long count = list.size();
        long timeStop = System.currentTimeMillis();
        System.out.println("Q: FIND-ALL \t| TIME: " + (timeStop-timeStart) + "ms \t| COUNT: " + count);
        return list;
    }

    
    public List<Pagamento> getWithFilter(){
        final String enumEntry = enumEntries[(int)(Math.random() * enumEntriesQty)];

        final int startMonth = (int)(1+Math.random() * (12-1));
        final int endMonth = (int)(startMonth + Math.random() * (12 - startMonth));
        final String startDateStr = "2020-" + ((startMonth<10) ? "0" + startMonth:startMonth) + "-01";
        final String endDateStr = "2020-" + ((endMonth<10) ? "0" + endMonth:endMonth) + "-01";
        final LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        final LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

        final Sort.Direction sortDirection = Math.random() >= 0.5 ? Sort.Direction.ASC : Sort.Direction.DESC;
        final int page = (int) (Math.random() * maxPageNumber);
        final int pageSize = (int) (minPageSize + Math.random() * (maxPageSize - minPageSize));
        
        long timeStart = System.currentTimeMillis();        
        final Query query = new Query(
                Criteria
                        .where("type").is(enumEntry)
                        .and("date").gte(startDate).lt(endDate)
        );

        final Sort sorting = Sort.by(new Sort.Order(sortDirection, "date"));
        final PageRequest pagination = PageRequest.of(page, pageSize, sorting);
        query.with(pagination);
        
        final List<Pagamento> list = mongoTemplate.find(query, Pagamento.class);

        long count = list.size();
        long timeStop = System.currentTimeMillis();
        
        System.out.println("Q: FIND-FILTER | PAGINATION: {size="+(pageSize<10?"0"+pageSize:pageSize)+",page="+(page<10?"0"+page:page)+"} | FILTERS: {type="+enumEntry+",date="+startDateStr+"~"+endDateStr+"} | COUNT: "+(count<10?"0"+count:count)+" | TIME: " + (timeStop-timeStart) + "ms");
        return list;
    }

}
