package be.yianna.repository;

import be.yianna.domain.Event;
import be.yianna.domain.EventType;
import be.yianna.domain.User;
import be.yianna.domain.AuthorEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//TODO Documentation
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findAllByUser(User user);
    List<Event> findAllByType(EventType type);

    Event findByIdEvent(Long idEvent);

    //@Query("select u.username from User u inner join u.events ar where ar.idEvent = :idEvent")
    //String getAuthorEvent(@Param(value = "idEvent") Long idEvent);

    @Query("select new be.yianna.domain.AuthorEvent(u.username, u.avatar) from User u inner join u.events ar where ar.idEvent = :idEvent")
    AuthorEvent getAuthorEvent(@Param(value = "idEvent") Long idEvent);

    @Query("select u.name from Event u where u.idEvent = :idEvent")
    String getEventTitle(@Param(value = "idEvent") Long idEvent);
}
