package br.gov.inmetro.beacon.v1.application.web;

import br.gov.inmetro.beacon.v1.domain.service.SearchRecordService;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequestMapping("/records/chains")
public class ChainsController {

    private final SearchRecordService searchRecordService;

    @Autowired
    public ChainsController(SearchRecordService searchRecordService) {
        this.searchRecordService = searchRecordService;
    }

    @GetMapping
    public ModelAndView pesquisar() {
        return new ModelAndView("redirect:/records/chains/1");
    }

    @GetMapping("/{chainId}")
    public ModelAndView pesquisar(HttpServletRequest httpServletRequest, @PathVariable("chainId") Integer chainId) {
        ModelAndView mv = new ModelAndView("records/index");

        Optional<RecordEntity> lastRecord = searchRecordService.last(chainId);

        if (lastRecord.isPresent()) {
            final Optional<RecordEntity> previousRecord = searchRecordService.findByTimestampWork(chainId, lastRecord.get().getTimeStampWork().minus(chainId, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
            mv.addObject("previousRecord", previousRecord.isPresent() ? previousRecord.get() : lastRecord);
            mv.addObject("lastRecord", lastRecord.get());
        }

        mv.addObject("records", searchRecordService.findLast20(chainId));
        mv.addObject("url", getAppUrl(httpServletRequest));
        mv.addObject("v1", false);
        mv.addObject("chain", chainId);


        return mv;
    }

    @GetMapping("/{chainId}/{id}")
    public ModelAndView ver(@PathVariable("chainId") Integer chainId, @PathVariable("id") Long idChain) {
        ModelAndView mv = new ModelAndView("records/show");
        Optional<RecordEntity> byChainAndId = searchRecordService.findByChainAndId(chainId, idChain);
        mv.addObject("record", byChainAndId.get());
        mv.addObject("v1", false);
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
