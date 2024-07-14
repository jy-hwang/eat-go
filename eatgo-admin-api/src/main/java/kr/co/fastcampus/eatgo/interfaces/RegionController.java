package kr.co.fastcampus.eatgo.interfaces;

import jakarta.validation.Valid;
import kr.co.fastcampus.eatgo.application.RegionService;
import kr.co.fastcampus.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<Region> list() {
        List<Region> regions = regionService.getRegions();

        return regions;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Region resource) throws URISyntaxException {
        Region saved = regionService.addRegion(resource.getName());

        URI uri = new URI("/regions/" + saved.getId());
        return ResponseEntity.created(uri).body("{}");
    }

}
