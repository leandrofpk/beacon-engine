package br.gov.inmetro.beacon.v1.application.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/records")
public class RecordsController {

//    private final SearchRecordService searchRecordService;
//
//    @Autowired
//    public RecordsController(SearchRecordService searchRecordService) {
//        this.searchRecordService = searchRecordService;
//    }
//
//    @GetMapping
//    public ModelAndView pesquisar(HttpServletRequest httpServletRequest) {
//          ModelAndView mv = new ModelAndView("records/index");
//
//        Optional<PulseEntity> lastRecord = searchRecordService.last(1);
//
////        if (lastRecord.isPresent()) {
////            final Optional<PulseEntity> previousRecord = searchRecordService.findByTimestampWork(1, lastRecord.get().getTimeStampWork().minus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
////            mv.addObject("previousRecord", previousRecord.isPresent() ? previousRecord.get() : lastRecord);
////            mv.addObject("lastRecord", lastRecord.get());
////        }
//
//        mv.addObject("records", searchRecordService.findLast20(1));
//        mv.addObject("url", getAppUrl(httpServletRequest));
//        mv.addObject("v1", true);
//
//        return mv;
//    }
//
//    @GetMapping("/{id}")
//    public ModelAndView ver(@PathVariable("id") Long idChain) {
//        ModelAndView mv = new ModelAndView("records/show");
//        Optional<PulseEntity> byChainAndId = searchRecordService.findByChainAndId(1, idChain);
//        mv.addObject("record", byChainAndId.get());
//        mv.addObject("v1", true);
//        return mv;
//    }
//
//    private String getAppUrl(HttpServletRequest request) {
//        if (request.getServerPort() != 443){
//            return request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//        } else {
//            return request.getServerName() + request.getContextPath();
//        }
//    }

}
