package io.spring.cloud.samples.commerce.ui.services.commerce;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@EnableConfigurationProperties(CommerceProperties.class)
public class CommerceService {

    @Autowired
    CommerceProperties commerceProperties;

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackGetAll")
    public Commerce[] findAll() {
        Item[] allItems = restTemplate.getForObject("http://item/items", Item[].class);
        Map<String, String> prices = restTemplate.getForObject("http://price/prices", Map.class);
        Commerce[] commerces = new Commerce[allItems.length];
        int j=0;
        for (Item i :allItems){
            commerces[j++]= new Commerce(i.getId(),i.getName(),i.getCategory(),i.getDescription(), Integer.parseInt(prices.get(i.getId().toString())));
        }
        return commerces;
    }

    @HystrixCommand(fallbackMethod = "fallbackCategory")
    public Commerce[] findByCategory(String category){
        Item[] allItems = restTemplate.getForObject("http://item/category/{cat}", Item[].class,category);
        Map<String, String> prices = restTemplate.getForObject("http://price/prices", Map.class);
        Commerce[] commerces = new Commerce[allItems.length];
        int j=0;
        for (Item i :allItems){
            commerces[j++]= new Commerce(i.getId(),i.getName(),i.getCategory(),i.getDescription(), Integer.parseInt(prices.get(i.getId().toString())));
        }
        return commerces;
    }

    @HystrixCommand(fallbackMethod = "fallbackId")
    public Commerce[] findById(Long id){
        Item[] allItems = restTemplate.getForObject("http://item/item/{itemId}", Item[].class,id);
        Map<String, String> prices = restTemplate.getForObject("http://price/prices", Map.class);
        Commerce[] commerces = new Commerce[allItems.length];
        int j=0;
        for (Item i :allItems){
            commerces[j++]= new Commerce(i.getId(),i.getName(),i.getCategory(),i.getDescription(), Integer.parseInt(prices.get(i.getId().toString())));
        }
    return commerces;
    }

    private Commerce[] fallbackId(Long id) {
        return commerceProperties.getError();
    }

    private Commerce[] fallbackCategory(String cat) {
        return commerceProperties.getError();
    }

    private Commerce[] fallbackGetAll() {
        return commerceProperties.getError();
    }

}
