package com.urban.service.urban;

import com.urban.service.urban.impl.http.UrbanServiceHttpImpl;
import com.urban.service.urban.impl.stub.UrbanServiceStubImpl;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class UrbanServiceFactory {

    private static UrbanService instance;

    private static UrbanService createInstance(UrbanServiceSettings.UrbanServiceType type) {
        UrbanService instance;

        switch (type) {
            case STUB : instance = new UrbanServiceStubImpl();
                break;
            case HTTP_POST : instance = new UrbanServiceHttpImpl();
                break;
            case WEB_SERVICE : instance = new UrbanServiceStubImpl();
                break;
            default : instance = new UrbanServiceStubImpl();
                break;
        }
        return instance;
    }

    public static UrbanService getInstance() {
        if (instance == null) {
            instance = createInstance(UrbanServiceSettings.getServiceType());
        }
        return instance;
    }

}
