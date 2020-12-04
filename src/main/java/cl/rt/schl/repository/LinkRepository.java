package cl.rt.schl.repository;

import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Link;

public interface LinkRepository extends CrudRepository<Link, Long> {

	public Link findByLink(String link);

	public Link deleteByLink(String link);

}