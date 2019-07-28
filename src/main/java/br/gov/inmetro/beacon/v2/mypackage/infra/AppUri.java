package br.gov.inmetro.beacon.v2.mypackage.infra;

import javax.servlet.http.HttpServletRequest;

public final class AppUri {

    private final HttpServletRequest request;

    public AppUri(HttpServletRequest request) {
        this.request = request;
    }

    @Deprecated
    public String getUri(){
        if (request.getServerPort() != 443){
            return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } else {
            return "https://" + request.getServerName() + request.getContextPath();
        }
    }

}
