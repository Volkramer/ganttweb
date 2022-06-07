package volk.gantt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import volk.gantt.exception.LinkNotFoundException;
import volk.gantt.model.Link;
import volk.gantt.repo.LinkRepo;

@Service
public class LinkService {
    private final LinkRepo linkRepo;

    @Autowired
    public LinkService(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    public Link addLink(Link link) {
        return linkRepo.save(link);
    }

    public List<Link> findAllLinks() {
        return linkRepo.findAll();
    }

    public Link updateLink(Link link) {
        return linkRepo.save(link);
    }

    public Link findLinkById(Integer id) {
        return linkRepo.findLinkById(id)
                .orElseThrow(() -> new LinkNotFoundException("Link by id " + id + " was not found"));
    }

    @Transactional
    public void deleteLink(Integer id) {
        linkRepo.deleteById(id);
    }

}
