package br.gov.inmetro.beacon.application.web;

import br.gov.inmetro.beacon.infra.Record;
import br.gov.inmetro.beacon.domain.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequestScope
@RequestMapping("/records")
public class RecordsController {

    private final SearchRecordService searchRecordService;

    @Autowired
    public RecordsController(SearchRecordService searchRecordService) {
        this.searchRecordService = searchRecordService;
    }

    @GetMapping
    public ModelAndView pesquisar(HttpServletRequest httpServletRequest) {
          ModelAndView mv = new ModelAndView("records/index");

        Optional<Record> lastRecord = searchRecordService.last(1);

        if (lastRecord.isPresent()) {
            final Optional<Record> previousRecord = searchRecordService.findByTimestamp(1, lastRecord.get().getTimeStamp().minus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
            mv.addObject("previousRecord", previousRecord.isPresent() ? previousRecord.get() : lastRecord);
            mv.addObject("lastRecord", lastRecord.get());
        }

        mv.addObject("records", searchRecordService.findLast20(1));
        mv.addObject("url", getAppUrl(httpServletRequest));
        mv.addObject("v1", true);
//        mv.addObject("chain", 1);


//        Optional<Record> lastRecord = searchRecordService.last(1);
//        final Optional<Record> previousRecord = searchRecordService.findByTimestamp(1, lastRecord.get().getTimeStamp().minus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
//        ModelAndView mv = new ModelAndView("records/index");
//        mv.addObject("records", searchRecordService.findLast20(1));
//        mv.addObject("url", getAppUrl(httpServletRequest));
//        mv.addObject("lastRecord", lastRecord);
//        mv.addObject("previousRecord", previousRecord.isPresent() ? previousRecord.get() : lastRecord);
//        mv.addObject("v1", true);

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView ver(@PathVariable("id") Long idChain) {
        ModelAndView mv = new ModelAndView("records/show");
        Optional<Record> byChainAndId = searchRecordService.findByChainAndId(1, idChain);
        mv.addObject(byChainAndId.get());
        mv.addObject("v1", true);
        return mv;
    }

    private String getAppUrl(HttpServletRequest request) {
        if (request.getServerPort() != 443){
            return request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } else {
            return request.getServerName() + request.getContextPath();
        }
    }

}
