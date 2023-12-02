package com.tonictrade.tonictrade.configuration;

import com.tonictrade.tonictrade.datamodel.AppVersionModel;
import com.tonictrade.tonictrade.datamodel.RemoteTradeOneData;
import com.tonictrade.tonictrade.repository.RemoteTradeOneRepository;
import com.tonictrade.tonictrade.repository.VersionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApiConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(VersionRepository versionRepository, RemoteTradeOneRepository remoteTradeOneRepository){


        return args -> {


            List<AppVersionModel> appVersionModels = new ArrayList<>();
            AppVersionModel appVersionModel = new AppVersionModel(5,"1.3");
            appVersionModels.add(appVersionModel);
            versionRepository.save(appVersionModel);

//            remoteTradeOneRepository.save(new RemoteTradeOneData("Remote Trade One","","","","",0,""));
//            remoteTradeOneRepository.save(new RemoteTradeOneData("Remote Trade Two","","","","",0,""));

        };
    }


}
