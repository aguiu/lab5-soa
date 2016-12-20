package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
		HashMap<String,Object> h = new HashMap<>();
		if (q.contains("max:")) {
			// example: "unizar max:4" -> parts[0]="unizar", parts[1]=4
			String[] parts = q.split("max:");
			q = parts[0];			
			h.put("CamelTwitterCount", Integer.parseInt(parts[1]));
		}
		h.put("CamelTwitterKeywords", q);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", h);
    }
}