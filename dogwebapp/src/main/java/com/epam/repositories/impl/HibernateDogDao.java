package com.epam.repositories.impl;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.repositories.DogDao;
import java.util.logging.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateDogDao implements DogDao {

  private final SessionFactory sessionFactory;

  public HibernateDogDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }


  @Override
  public DogDto create(DogDto dog) {
    getCurrentSession().save(dog);
    if (dog.getId() == null ) {
      throw new RuntimeException("Dog creation failed");
    }
      return dog;
  }

  @Override
  public DogDto update(DogDto dog) {
    return (DogDto) getCurrentSession().merge(dog);
  }

  @Override
  public DogDto get(long id) {
    DogDto dogDto = getCurrentSession().get(DogDto.class, id);
    if (dogDto != null) {
      return dogDto;
    } else {
      throw new ResourceNotFoundException();
    }
  }

  public DogDto load(long id) {
    return getCurrentSession().load(DogDto.class, id);
  }

  @Override
  public void delete(long id) {
    Session session = getCurrentSession();
    DogDto dogDto = session.load(DogDto.class, id);
    try {
      Logger.getLogger(HibernateDogDao.class.getName()).info("Between load and delete");
      session.delete(dogDto);
    } catch (ObjectNotFoundException e) {
      throw new ResourceNotFoundException(e);
    }
  }

  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  public void flushAndClear() {
    getCurrentSession().flush();
    getCurrentSession().clear();
  }
}
