package volk.gantt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import volk.gantt.model.Link;
import volk.gantt.service.LinkService;
import volk.gantt.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping("/link")
public class LinkResource {
    private final LinkService linkService;
    private final TaskService taskService;

    @Autowired
    public LinkResource(LinkService linkService, TaskService taskService) {
        this.linkService = linkService;
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Link>> getAllLinks() {
        List<Link> links = linkService.findAllLinks();
        return new ResponseEntity<>(links, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Link> getLinkById(@PathVariable("id") Integer id) {
        Link link = linkService.findLinkById(id);
        return new ResponseEntity<>(link, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Link> addLink(@RequestBody Link link) {
        Link newLink = linkService.addLink(link);
        PertLogic pert = new PertLogic(this.taskService, this.linkService);
        pert.calculatePert();
        return new ResponseEntity<>(newLink, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Link> updateLink(@RequestBody Link link) {
        Link updateLink = linkService.updateLink(link);
        return new ResponseEntity<>(updateLink, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Link> deleteLink(@PathVariable("id") Integer id) {
        linkService.deleteLink(id);
        PertLogic pert = new PertLogic(this.taskService, this.linkService);
        pert.calculatePert();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
