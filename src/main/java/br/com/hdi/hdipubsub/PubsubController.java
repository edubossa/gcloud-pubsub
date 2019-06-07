package br.com.hdi.hdipubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloudpubsub")
public class PubsubController {

    @Autowired
    private HDIPubsubRepository repository;

    @GetMapping
    public Iterable<HDIPubsub> getAll() {
        return this.repository.findAll();
    }

}
